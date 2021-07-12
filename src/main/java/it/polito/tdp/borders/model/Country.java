package it.polito.tdp.borders.model;

public class Country implements Comparable<Country>{
	
	private String abbreviazione;
	private int cod;
	private String nome;
	
	public Country(String abbreviazione, int cod, String nome) {
		super();
		this.abbreviazione = abbreviazione;
		this.cod = cod;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return  nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cod;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (cod != other.cod)
			return false;
		return true;
	}

	public String getAbbreviazione() {
		return abbreviazione;
	}

	public void setAbbreviazione(String abbreviazione) {
		this.abbreviazione = abbreviazione;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int compareTo(Country o) {
		return this.nome.compareTo(o.nome);
	}
	
	
}
