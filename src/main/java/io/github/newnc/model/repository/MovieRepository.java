package io.github.newnc.model.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.DataReloadTimer;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.KeyWordsList;
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
	
	protected Map<Integer, List<Integer>> listAnimal    = new HashMap<Integer, List<Integer>>();
	protected Map<Integer, List<Integer>> listTech		= new HashMap<Integer, List<Integer>>();
	protected Map<Integer, List<Integer>> listPrincess  = new HashMap<Integer, List<Integer>>();
	protected Map<Integer, List<Integer>> listAdventure = new HashMap<Integer, List<Integer>>();
	
	protected KeyWordsList keyWordsList = new KeyWordsList();
	
	/**
	 * This fields represents a list of pages of the response from TMDB API.
	 */
	protected List<MovieResponseAPI> movieResponsePages;

	/**
	 * Returns a list of pages of this <code>MovieRepository</code> instance.
	 *
	 * @return a list of pages of this <code>MovieRepository</code> instance.
	 */
	public List<MovieResponseAPI> getPages() {
		return movieResponsePages;
	}

	/**
	 * Returns an iterator for the list of <code>pages</code> of this <code>
	 * MovieRepository</code> instance.
	 *
	 * @return an iterator for the list of <code>pages</code> of this <code>
	 * MovieRepository</code> instance.
	 */
	public Iterator<MovieResponseAPI> getIterator() {
		return movieResponsePages.iterator();
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
	public MovieResponseAPI getPage(int numPage) {
		int i;
		for (i = 0; i < movieResponsePages.size(); i++)
			if (movieResponsePages.get(i).getPage() == numPage)
				return movieResponsePages.get(i);

		return null;
	}

	@Override
	protected void update() throws Exception {
		if(debug) System.out.println("update() " + System.currentTimeMillis());

		movieResponsePages = requestMovies();

		setChanged();
		notifyObservers();

		System.out.println("---- MovieRepository -----");
	}
	
	protected List<MovieResponseAPI> requestMovies(){
		List<MovieResponseAPI> list_movies = new ArrayList<>();
		
		for (int i = 1; i <= TMDBRequester.MAXREQUEST; i++) {
			System.out.println("Get page: " + i);
			
			String apiResponse = TMDBRequester.requestPage(i);
			list_movies.add(getMovieResponse(apiResponse));

			System.out.println("---- Teste -----");
			System.out.println(movieResponsePages.get(movieResponsePages.size() - 1).getMovies().get(0).getLabels());
		}
		
		return list_movies;
	}
	
	protected MovieResponseAPI getMovieResponse(String apiResponse){

		JsonObject jsonObjectFactory = new JsonObject();
		MovieResponseAPI movieData = jsonObjectFactory.createObject(apiResponse)[0];
		movieData.setMovies(movieData.getMovies());
		
		return movieData;
	}
	
	protected MovieResponseAPI categorySetter(int i, String apiResponse){
		
		MovieResponseAPI movieData = getMovieResponse(apiResponse);

		Set<Integer> adventureMovieIds = new HashSet<Integer>();
		Set<Integer> animalMovieIds = new HashSet<Integer>();
		Set<Integer> princessMovieIds = new HashSet<Integer>();
		Set<Integer> techMovieIds = new HashSet<Integer>();
		
		List<MovieInfo> tempMI = movieData.getMovies();
		List<String> tempMovieLabels, tempCatLabels;
		
		for(MovieInfo m : tempMI){
			
			tempMovieLabels = m.getLabels();
			for(String label : tempMovieLabels){
				
				tempCatLabels = keyWordsList.getAdventureList();
				for(String advLabel : tempCatLabels){
					
					if(label.equals(advLabel)){
						adventureMovieIds.add(m.getId());
					}
				}
				
				tempCatLabels = keyWordsList.getAnimalList();
				for(String animalLabel : tempCatLabels){
					
					if(label.equals(animalLabel)){
						animalMovieIds.add(m.getId());
					}
				}
				
				tempCatLabels = keyWordsList.getPrincessList();
				for(String prinLabel : tempCatLabels){
					
					if(label.equals(prinLabel)){
						princessMovieIds.add(m.getId());
					}
				}
				
				tempCatLabels = keyWordsList.getTechList();
				for(String techLabel : tempCatLabels){
					
					if(label.equals(techLabel)){
						techMovieIds.add(m.getId());
					}
				}
			}
		}
		
		List<Integer> adventureIntList = new ArrayList<Integer>();
		for(Integer id : adventureMovieIds){
			adventureIntList.add(id);
		}
		this.listAdventure.put(i, adventureIntList);
		
		List<Integer> animalIntList = new ArrayList<Integer>();
		for(Integer id : animalMovieIds){
			animalIntList.add(id);
		}
		this.listAnimal.put(i, animalIntList);
		
		List<Integer> princessIntList = new ArrayList<Integer>();
		for(Integer id : princessMovieIds){
			princessIntList.add(id);
		}
		this.listPrincess.put(i, princessIntList);
		
		List<Integer> techIntList = new ArrayList<Integer>();
		for(Integer id : techMovieIds){
			techIntList.add(id);
		}
		this.listTech.put(i, techIntList);
		
		if(debug){
			sopln("===== TEST =====");
			sopln("listAdventure["+i+"] size: "+this.listAdventure.get(i).size());
			sopln("listAnimal["+i+"] size: "+this.listAnimal.get(i).size());
			sopln("listPrincess["+i+"] size: "+this.listPrincess.get(i).size());
			sopln("listTech["+i+"] size: "+this.listTech.get(i).size());
		}
		
		return movieData;
	}
	
	private void sopln(String s){
		System.out.println(s);
	}

	@Override
	public void updateIfNeeded() throws Exception {
		if (debug)
			System.out.println("updateIfNeeded() " + this.getClass());
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
		movieResponsePages.clear();

		setChanged();
		notifyObservers();
	}

	@Override
	public boolean isEmpty() {
		return movieResponsePages.isEmpty();
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
		movieResponsePages = new ArrayList<>();

		addObserver(DataReloadTimer.getTimer());
	}

	private boolean debug = true;

	public String[] getOneCoverbyBucket() {
		String[] covers = new String[4];

		covers[0] = "";
		for (int i : listAdventure.keySet()) {
			List<Integer> list = listAdventure.get(i);
			if (list != null)
				for (Integer j : list) {
					if (movieResponsePages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("JUNGLE") || movieResponsePages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("ADVENTURE") || movieResponsePages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("MICKEY")) {
						covers[0] = movieResponsePages.get(i).getMovies().get(j.intValue()).getPoster_path();
						//System.out.println("Animal: " + pages.get(i).getMovies().get(j.intValue()).getTitle());
						break;
					}
					//System.out.println("Adventure: " + pages.get(i).getMovies().get(j.intValue()).getTitle());
				}

			if (covers[0] != null && !covers[0].isEmpty())
				break;
		}

		covers[1] = "";
		for (int i : listAnimal.keySet()) {
			List<Integer> list = listAnimal.get(i);
			if (list != null)
				for (Integer j : list) {
					if (movieResponsePages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("SCOOBY")){ //|| pages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("SNOOPY") || pages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("GARFIELD")) {
						covers[1] = movieResponsePages.get(i).getMovies().get(j.intValue()).getPoster_path();
						//System.out.println("Animal: " + pages.get(i).getMovies().get(j.intValue()).getTitle());
						break;
					}
				}
				//System.out.println("#");

			if (covers[1] != null && !covers[1].isEmpty())
				break;
		}

		covers[2] = "";
		for (int i : listPrincess.keySet()) {
			List<Integer> list = listPrincess.get(i);
			if (list != null)
				for (Integer j : list) {
					if (movieResponsePages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("BEAUTY") || movieResponsePages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("MERMAID") || movieResponsePages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("LITTLE")) {
						covers[2] = movieResponsePages.get(i).getMovies().get(j.intValue()).getPoster_path();
						//System.out.println("Animal: " + pages.get(i).getMovies().get(j.intValue()).getTitle());
						break;
					}
				}

			if (covers[2] != null && !covers[2].isEmpty())
				break;
		}

		covers[3] = "";
		for (int i : listTech.keySet()) {
			List<Integer> list = listTech.get(i);
			if (list != null)
				for (Integer j : list) {
					if (movieResponsePages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("WALL") || movieResponsePages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("SPACE") || movieResponsePages.get(i).getMovies().get(j.intValue()).getTitle().toUpperCase().contains("APES")) {
						covers[3] = movieResponsePages.get(i).getMovies().get(j.intValue()).getPoster_path();
						//System.out.println("Animal: " + pages.get(i).getMovies().get(j.intValue()).getTitle());
						break;
					}
				}

			if (covers[3] != null && !covers[3].isEmpty())
				break;
		}

		return covers;
	}

	public String[] getCoversFromBucket(Integer genreId) {
		System.out.println("getCoversFromBucket(" + genreId + ")");
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
		System.out.println(bucketMap);

		int i = -1;
		for (Integer j : bucketMap.keySet()) {
			System.out.println("j="+j);
			List<Integer> list = bucketMap.get(j);
			if (list != null)
				for (Integer k : list) {
					System.out.println("k="+k);
					System.out.println("i="+i);
					if (i >= 0) {
						covers[i] = movieResponsePages.get(j).getMovies().get(k.intValue()).getPoster_path();
						System.out.println("i=" + i + " :: " + covers[i]);
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
