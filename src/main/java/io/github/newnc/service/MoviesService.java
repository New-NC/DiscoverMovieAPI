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
import io.github.newnc.model.repository.MoviesRepository;
import io.github.newnc.model.repository.NewestMoviesRepository;
import io.github.newnc.model.repository.TopRatedMoviesRepository;
import io.github.newnc.util.DataReloadTimer;
import io.github.newnc.model.repository.CompanyMoviesRepository;

@RestController
public class MoviesService {
	
	/*
		ESTA É NOSSA CLASSE PRINCIPAL, E DEVE SER RESPEITADA E TRATADA COM CARINHO
	*/

	private List<MoviesRepository> repositories = null;

	private int n_categories = 4;

	
	public MoviesService() {
		System.out.println("MoviesService");

		// If you don't want to see log messages, comment the line below.
		// io.github.newnc.debug.Print.activate();

		repositories = new ArrayList<>();
		
		start();
		
		DataReloadTimer dataReloadTimer = new DataReloadTimer(repositories);
		
	}
	
	
	public void start(){
		boolean flag_npe;
		
		repositories.add(NewestMoviesRepository.getInstance());
		repositories.add(TopRatedMoviesRepository.getInstance());
		repositories.add(CompanyMoviesRepository.getInstance());

		try {
			
			for (MoviesRepository r : repositories){
				
				System.out.println("Calling updateIfNeeded() in "+r.getClass());
				
				flag_npe = true;
				
				while (flag_npe) {
					
					flag_npe = false;
					
					try {
						r.updateIfNeeded();
					} catch (NullPointerException e) {
						flag_npe = true;
						e.printStackTrace();
					}
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public MovieResponse[] movies() {
		System.out.println("movies()");

		/* not needed anymore
		for (MoviesRepository r : repositories) {
			try {
				r.updateIfNeeded();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//*/

		MovieResponse[] movieData = { repositories.get(0).getPage(1), repositories.get(1).getPage(1) };

		return movieData;
	}

	@RequestMapping(value = "/movies/covers", method = RequestMethod.GET)
	public String[] covers(HttpServletResponse response) {
		System.out.println("/movies/covers");

		String[] covers;
		int numRepos = repositories.size() - 1; // -1 because of companymovies

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

		MoviesRepository repo = null;
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
		System.out.println("/movies/companies");

		String[] companies = repositories.get(2).getRandomCoversFromEachCategory();

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

		MoviesRepository repo = null;
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
		for (MoviesRepository mR : repositories) {
			try {
				mR.forceUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public void clear() {
		Iterator<MoviesRepository> repository = repositories.iterator();
		
		while (repository.hasNext())
			((MoviesRepository) repository.next()).clear();
	}
}
