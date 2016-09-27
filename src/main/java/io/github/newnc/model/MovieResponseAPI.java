package io.github.newnc.model;

import java.util.ArrayList;

public class MovieResponseAPI {
	// attributes
	private int page;
	private ArrayList<MovieInfo> results; //results = nome do campo no JSON
	
	
	// methods
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public ArrayList<MovieInfo> getResults() {
		return results;
	}

	public void setResults(ArrayList<MovieInfo> results) {
		this.results = results;
	}

	@Override
    public String toString() { //converte o objeto para String
		return page + " - " + "(" + results + ")";
	}

}
