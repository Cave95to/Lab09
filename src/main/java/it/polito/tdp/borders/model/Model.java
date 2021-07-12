package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private BordersDAO dao;
	private Graph<Country, DefaultEdge> grafo ;
	private Map<Integer,Country> countriesMap ;
	private List<Country> verticiOrdinati;
	
	public Model() {
		this.dao = new BordersDAO();
	}

	public void creaGrafo(int anno) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		this.countriesMap = new HashMap<>();
		this.verticiOrdinati = new ArrayList<>();
		
		// carichiamo tutti paesi nella mappa (anche quelli senza confini)
		this.dao.loadAllCountries(this.countriesMap);
		
		
		List<Border> borders = this.dao.getCountryPairs(this.countriesMap, anno);
		
		if(borders.size()!=0) {
			for(Border b : borders) {
				Country c1 = b.getC1();
				Country c2 = b.getC2();
				this.grafo.addVertex(c1);
				this.grafo.addVertex(c2);
				this.grafo.addEdge(c1, c2);
				if(!this.verticiOrdinati.contains(c1))
					this.verticiOrdinati.add(c1);
				if(!this.verticiOrdinati.contains(c2))
					this.verticiOrdinati.add(c2);
					
			}
			Collections.sort(this.verticiOrdinati);
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

	public List<Country> getVertici() {
		if(this.grafo!=null) {
			Collections.sort(verticiOrdinati);
			return this.verticiOrdinati;
		}
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

	public Set<Country> getRaggiungibiliInspector(Country c) {
		if(this.grafo!= null) {
			
			ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(this.grafo) ;
		
			return  ci.connectedSetOf(c);
		
		}
		
		return null;
	}

	public List<Country> getRaggiungibiliBreadthFirstIterator(Country c) {
			
		// AMPIEZZA
		BreadthFirstIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<>(this.grafo, c);
		
		List<Country> result = new ArrayList<>();
		
		while(bfv.hasNext()) {
			 Country f = bfv.next();
			 result.add(f);
		}
		
		return result;
	}
	
	public List<Country> getRaggiungibiliManualeIterazioni(Country c) {
		
		List<Country> daVisitare = new ArrayList<>();
		List<Country> visitati = new ArrayList<>();
		
		visitati.add(c);
		
		daVisitare.addAll(Graphs.neighborListOf(this.grafo, c));
		
		
		while(!daVisitare.isEmpty()) {
			
			Country temp = daVisitare.remove(0);
				
			visitati.add(temp);
			
			List<Country> vicini = Graphs.neighborListOf(this.grafo, temp);
			
			vicini.removeAll(visitati);
			
			vicini.removeAll(daVisitare);
			
			daVisitare.addAll(vicini);
			

		}
		return visitati;
		
	}
	
	public List<Country> getRaggiungibiliRecursive(Country selectedCountry) {

		List<Country> visited = new ArrayList<Country>();
		recursiveVisit(selectedCountry, visited);
		return visited;
	}

	private void recursiveVisit(Country n, List<Country> visited) {
		// Do always
		visited.add(n);

		// cycle
		for (Country c : Graphs.neighborListOf(grafo, n)) {	
			// filter
			if (!visited.contains(c))
				recursiveVisit(c, visited);
				// DO NOT REMOVE!! (no backtrack)
		}
	}
}
