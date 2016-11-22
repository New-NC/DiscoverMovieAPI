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
import io.github.newnc.model.repository.CompanyMoviesRepository;
import io.github.newnc.model.repository.MovieRepository;
import io.github.newnc.model.repository.NewestMovieRepository;
import io.github.newnc.model.repository.TopRatedMovieRepository;

@RestController
public class MoviesService {

	private List<MovieRepository> repositories;
	private CompanyMoviesRepository companyMoviesRepository;

	private int n_categories = 4;

	private boolean debug = true;

	public MoviesService() {
		if (debug)
			System.out.println("MoviesService");

		// If you don't want to see log messages, comment the line below.
		// io.github.newnc.debug.Print.activate();

		repositories = new ArrayList<>();

		repositories.add(NewestMovieRepository.getInstance());
		repositories.add(TopRatedMovieRepository.getInstance());

		try {
			boolean npe = true;
			for (MovieRepository r : repositories)
				while (npe) {
					npe = false;
					try {
						r.updateIfNeeded();
					} catch (NullPointerException e) {
						npe = true;
						e.printStackTrace();
					}
				}
			
			companyMoviesRepository = CompanyMoviesRepository.getInstance();
			npe = true;
			while (npe) {
				npe = false;
				try {
					companyMoviesRepository.updateIfNeeded();
				} catch (NullPointerException e) {
					npe = true;
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public MovieResponse[] movies() {
		if (debug)
			System.out.println("movies()");

		for (MovieRepository r : repositories) {
			try {
				r.updateIfNeeded();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		MovieResponse[] movieData = { repositories.get(0).getPage(1), repositories.get(1).getPage(1) };

		return movieData;
	}

	@RequestMapping(value = "/movies/covers", method = RequestMethod.GET)
	public String[] covers(HttpServletResponse response) {
		if (debug)
			System.out.println("/movies/covers");

		String[] covers;
		int numRepos = repositories.size();

		covers = new String[numRepos];

		try {
			for (int i = 0; i < numRepos; i++) {
				covers[i] = repositories.get(i).getRandomCover();
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
	public String[] coversCategories(HttpServletResponse response, @PathVariable("id") Integer repositoryId) {
		System.out.println("/movies/categories/{id}=" + repositoryId);

		String[] covers = new String[n_categories];

		MovieRepository repo = null;
		switch (repositoryId) {
		case 0:
			repo = repositories.get(repositoryId);
			break;

		case 1:
			repo = repositories.get(repositoryId);
			break;
		}

		if (repo != null) {
			covers = repo.getRandomCoversFromEachCategory();
		}

		return covers;
	}

	@RequestMapping(value = "/movies/companies", method = RequestMethod.GET)
	public String[] companies(HttpServletResponse response) {
		if (debug)
			System.out.println("/movies/companies");

		String[] companies = companyMoviesRepository.getRandomCoversFromEachCategory();

		for (int i = 0; i < 4; i++) {
			if (companies[i] == null || companies[i].isEmpty()) {
				response.setStatus(HttpStatus.SC_NO_CONTENT);

				break;
			}
		}

		return companies;
	}

	@RequestMapping(value = "/movies/results/{repo}/{genre}", method = RequestMethod.GET)
	public String[] getResults(@PathVariable("repo") Integer repositoryId, @PathVariable("genre") Integer genreId) {
		System.out.println("/movies/results/{repo}=" + repositoryId + "/{genre}=" + genreId);

		String[] covers = new String[5];

		MovieRepository repo = null;
		switch (repositoryId) {
		case 0:
			repo = repositories.get(repositoryId);
			break;

		case 1:
			repo = repositories.get(repositoryId);
			break;
		}

		if (repo != null) {
			covers = repo.getRandomCoversForResult(genreId);
		}

		return covers;
	}

	@RequestMapping(value = "/reload", method = RequestMethod.GET)
	public void reload() {
		for (MovieRepository mR : repositories) {
			try {
				mR.forceUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public void clear() {
		Iterator<MovieRepository> repository = repositories.iterator();
		while (repository.hasNext())
			((MovieRepository) repository.next()).clear();
		;
	}
}
