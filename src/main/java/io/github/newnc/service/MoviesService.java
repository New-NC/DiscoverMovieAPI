package io.github.newnc.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.newnc.model.MovieRepository;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.TMDBRequester;

@RestController
public class MoviesService {

	@RequestMapping(value = "/movies/", method = RequestMethod.GET)
	public MovieResponseAPI[] doTMDB() {
		MovieRepository repo = MovieRepository.getInstance();
		repo.updateIfNeeded();

		MovieResponseAPI[] movieData = new MovieResponseAPI[1];
		movieData[0] = repo.getPage(1);

		return movieData;
	}

	@RequestMapping(value = "/movies/{page}", method = RequestMethod.GET)
	public MovieResponseAPI[] doTMDBNaPagina(@PathVariable Integer page) {
		String apiRequest = TMDBRequester.requestPage(page);

		JsonObject jsonObjectFactory = new JsonObject();
		MovieResponseAPI[] movieData = jsonObjectFactory.createObject(apiRequest);

		return movieData;
	}
	
	@RequestMapping(value = "/reload", method = RequestMethod.GET)
	public void reload() {
		MovieRepository.getInstance().forceUpdate();
	}
}
