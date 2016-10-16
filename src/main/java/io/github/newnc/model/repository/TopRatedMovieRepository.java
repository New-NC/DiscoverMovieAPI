package io.github.newnc.model.repository;

import java.io.IOException;
import java.util.ArrayList;

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
			movieData.setMovies(movieData.getMovies());

			pages.add(movieData);
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

}
