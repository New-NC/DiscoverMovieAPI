package io.github.newnc.model;

public class MovieInfo {
	
	// attributes
	private String title;
	private String overview;
	
	//private String poster_path;
	//private boolean adult;
	//private String release_date;
	//private int id;
	//private String original_title;
	//private String original_language;
	//private String backdrop_path;
	//private String popularity;
	//private int vote_count;
	//private String video;
	//private int vote_average;

	
	// methods
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

	//converte o objeto para String
	@Override
    public String toString() {
	  return title + " - " + overview;
    }

}
