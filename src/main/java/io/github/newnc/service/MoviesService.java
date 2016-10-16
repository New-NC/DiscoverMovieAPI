package io.github.newnc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.model.repository.MovieRepository;
import io.github.newnc.model.repository.NewestMovieRepository;
import io.github.newnc.model.repository.TopRatedMovieRepository;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.TMDBRequester;

@RestController
public class MoviesService {
	
	private List<MovieRepository> repositories;
	
	public MoviesService() {
		repositories = new ArrayList<>();
		
		repositories.add(NewestMovieRepository.getInstance());
		repositories.add(TopRatedMovieRepository.getInstance());
	}

	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public MovieResponseAPI[] movies() {
		MovieRepository repo = null;
		
		Iterator<MovieRepository> repository = repositories.iterator();
		while (repository.hasNext())
			try {
				(repo = (MovieRepository) repository.next()).updateIfNeeded();
			} catch (Exception e) {
				e.printStackTrace();
			}

		MovieResponseAPI[] movieData = new MovieResponseAPI[1];
		movieData[0] = repo.getPage(1);
		System.out.println("AEHOOO!!");

		return movieData;
	}
	
	@RequestMapping(value = "/movies/covers", method = RequestMethod.GET)
	public String[] covers(HttpServletResponse response) {
		String[] covers;
		int numRepos = repositories.size();
		
		covers = new String[numRepos];

		try {
			for (int i = 0; i < numRepos; i++) {
				covers[i] = repositories.get(i)
						.getPage(1)
						.getMovies()
						.get(0)
						.getPoster_path();
			}
			
			for (int i = 0; i < numRepos; i++) {
				if (covers[i] == null || covers[i].isEmpty()) {
					response.setStatus(HttpStatus.SC_NO_CONTENT);
					
					break;
				}
			}
		} catch (NullPointerException npe) {
			response.setStatus(HttpStatus.SC_NO_CONTENT);
		}
		
		return covers;
	}
	
	@RequestMapping(value = "/movies/categories/{id}", method = RequestMethod.GET)
	public Map<String, String> coversCategories(HttpServletResponse response, @PathVariable("id") Integer repositoryId ) {
		Map<String, String> categories = new HashMap<String, String>();
		
		System.out.println("/movies/categories/{id}="+repositoryId);
		
		try {
			List<MovieInfo> movies = repositories.get(repositoryId).getPage(1).getMovies();
			
			System.out.println("pegamos " + movies.size() + " filmes");
			
			for (MovieInfo movie : movies) {
				//System.out.println(movie.stringify());
				System.out.println("--------------------");
				System.out.println("Titulo: "+movie.getTitle());
				List<String> genre = movie.getLabels();
				
				System.out.println("Labels: " + genre);
				
				if(genre != null){
					System.out.println("qtde labels: " + genre.size());
					if (genre.size() > 0) {
						categories.put(genre.get(0), movie.getPoster_path());
						System.out.println(genre.get(0) + " - " + movie.getPoster_path()+"\n");
					}
				}
				
				if (categories.size() == 4)
					break;
				else
					System.out.println("< 4");
			}
		} catch (IndexOutOfBoundsException ioobe) {
			response.setStatus(HttpStatus.SC_NO_CONTENT);
		}
		
		return categories;
	}
	
	public MovieInfo[] getResults() {
		MovieInfo[] movies = new MovieInfo[5];
		
		return movies;
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
		Iterator<MovieRepository> repository = repositories.iterator();
		while (repository.hasNext())
			try {
				((MovieRepository) repository.next()).forceUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public void clear() {
		Iterator<MovieRepository> repository = repositories.iterator();
		while (repository.hasNext())
			((MovieRepository) repository.next()).clear();;
	}
}
