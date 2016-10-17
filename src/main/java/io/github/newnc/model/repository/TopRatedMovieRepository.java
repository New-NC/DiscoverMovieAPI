package io.github.newnc.model.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.DataReloadTimer;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.TMDBRequester;

public class TopRatedMovieRepository extends MovieRepository {
	
	@Override
	protected void update() throws Exception {
		System.out.println("update " + System.currentTimeMillis());

		for (int i = 1; i <= TMDBRequester.MAXREQUEST / 2; i++) {
			String apiResponse = TMDBRequester.requestPageTopRated(i);

			JsonObject jsonObjectFactory = new JsonObject();
			MovieResponseAPI movieData = jsonObjectFactory.createObject(apiResponse)[0];
			List<MovieInfo> movies = movieData.getMovies();
			movieData.setMovies(movies);
			
			for(MovieInfo movie : movies){
				/*Foram adicionadas 4 listas no AbstractRepository
				 * Aqui, precisa iterar por todos os filmes e dependendo da label
				 * colocar na lista adequada. Assim, teremos uma lista com filmes de aventura,
				 * animal, tecnologia e princesa.
				 */
				
				List<String> labels = movie.getLabels();
				if(debug)
					System.out.println("Movie "+movie.getTitle()+" has "+labels.size()+" labels");
				
				for(String label: labels){
					if( label.equals("DOG") ||
						label.equals("PONY") ||
						labels.equals("FISH") ||
						labels.equals("LION") ||
						labels.equals("CAT")
							){
						/* Animal */
						listAnimal.add(movie.getId());
						
					}
					else if( labels.equals("ROBOT") ||
							 labels.equals("RACE CAR") ||
							 labels.equals("MONSTER")
							){
						
						/* Tech */
						listTech.add(movie.getId());
					}
					else if( labels.equals("PRINCESS") ||
							 labels.equals("PRINCE") ||
							 labels.equals("UNICORN") ||
							 labels.equals("FAIRY") ||
							 labels.equals("DWARF")
							){
						
						/* Princess */
						listPrincess.add(movie.getId());
					}
					else if( labels.equals("FRIENDSHIP") ||
							 labels.equals("DRAGON") ||
							 labels.equals("TOY")
							){
						
						/* Adventure */
						listAdventure.add(movie.getId());
					}
				}
			}

			pages.add(movieData);
			
			if(debug) printLists(movies);
		}

		setChanged();
		notifyObservers();

		System.out.println("---- TopRatedMovieRepository -----");
	}

	/**
	 * This fields represents a instance of this class.
	 */
	private static TopRatedMovieRepository instance;

	/**
	 * Returns an instance of this class.
	 *
	 * @return an instance of this class.
	 */
	public static synchronized TopRatedMovieRepository getInstance() {
		if (instance == null)
			instance = new TopRatedMovieRepository();
		return instance;
	}

	/**
	 * Default constructor.
	 */
	protected TopRatedMovieRepository() {
		pages = new ArrayList<>();

		addObserver(DataReloadTimer.getTimer());
	}

	public void printLists(List<MovieInfo> movies){
		System.out.println("listAdventure");
		if(listAdventure.size() > 0)
			for(int i : listAdventure){
				System.out.println(movies.get(i).getTitle());
			}
		System.out.println("\nlistAnimal");
		if(listAdventure.size() > 0)
			for(int i : listAnimal){
				System.out.println(movies.get(i).getTitle());
			}
		System.out.println("\nlistPrincess");
		if(listPrincess.size() > 0)
			for(int i : listPrincess){
				System.out.println(movies.get(i).getTitle());
			}
		System.out.println("\nlistTech");
		if(listTech.size() > 0)
			for(int i : listTech){
				System.out.println(movies.get(i).getTitle());
			}
		System.out.println("");
	}
	
	private boolean debug = true;
	
}
