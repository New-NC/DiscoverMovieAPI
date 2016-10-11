package io.github.newnc.model.repository;

import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.TMDBRequester;

public class NewestMovieRepository extends MovieRepository {
	
	@Override
	protected void update() {
		System.out.println("update " + System.currentTimeMillis());
		
		for (int i = 1; i <= TMDBRequester.MAXREQUEST / 2; i++) {
			String apiResponse = TMDBRequester.requestPageNewest(i);

			JsonObject jsonObjectFactory = new JsonObject();
			MovieResponseAPI[] movieData = jsonObjectFactory.createObject(apiResponse);
			
			pages.add(movieData[0]);
		}
		
		setChanged();
		notifyObservers();
	}
}
