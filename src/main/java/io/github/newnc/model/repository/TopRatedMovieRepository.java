package io.github.newnc.model.repository;

import java.util.ArrayList;

import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.DataReloadTimer;
import io.github.newnc.util.TMDBRequester;

public class TopRatedMovieRepository extends MovieRepository {

	@Override
	protected void update() throws Exception {

		int i = 1;
		int j = 1;
		while (this.listAdventure.isEmpty() || this.listAnimal.isEmpty() || this.listPrincess.isEmpty() || this.listTech.isEmpty()) {
			while (i <= j * (TMDBRequester.MAXREQUEST / 2)) {
				String apiResponse = TMDBRequester.requestPageTopRated(i);

				MovieResponseAPI movieData = categorySetter(i, apiResponse);

				movieResponsePages.add(movieData);
				
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
		movieResponsePages = new ArrayList<>();

		addObserver(DataReloadTimer.getTimer());
	}

	public void printLists() {
		System.out.println("listAdventure Last Call TOPRATED");
		for (int j = 0; j < listAdventure.size(); j++)
			if (listAdventure.get(j) != null && listAdventure.get(j).size() > 0)
				for (Integer i : listAdventure.get(j)) {
					if (movieResponsePages.get(j).getMovies().size() > i.intValue())
						System.out.println(movieResponsePages.get(j).getMovies().get(i.intValue()).getTitle());
				}
		System.out.println("\nlistAnimal");
		for (int j = 0; j < listAnimal.size(); j++)
			if (listAnimal.get(j) != null && listAnimal.get(j).size() > 0)
				for (Integer i : listAnimal.get(j)) {
					if (movieResponsePages.get(j).getMovies().size() > i.intValue())
						System.out.println(movieResponsePages.get(j).getMovies().get(i.intValue()).getTitle());
				}
		System.out.println("\nlistPrincess");
		for (int j = 0; j < listPrincess.size(); j++)
			if (listPrincess.get(j) != null && listPrincess.get(j).size() > 0)
				for (Integer i : listPrincess.get(j)) {
					if (movieResponsePages.get(j).getMovies().size() > i.intValue())
						System.out.println(movieResponsePages.get(j).getMovies().get(i.intValue()).getTitle());
				}
		System.out.println("\nlistTech");
		for (int j = 0; j < listTech.size(); j++)
			if (listTech.get(j) != null && listTech.get(j).size() > 0)
				for (Integer i : listTech.get(j)) {
					if (movieResponsePages.get(j).getMovies().size() > i.intValue())
						System.out.println(movieResponsePages.get(j).getMovies().get(i.intValue()).getTitle());
				}
		System.out.println("");
	}

	private boolean debug = true;

}
