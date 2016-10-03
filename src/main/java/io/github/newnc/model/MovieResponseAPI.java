package io.github.newnc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a response given by the TMDB API. 
 *
 */
public class MovieResponseAPI {
	
	/**
	 * This field represents the page of the response.
	 */
	private int page;
	
	/**
	 * This field represents the list of movies in the response.
	 */
	private List<MovieInfo> movies;
	
	/**
	 * Returns the number of the page of response from TMDB API of this <code>
	 * MovieResponseAPI</code> instance.
	 * 	
	 * @return the number of the page of response from TMDB API of this <code>
	 * MovieResponseAPI</code> instance.
	 */
	public int getPage() {
		return page;
	}

	/**
	 * Sets the number of the page of response from TMDB API of this <code>
	 * MovieResponseAPI</code> instance.
	 * 
	 * @param page the number of the page of response from TMDB API of this 
	 * <code>MovieResponseAPI</code> instance.
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
	/**
	 * Returns the list of movies given by the request to TMDB API of this 
	 * <code>MovieResponseAPI</code> instance.
	 * 
	 * @return the list of movies given by the request to TMDB API of this 
	 * <code>MovieResponseAPI</code> instance.
	 */
	public List<MovieInfo> getMovies() {
		return movies;
	}

	/**
	 * Sets the list of movies given by the request to TMDB API of this 
	 * <code>MovieResponseAPI</code> instance.
	 * 
	 * @param movies the list of movies given by the request to TMDB API of this 
	 * <code>MovieResponseAPI</code> instance.
	 */
	public void setMovies(ArrayList<MovieInfo> movies) {
		this.movies = movies;
	}

	public String stringify() {
		return page + " - " + "(" + movies + ")";
	}

}
