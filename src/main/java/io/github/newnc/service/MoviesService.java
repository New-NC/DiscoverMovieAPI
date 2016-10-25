package io.github.newnc.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.newnc.model.MovieResponse;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.model.repository.MovieRepository;
import io.github.newnc.model.repository.NewestMovieRepository;
import io.github.newnc.model.repository.TopRatedMovieRepository;

@RestController
public class MoviesService {

	private List<MovieRepository> repositories;

	private boolean debug = true;

	public MoviesService() {
		if(debug) System.out.println("MoviesService");

		repositories = new ArrayList<>();

		repositories.add(NewestMovieRepository.getInstance());
		repositories.add(TopRatedMovieRepository.getInstance());

		for(MovieRepository r : repositories){
			try {
				r.updateIfNeeded();;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public MovieResponseAPI movies() {
		if(debug) System.out.println("movies()");

		for(MovieRepository r : repositories){
			try {
				r.updateIfNeeded();;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		MovieResponseAPI movieData = repositories.get(0).getPage(1);

		if(debug) System.out.println("AEHOOO!!");

		System.out.println(repositories.size());

		return movieData;
	}

	@RequestMapping(value = "/movies/covers", method = RequestMethod.GET)
	public String[] covers(HttpServletResponse response) {
		if(debug) System.out.println("/movies/covers");

		String[] covers;
		int numRepos = repositories.size();

		covers = new String[numRepos];

		try {
			for (int i = 0; i < numRepos; i++) {
				covers[i] = repositories.get(i).getPage(1).getMovies()
								.get(0)
								.getPoster_path();
				System.out.println(repositories.get(i));
				System.out.println(covers[i]);
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
	public String[] coversCategories(HttpServletResponse response, @PathVariable("id") Integer repositoryId ) {
		System.out.println("/movies/categories/{id}="+repositoryId);
		
		String[] covers = new String[4];

		MovieRepository repo = null;
		if (repositoryId == 0)
			repo = (NewestMovieRepository) repositories.get(repositoryId);
		else if (repositoryId == 1)
			repo = (TopRatedMovieRepository) repositories.get(repositoryId);

		if (repo != null) {
			covers[0] = repo.getCoverFromBucket(0);
			covers[1] = repo.getCoverFromBucket(1);
			covers[2] = repo.getCoverFromBucket(2);
			covers[3] = repo.getCoverFromBucket(3);
		}

		return covers;
	}

	@RequestMapping(value = "/movies/results/{repo}/{genre}", method = RequestMethod.GET)
	public String[] getResults(@PathVariable("repo") Integer repositoryId, @PathVariable("genre") Integer genreId) {
		System.out.println("/movies/results/{repo}="+repositoryId+"/{genre}="+genreId);

		String[] moviesCovers = new String[5];

		MovieRepository repo = null;
		if (repositoryId == 0)
			repo = (NewestMovieRepository) repositories.get(repositoryId);
		else if (repositoryId == 1)
			repo = (TopRatedMovieRepository) repositories.get(repositoryId);

		if (repo != null) {
			return repo.getCoversFromBucket(genreId);
		}

		return moviesCovers;
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
