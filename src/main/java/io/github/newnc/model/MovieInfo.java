package io.github.newnc.model;

/**
 * This class represents one movie and all information about that is given by 
 * the TMDB API. 
 *
 */
public class MovieInfo {
	
	/**
	 * This field represents the title of the movie.
	 */
	private String title;
	
	/**
	 * This field represents an overview of the movie.
	 */
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

	
	/**
	 * Returns the <code>overview</code> of this <code>MovieInfo</code> 
	 * instance.
	 * 
	 * @return the <code>overview</code> of this <code>MovieInfo</code> 
	 * instance.
	 */
	public String getOverview() {
		return overview;
	}

	/**
	 * Sets the <code>overview</code> of this <code>MovieInfo</code> 
	 * instance.
	 * 
	 * @param overview the <code>overview</code> of this <code>MovieInfo</code> 
	 * instance.
	 */
	public void setOverview(String overview) {
		this.overview = overview;
	}

	/**
	 * Returns the <code>title</code> of this <code>MovieInfo</code> instance.
	 * 
	 * @return the <code>title</code> of this <code>MovieInfo</code> instance.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the <code>title</code> of this <code>MovieInfo</code> instance.
	 * 
	 * @param title the <code>title</code> of this <code>MovieInfo</code> instance.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns a <code>String</code> representation of this <code>MovieInfo
	 * </code> instance.
	 * 
	 * @return a <code>String</code> representation of this <code>MovieInfo
	 * </code> instance.
	 */
	public String stringify() {
	  return title + " - " + overview;
    }

}
