package io.github.newnc.model;

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * This field represents the image of the movie.
	 */
	private String poster_path;

	/**
	 * This field represents the id of the movie.
	 */
	private int id;

	/*
		classified genre of the movie.
	*/
	private List<String> labels = new ArrayList<String>();

	//private String poster_path;
	//private boolean adult;
	//private String release_date;
	
	//private String original_title;
	//private String original_language;
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
		return this.overview;
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
		 //System.out.println("MovieInfo::getTitle " + title);
		return this.title;
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
	 * Sets the <code>backdrop_path</code> of this <code>MovieInfo</code> instance.
	 *
	 * @param title the <code>backdrop_path</code> of this <code>MovieInfo</code> instance.
	 */
	public String getPoster_path() {
		return this.poster_path;
	}

	/**
	 * Sets the <code>backdrop_path</code> of this <code>MovieInfo</code> instance.
	 *
	 * @param title the <code>backdrop_path</code> of this <code>MovieInfo</code> instance.
	 */
	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	/**
	 * Returns a <code>String</code> representation of this <code>MovieInfo
	 * </code> instance.
	 *
	 * @return a <code>String</code> representation of this <code>MovieInfo
	 * </code> instance.
	 */
	public String stringify() {
	  return title + " - " + poster_path + " - " + overview;
    }

	public List<String> getLabels(){
		//System.out.println("MovieInfo::Sizelabel "+labels.size());
		return this.labels;
	}

	public String findLabel(String find){
		return this.labels.get(this.labels.indexOf(find));
	}

	public void setLabel(List<String> labels){
		this.labels = labels;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
