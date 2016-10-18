package io.github.newnc.model.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.DataReloadTimer;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.TMDBRequester;

public class NewestMovieRepository extends MovieRepository {

	private boolean debug = true;

	@Override
	protected void update() throws Exception {
		System.out.println("update " + System.currentTimeMillis());

		int i;

		for (i = 1; i <= TMDBRequester.MAXREQUEST / 2; i++) {
			String apiResponse = TMDBRequester.requestPageNewest(i);

			JsonObject jsonObjectFactory = new JsonObject();
			MovieResponseAPI movieData = jsonObjectFactory.createObject(apiResponse)[0];
			List<MovieInfo> movies = movieData.getMovies();
			movieData.setMovies(movies);

			List<Integer> listAdventure = new ArrayList<Integer>();
			List<Integer> listAnimal = new ArrayList<Integer>();
			List<Integer> listPrincess = new ArrayList<Integer>();
			List<Integer> listTech = new ArrayList<Integer>();

			for (MovieInfo movie : movieData.getMovies()) {
				/*
				 * Foram adicionadas 4 listas no AbstractRepository Aqui,
				 * precisa iterar por todos os filmes e dependendo da label
				 * colocar na lista adequada. Assim, teremos uma lista com
				 * filmes de aventura, animal, tecnologia e princesa.
				 */

				List<String> labels = movie.getLabels();

				if (debug)
					System.out.println("Movie " + movie.getTitle() + " has " + labels.size() + " labels");

				for (String label : labels) {
					System.out.println(">>>>>>>> " + label);
					if (label.equals("DOG") 
							|| label.equals("PONY") 
							|| label.equals("FISH") 
							|| label.equals("LION")
							|| label.equals("CAT")) {
						/* Animal */
						System.out.println("<<<<<<< Animal");
						listAnimal.add(movieData.getMovies().indexOf(movie));

					} else if (label.equals("ROBOT") 
							|| label.equals("RACE CAR") 
							|| label.equals("MONSTER")) {

						/* Tech */
						System.out.println("<<<<<<< Tech");
						listTech.add(movieData.getMovies().indexOf(movie));
					} else if (label.equals("PRINCESS") 
							|| label.equals("PRINCE") 
							|| label.equals("UNICORN")
							|| label.equals("FAIRY") 
							|| label.equals("DWARF")) {

						/* Princess */
						System.out.println("<<<<<<< Princess");
						listPrincess.add(movieData.getMovies().indexOf(movie));
					} else if (label.equals("FRIENDSHIP") 
							|| label.equals("DRAGON") 
							|| label.equals("TOY")) {

						/* Adventure */
						System.out.println("<<<<<<< Adventure");
						listAdventure.add(movieData.getMovies().indexOf(movie));
					}
				}
			}

			this.listAdventure.put(i, listAdventure);
			this.listAnimal.put(i, listAnimal);
			this.listPrincess.put(i, listPrincess);
			this.listTech.put(i, listTech);

			pages.add(movieData);

		}

		if (debug)
			printLists();

		setChanged();
		notifyObservers();

		System.out.println("---- NewestMovieRepository -----");
	}

	/**
	 * This fields represents a instance of this class.
	 */
	private static NewestMovieRepository instance;

	/**
	 * Returns an instance of this class.
	 *
	 * @return an instance of this class.
	 */
	public static synchronized NewestMovieRepository getInstance() {
		if (instance == null)
			instance = new NewestMovieRepository();
		return instance;
	}

	/**
	 * Default constructor.
	 */
	protected NewestMovieRepository() {
		pages = new ArrayList<>();

		addObserver(DataReloadTimer.getTimer());
	}

	public void printLists() {
		System.out.println("listAdventure");
		for (int j = 0; j < listAdventure.size(); j++)
			if (listAdventure.get(j) != null && listAdventure.get(j).size() > 0)
				for (Integer i : listAdventure.get(j)) {
					if (pages.get(j).getMovies().size() > i.intValue())
						System.out.println(pages.get(j).getMovies().get(i.intValue()).getTitle());
				}
		System.out.println("\nlistAnimal");
		for (int j = 0; j < listAnimal.size(); j++)
			if (listAnimal.get(j) != null && listAnimal.get(j).size() > 0)
				for (Integer i : listAnimal.get(j)) {
					if (pages.get(j).getMovies().size() > i.intValue())
						System.out.println(pages.get(j).getMovies().get(i.intValue()).getTitle());
				}
		System.out.println("\nlistPrincess");
		for (int j = 0; j < listPrincess.size(); j++)
			if (listPrincess.get(j) != null && listPrincess.get(j).size() > 0)
				for (Integer i : listPrincess.get(j)) {
					if (pages.get(j).getMovies().size() > i.intValue())
						System.out.println(pages.get(j).getMovies().get(i.intValue()).getTitle());
				}
		System.out.println("\nlistTech");
		for (int j = 0; j < listTech.size(); j++)
			if (listTech.get(j) != null && listTech.get(j).size() > 0)
				for (Integer i : listTech.get(j)) {
					if (pages.get(j).getMovies().size() > i.intValue())
						System.out.println(pages.get(j).getMovies().get(i.intValue()).getTitle());
				}
		System.out.println("");
	}
}
