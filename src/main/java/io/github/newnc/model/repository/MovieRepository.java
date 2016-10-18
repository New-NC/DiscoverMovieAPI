package io.github.newnc.model.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.newnc.model.AbstractRepository;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.DataReloadTimer;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.TMDBRequester;

/**
 * This class represents an in-memory repository of movies gets from TMDB API.
 *
 * This class implements the singleton pattern.
 *
 * @see <a href="http://www.oodesign.com/singleton-pattern.html">Singleton Pattern</a>
 */
public class MovieRepository extends AbstractRepository {

	/**
	 * This fields represents a list of pages of the response from TMDB API.
	 */
	protected List<MovieResponseAPI> pages;

	/**
	 * Returns a list of pages of this <code>MovieRepository</code> instance.
	 *
	 * @return a list of pages of this <code>MovieRepository</code> instance.
	 */
	public List<MovieResponseAPI> getPages() {
		return pages;
	}

	/**
	 * Returns an iterator for the list of <code>pages</code> of this <code>
	 * MovieRepository</code> instance.
	 *
	 * @return an iterator for the list of <code>pages</code> of this <code>
	 * MovieRepository</code> instance.
	 */
	public Iterator<MovieResponseAPI> getIterator() {
		return pages.iterator();
	}

	/**
	 * Returns a specific <code>page</code> of this <code>MovieRepository
	 * </code> instance.
	 *
	 * @param numPage the number of the <code>page</code>.
	 * @return a specific <code>page</code> of this <code>MovieRepository
	 * </code> instance.
	 */
	public MovieResponseAPI getPage(int numPage) {
		int i;
		for (i=0; i<pages.size(); i++)
			if (pages.get(i).getPage() == numPage)
				return pages.get(i);
		
		return null;
	}

	@Override
	protected void update() throws Exception {
		System.out.println("CALLED: update() " + System.currentTimeMillis());

		for (int i = 1; i <= TMDBRequester.MAXREQUEST; i++) {
			System.out.println("Get page: "+i);

			String apiResponse = TMDBRequester.requestPage(i);

			JsonObject jsonObjectFactory = new JsonObject();
			MovieResponseAPI movieData = jsonObjectFactory.createObject(apiResponse)[0];
			movieData.setMovies(movieData.getMovies());

			pages.add(movieData);

			System.out.println("---- Teste -----");
			System.out.println(pages.get(pages.size() - 1).getMovies().get(0).getLabels());
		}

		setChanged();
		notifyObservers();

		System.out.println("---- MovieRepository -----");
	}

	@Override
	public void updateIfNeeded() throws Exception {
		if(debug) System.out.println("updateIfNeeded() "+this.getClass());
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
	
	private boolean debug = true;

	public String[] getOneCoverbyBucket() {
		String[] covers = new String[4];

		covers[0] = "";
		for (int i = 0; i < listAdventure.size(); i++) {
			List<Integer> list = listAdventure.get(i);
			if (list != null)
				for (Integer j : list) {
					covers[0] = pages.get(i).getMovies().get(j.intValue()).getPoster_path();
					break;
				}
			
			if (covers[0] != null && !covers[0].isEmpty()) break;
		}

		covers[1] = "";
		for (int i = 0; i < listAnimal.size(); i++) {
			List<Integer> list = listAnimal.get(i);
			if (list != null)
				for (Integer j : list) {
					covers[1] = pages.get(i).getMovies().get(j.intValue()).getPoster_path();
					break;
				}
			
			if (covers[1] != null && !covers[1].isEmpty()) break;
		}

		covers[2] = "";
		for (int i = 0; i < listPrincess.size(); i++) {
			List<Integer> list = listPrincess.get(i);
			if (list != null)
				for (Integer j : list) {
					covers[2] = pages.get(i).getMovies().get(j.intValue()).getPoster_path();
					break;
				}
			
			if (covers[2] != null && !covers[2].isEmpty()) break;
		}

		covers[3] = "";
		for (int i = 0; i < listTech.size(); i++) {
			List<Integer> list = listTech.get(i);
			if (list != null)
				for (Integer j : list) {
					covers[3] = pages.get(i).getMovies().get(j.intValue()).getPoster_path();
					break;
				}
			
			if (covers[3] != null && !covers[3].isEmpty()) break;
		}
		
		return covers;
	}

}
