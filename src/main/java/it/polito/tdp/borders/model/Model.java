package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private BordersDAO dao;
	private Graph<Country, DefaultEdge> grafo ;
	private Map<Integer,Country> countriesMap ;
	
	public Model() {
		this.dao = new BordersDAO();
	}

	public void creaGrafo(int anno) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		this.countriesMap = new HashMap<>();
		
		// carichiamo tutti paesi nella mappa (anche quelli senza confini)
		this.dao.loadAllCountries(this.countriesMap);
		
		
		List<Border> borders = this.dao.getCountryPairs(this.countriesMap, anno);
		
		if(borders.size()!=0) {
			for(Border b : borders) {
				this.grafo.addVertex(b.getC1());
				this.grafo.addVertex(b.getC2());
				this.grafo.addEdge(b.getC1(), b.getC2());
			}
		}

		
	}

	public int getNVertici() {
		if(this.grafo!=null)
			return this.grafo.vertexSet().size();
		return 0;
	}

	public int getNArchi() {
		if(this.grafo!=null)
			return this.grafo.edgeSet().size();
		return 0;
	}

	public Set<Country> getVertici() {
		if(this.grafo!=null)
			return this.grafo.vertexSet();
		return null;
	}

	public int getGrado(Country c) {
		return this.grafo.degreeOf(c);
	}
	
	public int getNComponenti() {
		if(this.grafo!= null) {
			
			ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(this.grafo) ;
		
			return ci.connectedSets().size();
		
		}
		
		return 0;
	}
	
}
