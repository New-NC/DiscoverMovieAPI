package io.github.newnc.service;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.model.repository.MovieRepository;
import io.github.newnc.model.repository.NewestMovieRepository;
import io.github.newnc.model.repository.TopRatedMovieRepository;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.TMDBRequester;

@RestController
public class MoviesService {

	@RequestMapping(value = "/movies/", method = RequestMethod.GET)
	public MovieResponseAPI[] movies() {
		MovieRepository repo = NewestMovieRepository.getInstance();
		repo.updateIfNeeded();
		
		repo = TopRatedMovieRepository.getInstance();
		repo.updateIfNeeded();

		MovieResponseAPI[] movieData = new MovieResponseAPI[1];
		movieData[0] = repo.getPage(1);
		System.out.println("AEHOOO!!");

		return movieData;
	}
	
	@RequestMapping(value = "/movies/covers/", method = RequestMethod.GET)
	public String[] covers(HttpServletResponse response) {
		String[] covers = new String[2];

		try {
			covers[0] = TopRatedMovieRepository
					.getInstance()
					.getPage(1)
					.getMovies()
					.get(0)
					.getPoster_path();
			covers[1] = NewestMovieRepository
					.getInstance()
					.getPage(1)
					.getMovies()
					.get(0)
					.getPoster_path();
			
			if ((covers[0] == null || covers[0].isEmpty()) && (covers[1] == null || covers[1].isEmpty()))
				response.setStatus(HttpStatus.SC_NO_CONTENT);
		} catch (NullPointerException npe) {
			response.setStatus(HttpStatus.SC_NO_CONTENT);
		}
		
		return covers;
	}

	@RequestMapping(value = "/movies/{page}", method = RequestMethod.GET)
	public MovieResponseAPI[] moviesAtPage(@PathVariable Integer page) {
		String apiRequest = TMDBRequester.requestPage(page);

		JsonObject jsonObjectFactory = new JsonObject();
		MovieResponseAPI[] movieData = jsonObjectFactory.createObject(apiRequest);

		return movieData;
	}

	@RequestMapping(value = "/reload", method = RequestMethod.GET)
	public void reload() {
		NewestMovieRepository.getInstance().forceUpdate();
		TopRatedMovieRepository.getInstance().forceUpdate();
	}
	
	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public void clear() {
		NewestMovieRepository.getInstance().clear();;
		TopRatedMovieRepository.getInstance().clear();
	}
}
