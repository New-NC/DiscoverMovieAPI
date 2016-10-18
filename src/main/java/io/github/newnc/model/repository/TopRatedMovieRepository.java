package io.github.newnc.model.repository;

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

		int i = 1;
		int j = 1;
		while (this.listAdventure.isEmpty() || this.listAnimal.isEmpty() || this.listPrincess.isEmpty() || this.listTech.isEmpty()) {
			while (i <= j * (TMDBRequester.MAXREQUEST / 2)) {
				String apiResponse = TMDBRequester.requestPageTopRated(i);

				JsonObject jsonObjectFactory = new JsonObject();
				MovieResponseAPI movieData = jsonObjectFactory.createObject(apiResponse)[0];
				List<MovieInfo> movies = movieData.getMovies();
				movieData.setMovies(movies);

				List<Integer> listAdventure = null;//new ArrayList<Integer>();
				List<Integer> listAnimal = null;//new ArrayList<Integer>();
				List<Integer> listPrincess = null;//new ArrayList<Integer>();
				List<Integer> listTech = null;//new ArrayList<Integer>();

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

						if (debug)
							System.out.println("Movie " + movie.getTitle() + " has " + labels.size() + " labels");

						for (String label : labels) {
							if (debug)
								System.out.println(">>>>>>>> " + label);
							if (label.equals("DOG") || label.equals("PONY") || label.equals("FISH")
									|| label.equals("LION") || label.equals("CAT") || label.equals("SNOOPY")) {
								/* Animal */
								if (debug)
									System.out.println("<<<<<<< Animal :: " + movieData.getMovies().indexOf(movie));
								if (listAnimal == null)
									listAnimal = new ArrayList<>();
								listAnimal.add(movieData.getMovies().indexOf(movie));
								this.listAnimal.put(i, listAnimal);
							} else if (label.equals("ROBOT") || label.equals("RACE CAR") || label.equals("MONSTER")) {

								/* Tech */
								if (debug)
									System.out.println("<<<<<<< Tech :: " + movieData.getMovies().indexOf(movie));
								if (listTech == null)
									listTech = new ArrayList<>();
								listTech.add(movieData.getMovies().indexOf(movie));
								this.listTech.put(i, listTech);
							} else if (label.equals("PRINCESS") || label.equals("PRINCE") || label.equals("UNICORN")
									|| label.equals("FAIRY") || label.equals("DWARF")) {

								/* Princess */
								if (debug)
									System.out.println("<<<<<<< Princess :: " + movieData.getMovies().indexOf(movie));
								if (listPrincess == null)
									listPrincess = new ArrayList<>();
								listPrincess.add(movieData.getMovies().indexOf(movie));
								this.listPrincess.put(i, listPrincess);
							} else if (label.equals("FRIENDSHIP") || label.equals("DRAGON") || label.equals("TOY")) {

								/* Adventure */
								if (debug)
									System.out.println("<<<<<<< Adventure :: " + movieData.getMovies().indexOf(movie));
								if (listAdventure == null)
									listAdventure = new ArrayList<>();
								listAdventure.add(movieData.getMovies().indexOf(movie));
								this.listAdventure.put(i, listAdventure);
							}
						}
					}

				if (listAdventure != null) {
					if (debug) {
						System.out.println("=======================");
						System.out.println("=======================");
						System.out.println("listAdventure :: " + i);
						System.out.println("=======================");
						System.out.println(this.listAdventure.get(i));
						System.out.println(listAdventure);
					}
					/*
					 * List<Integer> previous = this.listAdventure.put(i,
					 * listAdventure); if (previous != null)
					 * this.listAdventure.put(i, previous);
					 */
					this.listAdventure.putIfAbsent(i, listAdventure);
					if (debug) {
						System.out.println(this.listAdventure.get(i));
						System.out.println("=======================");
						System.out.println("=======================");
					}
				}
				if (listAnimal != null) {
					if (debug) {
						System.out.println("=======================");
						System.out.println("=======================");
						System.out.println("listAnimal :: " + i);
						System.out.println("=======================");
						System.out.println(this.listAnimal.get(i));
						System.out.println(listAnimal);
					}
					/*
					 * List<Integer> previous = this.listAnimal.put(i,
					 * listAnimal); if (previous != null)
					 * this.listAdventure.put(i, previous);
					 */
					this.listAnimal.putIfAbsent(i, listAnimal);
					if (debug) {
						System.out.println(this.listAnimal.get(i));
						System.out.println("=======================");
						System.out.println("=======================");
					}
				}
				if (listPrincess != null) {
					if (debug) {
						System.out.println("=======================");
						System.out.println("=======================");
						System.out.println("listPrincess :: " + i);
						System.out.println("=======================");
						System.out.println(this.listPrincess.get(i));
						System.out.println(listPrincess);
					}
					/*
					 * List<Integer> previous = this.listPrincess.put(i,
					 * listPrincess); if (previous != null)
					 * this.listAdventure.put(i, previous);
					 */
					this.listPrincess.putIfAbsent(i, listPrincess);
					if (debug) {
						System.out.println(this.listPrincess.get(i));
						System.out.println("=======================");
						System.out.println("=======================");
					}
				}
				if (listTech != null) {
					if (debug) {
						System.out.println("=======================");
						System.out.println("=======================");
						System.out.println("listTech :: " + i);
						System.out.println("=======================");
						System.out.println(this.listTech.get(i));
						System.out.println(listTech);
					}
					/*
					 * List<Integer> previous = this.listTech.put(i, listTech);
					 * if (previous != null) this.listAdventure.put(i,
					 * previous);
					 */
					this.listTech.putIfAbsent(i, listTech);
					if (debug) {
						System.out.println(this.listTech.get(i));
						System.out.println("=======================");
						System.out.println("=======================");
					}
				}

				pages.add(movieData);
				//if (debug)
					//printLists();

				i++;
			}
			j++;
		}

		if (debug)
			printLists();

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

	private boolean debug = false;

}
