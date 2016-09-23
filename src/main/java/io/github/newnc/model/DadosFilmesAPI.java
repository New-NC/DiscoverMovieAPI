package io.github.newnc.model;

public class DadosFilmesAPI {
	//private String poster_path;
	//private boolean adult;
	private String overview;
	//private String release_date;
	//private int id;
	//private String original_title;
	//private String original_language;
	private String title;
	//private String backdrop_path;
	//private String popularity;
	//private int vote_count;
	//private String video;
	//private int vote_average;

	public String getOverview() {
		return overview;
	}




	public void setOverview(String overview) {
		this.overview = overview;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}


	  @Override
	    public String toString() { //converte o objeto para String
	        return title + " - " + overview;
	    }


}
