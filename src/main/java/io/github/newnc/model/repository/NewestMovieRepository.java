package io.github.newnc.model.repository;

import java.util.ArrayList;
import java.util.List;

import io.github.newnc.debug.Print;
import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponse;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.DataReloadTimer;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.TMDBRequester;

public class NewestMovieRepository extends MovieRepository {

	@Override
	protected void update() throws Exception {
		System.out.println("update " + System.currentTimeMillis());

		int i = 1;
		int j = 1;
		while (this.listAdventure.isEmpty() || this.listAnimal.isEmpty() || this.listPrincess.isEmpty()
				|| this.listTech.isEmpty()) {
			while (i <= j * (TMDBRequester.MAXREQUEST / 2)) {
				String apiResponse = TMDBRequester.requestPageNewest(i);

				JsonObject jsonObjectFactory = new JsonObject();
				MovieResponseAPI movieDataAPI = jsonObjectFactory.createObject(apiResponse);
				MovieResponse movieData = MovieResponse.createMovieResponse(movieDataAPI);

				List<Integer> listAdventure = null;// new ArrayList<Integer>();
				List<Integer> listAnimal = null;// new ArrayList<Integer>();
				List<Integer> listPrincess = null;// new ArrayList<Integer>();
				List<Integer> listTech = null;// new ArrayList<Integer>();

				if (movieData != null && movieData.getMovies() != null)
					for (MovieInfo movie : movieData.getMovies()) {
						/*
						 * Foram adicionadas 4 listas no AbstractRepository
						 * Aqui, precisa iterar por todos os filmes e dependendo
						 * da label colocar na lista adequada. Assim, teremos
						 * uma lista com filmes de aventura, animal, tecnologia
						 * e princesa.
						 */

						List<String> labels = movie.getLabels();

						Print.howManyLabels(movie);

						for (String label : labels) {
							if (label.equals("DOG") || label.equals("PONY") || label.equals("FISH")
									|| label.equals("LION") || label.equals("CAT") || label.equals("SNOOPY")) {
								Print.categorizeMovie(movie, label, "Animal");
								if (listAnimal == null)
									listAnimal = new ArrayList<>();
								listAnimal.add(movieData.getMovies().indexOf(movie));
								this.listAnimal.put(i, listAnimal);
							} else if (label.equals("ROBOT") || label.equals("RACE CAR") || label.equals("MONSTER")) {
								Print.categorizeMovie(movie, label, "Tech");
								if (listTech == null)
									listTech = new ArrayList<>();
								listTech.add(movieData.getMovies().indexOf(movie));
								this.listTech.put(i, listTech);
							} else if (label.equals("PRINCESS") || label.equals("PRINCE") || label.equals("UNICORN")
									|| label.equals("FAIRY") || label.equals("DWARF")) {
								Print.categorizeMovie(movie, label, "Princess");
								if (listPrincess == null)
									listPrincess = new ArrayList<>();
								listPrincess.add(movieData.getMovies().indexOf(movie));
								this.listPrincess.put(i, listPrincess);
							} else if (label.equals("FRIENDSHIP") || label.equals("DRAGON") || label.equals("TOY")) {
								Print.categorizeMovie(movie, label, "Adventure");
								if (listAdventure == null)
									listAdventure = new ArrayList<>();
								listAdventure.add(movieData.getMovies().indexOf(movie));
								this.listAdventure.put(i, listAdventure);
							}
						}
					}

				pages.add(movieData);

				Print.allCategoriesList(pages, this.listAdventure, this.listAnimal, this.listPrincess, this.listTech);

				i++;
			}
			j++;
		}

		Print.allCategoriesList(pages, this.listAdventure, this.listAnimal, this.listPrincess, this.listTech);

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
}
