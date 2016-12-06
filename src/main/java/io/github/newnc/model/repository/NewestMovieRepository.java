package io.github.newnc.model.repository;

import io.github.newnc.model.MovieResponse;
import io.github.newnc.util.DataReloadTimer;
import io.github.newnc.util.TMDBRequester;

public class NewestMovieRepository extends MovieRepository {

	/**
	 * This fields represents a instance of this class.
	 */
	private static NewestMovieRepository instance;


	/* Methods */

	@Override
	protected void update() throws InterruptedException {

		int i = 1;
		int j = 1;
		
		int qtyCat[] = new int[qtyCategories];
		
		qtyCat[0] = 0;
		qtyCat[1] = 0;
		qtyCat[2] = 0;
		qtyCat[3] = 0;
		
		while (	notFilledAllCategories(qtyCat) ) {
			while (i <= j * (TMDBRequester.MAXREQUEST / 4)) {
				String apiResponse = TMDBRequester.requestPageNewest(i);

				MovieResponse movieData = categorySetter(apiResponse, qtyCat);

				movieResponsePages.add(movieData);

				i++;
			}
			j++;
		}

		setChanged();
		notifyObservers();

		System.out.println("---- end of update() in NewestMovieRepository -----");
	}


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
		init();

		addObserver(DataReloadTimer.getTimer());
	}
}
