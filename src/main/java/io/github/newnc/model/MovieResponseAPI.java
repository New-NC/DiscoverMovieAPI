package io.github.newnc.model;

import java.io.IOException;
import java.util.List;

import io.github.newnc.debug.Print;

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
	 * This field represents the total pages of the response.
	 */
	private int total_pages;

	/**
	 * This field represents the list of movies in the response.
	 */
	private List<MovieInfo> results;

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
		return results;
	}

	/**
	 * Sets the list of movies given by the request to TMDB API of this
	 * <code>MovieResponseAPI</code> instance.
	 * @param movies the list of movies given by the request to TMDB API of this
	 * <code>MovieResponseAPI</code> instance.
	 *
	 * @throws IOException
	 */
	public void labelMovies(){
		
		new OverviewSearcher().execute(this.results);

		Print.moviesTitleAndLabels(this.results);
	}

	public String stringify() {
		return page + " - " + "(" + results + ")";
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}

}
