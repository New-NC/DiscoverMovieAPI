package io.github.newnc.model.repository;

import java.util.ArrayList;

import io.github.newnc.debug.Print;
import io.github.newnc.model.MovieResponse;
import io.github.newnc.util.DataReloadTimer;
import io.github.newnc.util.TMDBRequester;

public class TopRatedMovieRepository extends MovieRepository {

	@Override
	protected void update() throws Exception {

		int i = 1;
		int j = 1;
		
		int qtyCat[] = new int[qtyCategories];
		
		qtyCat[0] = 0;
		qtyCat[1] = 0;
		qtyCat[2] = 0;
		qtyCat[3] = 0;
		
		while (	notFilledAllCategories(qtyCat) ) {
			while (i <= j * (TMDBRequester.MAXREQUEST / 4)) {
				String apiResponse = TMDBRequester.requestPageTopRated(i);

				MovieResponse movieData = categorySetter(apiResponse, qtyCat);

				movieResponsePages.add(movieData);

				i++;
			}
			j++;
		}

		//Print.allCategoriesList(movieResponsePages, this.listAdventure, this.listAnimal, this.listPrincess, this.listTech);

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
}
