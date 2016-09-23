package io.github.newnc.model;

import java.util.ArrayList;

public class RespostaFilmesAPI {
	private int page;
	private ArrayList<DadosFilmesAPI> results; //results = nome do campo no JSON
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public ArrayList<DadosFilmesAPI> getResults() {
		return results;
	}

	public void setResults(ArrayList<DadosFilmesAPI> results) {
		this.results = results;
	}

	@Override
    public String toString() { //converte o objeto para String
		return page + " - " + "(" + results + ")";
    }
	
	

}
