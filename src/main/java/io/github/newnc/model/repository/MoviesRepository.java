package io.github.newnc.model.repository;

import java.util.ArrayList;
import java.util.List;

import io.github.newnc.debug.Print;
import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponse;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.JsonObject;

/**
 * This class represents an in-memory repository of movies gets from TMDB API.
 *
 * This class implements the singleton pattern.
 *
 * @see <a href="http://www.oodesign.com/singleton-pattern.html">Singleton
	 *      Pattern</a>
 */
public class MoviesRepository extends AbstractRepository{
	/**
	 * This fields represents a list of pages of the response from TMDB API.
	 */
	protected List<MovieResponse> movieResponsePages;

	
	/**
	 * Default constructor.
	 */
	protected MoviesRepository(){
		if(Print.debugMoviesRepository) 
			System.out.println("MovieRepository()");
		init();
	}
	
	@Override
	protected void init(){
		if(Print.debugMoviesRepository) 
			System.out.println("Init of MovieRepository");
		
		movieResponsePages = new ArrayList<>();
	}
	
	@Override
	public void updateIfNeeded(){
		if(Print.debugMoviesRepository) 
			System.out.println("MovieRepository's updateIfNeeded()");
		
		if(isEmpty()) update();
	}

	@Override
	public void forceUpdate(){
		clear();
		update();
	}

	@Override
	public void clear(){
		if(Print.debugMoviesRepository) 
			System.out.println("MovieRepository's clear()");
		
		movieResponsePages.clear();
	}

	@Override
	public boolean isEmpty(){
		if(Print.debugMoviesRepository) 
			System.out.println("MovieRepository's isEmpty() : "+this.getClass());
		
		return movieResponsePages.isEmpty();
	}

	@Override
	protected void update(){
		// Not used
		if(Print.debugMoviesRepository) 
			System.out.println("Called an unimplemented method");
	}

	@Override
	public String getRandomCover(){
		// Only used by the childs
		return null;
	}

	@Override
	public String[] getRandomCoversFromEachCategory(){
		// Only used by the childs
		return null;
	}

	@Override
	public String[] getRandomCoversForResult(int category){
		// Only used by the childs
		return null;
	}
	
	/**
	 * Returns a specific <code>page</code> of this <code>MovieRepository
	 * </code> instance.
	 *
	 * @param numPage
	 *            the number of the <code>page</code>.
	 * @return a specific <code>page</code> of this <code>MovieRepository
	 * </code> instance.
	 */
	public MovieResponse getPage(int numPage){
		int i;
		for(i = 0; i < movieResponsePages.size(); i++)
			if(movieResponsePages.get(i).getPage() == numPage) return movieResponsePages.get(i);

		return null;
	}

	protected MovieResponse getMovieResponse(String apiResponse){

		JsonObject jsonObjectFactory = new JsonObject();
		// Here is where we create the first MovieInfo objects, and set their ids
		MovieResponseAPI movieData = jsonObjectFactory.createObject(apiResponse);
		
		if(movieData == null){
			System.out.println("movieData is null, returning null");
			return null;
		}
		
		movieData.labelMovies();

		return MovieResponse.createMovieResponse(movieData);
	}
	
	public MovieInfo findMovieById(int movie_id){
		MovieInfo found_it = null;
		
		if(Print.debugMoviesRepository)
			System.out.println("findMovieById("+movie_id+")");

		// Busca em cada filme de cada página pelo filme que tenha o id procurado
		for(MovieResponse mr : movieResponsePages){
			
			List<MovieInfo> tempListMI = mr.getMovies();
			
			for(MovieInfo mi : tempListMI){
				if(mi.getId() == movie_id){
					//System.out.println("FOUND SOMEONE "+mi.getId()+" "+mi.getTitle());
					found_it = mi;
					break;
				}
			}
			
			if(found_it != null){
				break;
			}
		}
		
		if(found_it == null){
			if(Print.debugMoviesRepository) 
				System.out.println("Couldn't find movie by id.");
		}
		
		return found_it;
	}
	
}
