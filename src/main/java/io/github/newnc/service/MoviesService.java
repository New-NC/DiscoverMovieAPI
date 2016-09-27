package io.github.newnc.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.TMDBRequester;

@RestController
public class MoviesService {

  @RequestMapping(value = "/movies/", method = RequestMethod.GET)
  public MovieResponseAPI[] doTMDB() {
	String apiResponse = TMDBRequester.requestPage(1);

	JsonObject jsonObjectFactory = new JsonObject();
	MovieResponseAPI[] movieDate = jsonObjectFactory.createObject(apiResponse);

	return movieDate;
  }

  @RequestMapping(value = "/movies/{page}", method = RequestMethod.GET)
  public MovieResponseAPI[] doTMDBNaPagina(@PathVariable Integer page) {
	String apiRequest = TMDBRequester.requestPage(page);

	JsonObject jsonObjectFactory = new JsonObject();
	MovieResponseAPI[] movieDate = jsonObjectFactory.createObject(apiRequest);

	return movieDate;
  }
}
