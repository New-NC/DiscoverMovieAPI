package io.github.newnc.model.repository;

import io.github.newnc.debug.Print;
import io.github.newnc.model.MovieResponse;
import io.github.newnc.util.TMDBRequester;

public class NewestMoviesRepository extends CategoryMoviesRepository{

	/**
	 * This fields represents a instance of this class.
	 */
	private static NewestMoviesRepository instance;

	/* Methods */

	/**
	 * Returns an instance of this class.
	 *
	 * @return an instance of this class.
	 */
	public static NewestMoviesRepository getInstance(){
		if(instance == null) instance = new NewestMoviesRepository();
		return instance;
	}

	@Override
	protected void update(){
		System.out.println("Update() of NewestMovieRepository");

		int i = 1;
		int j = 1;
		boolean ret;

		int qtyCat[] = new int[qtyCategories];

		qtyCat[0] = 0;
		qtyCat[1] = 0;
		qtyCat[2] = 0;
		qtyCat[3] = 0;

		ret = notFilledAllCategories(qtyCat);

		while(ret){

			while(i <= (j * TMDBRequester.MAXREQUEST)){
				String apiResponse = TMDBRequester.requestPageNewest(i);

				MovieResponse movieData = categorySetter(apiResponse, qtyCat);

				movieResponsePages.add(movieData);

				i++;
			}

			ret = notFilledAllCategories(qtyCat);

			j++;

			try{
				System.out.println("Waiting for the API request timelimit (10s)");
				Thread.sleep(10000);
			}catch(InterruptedException e){
				System.out.println("Couldn't sleep");
				e.printStackTrace();
			}

		}

		if(Print.debugNewestMoviesRepository)
			System.out.println("NewestMovies >> moviesResponsePages.size() = " + movieResponsePages.size());

		System.out.println("---- end of update() in NewestMovieRepository -----");
	}

}
