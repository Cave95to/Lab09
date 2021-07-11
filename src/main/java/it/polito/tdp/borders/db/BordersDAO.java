package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> countriesMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!countriesMap.containsKey(rs.getInt("ccode"))) {
					Country c = new Country(rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme"));
					countriesMap.put(c.getCod(), c);
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(Map<Integer,Country> countriesMap, int anno) {

		String sql = "SELECT c.state1no AS id1, c.state2no AS id2 "
				+ "FROM contiguity c "
				+ "WHERE c.conttype = 1 AND c.year<= ? AND c.state1no < c.state2no";
		
		List<Border> result = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c1 = countriesMap.get(rs.getInt("id1"));
				Country c2 = countriesMap.get(rs.getInt("id2"));
				
				if(c1!= null && c2!= null) {
					Border b = new Border(c1, c2);
					result.add(b);
				}
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
}
