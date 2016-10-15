package io.github.newnc.model.repository;

import java.io.IOException;

import io.github.newnc.model.MovieResponseAPI;
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
	}

}
