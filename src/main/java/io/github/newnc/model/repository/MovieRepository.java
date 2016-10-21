package io.github.newnc.model.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.github.newnc.model.AbstractRepository;
import io.github.newnc.model.MovieResponse;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.DataReloadTimer;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.TMDBRequester;

/**
 * This class represents an in-memory repository of movies gets from TMDB API.
 *
 * This class implements the singleton pattern.
 *
 * @see <a href="http://www.oodesign.com/singleton-pattern.html">Singleton
 *      Pattern</a>
 */
public class MovieRepository extends AbstractRepository {

	/**
	 * This fields represents a list of pages of the response from TMDB API.
	 */
	protected List<MovieResponse> pages;

	/**
	 * Returns a list of pages of this <code>MovieRepository</code> instance.
	 *
	 * @return a list of pages of this <code>MovieRepository</code> instance.
	 */
	public List<MovieResponse> getPages() {
		return pages;
	}

	/**
	 * Returns an iterator for the list of <code>pages</code> of this <code>
	 * MovieRepository</code> instance.
	 *
	 * @return an iterator for the list of <code>pages</code> of this <code>
	 * MovieRepository</code> instance.
	 */
	public Iterator<MovieResponse> getIterator() {
		return pages.iterator();
	}

	/**
	 * Returns a specific <code>page</code> of this <code>MovieRepository
	 * </code> instance.
	 *
	 * @param numPage
	 *            the number of the <code>page</code>.
	 * @return a specific <code>page</code> of this <code>MovieRepository
	 * </code> instance.
	 */
	public MovieResponse getPage(int numPage) {
		int i;
		for (i = 0; i < pages.size(); i++)
			if (pages.get(i).getPage() == numPage)
				return pages.get(i);

		return null;
	}

	@Override
	protected void update() throws Exception {
		System.out.println("UPDATE: Movies at " + System.currentTimeMillis());

		for (int i = 1; i <= TMDBRequester.MAXREQUEST; i++) {
			String apiResponse = TMDBRequester.requestPage(i);

			JsonObject jsonObjectFactory = new JsonObject();
			MovieResponseAPI movieDataAPI = jsonObjectFactory.createObject(apiResponse);
			MovieResponse movieData = MovieResponse.createMovieResponse(movieDataAPI);

			pages.add(movieData);
		}

		setChanged();
		notifyObservers();
	}

	@Override
	public void updateIfNeeded() throws Exception {
		if (isEmpty())
			update();
	}

	@Override
	public void forceUpdate() throws Exception {
		clear();

		update();
	}

	@Override
	public void clear() {
		pages.clear();

		setChanged();
		notifyObservers();
	}

	@Override
	public boolean isEmpty() {
		return pages.isEmpty();
	}

	/**
	 * This fields represents a instance of this class.
	 */
	private static MovieRepository instance;

	/**
	 * Returns an instance of this class.
	 *
	 * @return an instance of this class.
	 */
	public static synchronized MovieRepository getInstance() {
		if (instance == null)
			instance = new MovieRepository();
		return instance;
	}

	/**
	 * Default constructor.
	 */
	protected MovieRepository() {
		pages = new ArrayList<>();

		addObserver(DataReloadTimer.getTimer());
	}

	public String[] getOneCoverbyBucket() {
		String[] covers = new String[4];

		covers[0] = "";
		for (int i : listAdventure.keySet()) {
			List<Integer> list = listAdventure.get(i);
			if (list != null)
				covers[0] = pages
				.get(listAdventure.get(i).get(listAdventure.get(i).size() - 1))
				.getMovies()
				.get(list.get(0))
				.getPoster_path();
			/*
			 * for (Integer j : list) { if
			 * (pages.get(i).getMovies().get(j.intValue()).getTitle().
			 * toUpperCase().contains("JUNGLE") ||
			 * pages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase
			 * ().contains("ADVENTURE") ||
			 * pages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase
			 * ().contains("MICKEY")) { covers[0] =
			 * pages.get(i).getMovies().get(j.intValue()).getPoster_path();
			 *
			 * break; } }
			 */

			if (covers[0] != null && !covers[0].isEmpty())
				break;
		}

		covers[1] = "";
		for (int i : listAnimal.keySet()) {
			List<Integer> list = listAnimal.get(i);
			if (list != null)
				covers[1] = pages
						.get(listAnimal.get(i).get(listAnimal.get(i).size() - 1))
						.getMovies()
						.get(list.get(0))
						.getPoster_path();
			/*
			 * for (Integer j : list) { if
			 * (pages.get(i).getMovies().get(j.intValue()).getTitle().
			 * toUpperCase().contains("SCOOBY")){ //||
			 * pages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase
			 * ().contains("SNOOPY") ||
			 * pages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase
			 * ().contains("GARFIELD")) { covers[1] =
			 * pages.get(i).getMovies().get(j.intValue()).getPoster_path();
			 *
			 * break; } }
			 */

			if (covers[1] != null && !covers[1].isEmpty())
				break;
		}

		covers[2] = "";
		for (int i : listPrincess.keySet()) {
			List<Integer> list = listPrincess.get(i);
			if (list != null)
				covers[2] = pages
						.get(listPrincess.get(i).get(listPrincess.get(i).size() - 1))
						.getMovies()
						.get(list.get(0))
						.getPoster_path();
			/*
			 * for (Integer j : list) { if
			 * (pages.get(i).getMovies().get(j.intValue()).getTitle().
			 * toUpperCase().contains("BEAUTY") ||
			 * pages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase
			 * ().contains("MERMAID") ||
			 * pages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase
			 * ().contains("LITTLE")) { covers[2] =
			 * pages.get(i).getMovies().get(j.intValue()).getPoster_path();
			 *
			 * break; } }
			 */

			if (covers[2] != null && !covers[2].isEmpty())
				break;
		}

		covers[3] = "";
		for (int i : listTech.keySet()) {
			List<Integer> list = listTech.get(i);
			if (list != null)
				covers[3] = pages
						.get(listTech.get(i).get(listTech.get(i).size() - 1))
						.getMovies()
						.get(list.get(0))
						.getPoster_path();
			/*
			 * for (Integer j : list) { if
			 * (pages.get(i).getMovies().get(j.intValue()).getTitle().
			 * toUpperCase().contains("WALL") ||
			 * pages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase
			 * ().contains("SPACE") ||
			 * pages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase
			 * ().contains("APES")) { covers[3] =
			 * pages.get(i).getMovies().get(j.intValue()).getPoster_path();
			 *
			 * break; } }
			 */

			if (covers[3] != null && !covers[3].isEmpty())
				break;
		}

		return covers;
	}

	public String[] getCoversFromBucket(Integer genreId) {
		String[] covers = new String[5];

		Map<Integer, List<Integer>> bucketMap;
		switch (genreId) {
		case 0:
			bucketMap = listAdventure;
			break;
		case 1:
			bucketMap = listAnimal;
			break;
		case 2:
			bucketMap = listPrincess;
			break;
		case 3:
			bucketMap = listTech;
			break;
		default:
			return null;
		}

		int i = -1;
		for (Integer j : bucketMap.keySet()) {
			List<Integer> list = bucketMap.get(j);
			if (list != null)
				for (Integer k : list) {
					if (i >= 0) {
						covers[i] = pages.get(j).getMovies().get(k.intValue()).getPoster_path();
					}

					i++;

					if (i == 5)
						break;
				}
			if (i == 5)
				break;
		}

		return covers;
	}

}
