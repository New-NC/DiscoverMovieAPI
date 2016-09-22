package io.github.newnc.service;

import java.util.ArrayList;

public class RespostaFilmesAPI {
	private int page;
	ArrayList<DadosFilmesAPI> results; //results = nome do campo no JSON
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	@Override
    public String toString() { //converte o objeto para String
		return page + " - " + "(" + results + ")";
    }
	
	

}
