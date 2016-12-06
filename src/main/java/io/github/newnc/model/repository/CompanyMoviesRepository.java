package io.github.newnc.model.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import io.github.newnc.debug.Print;
import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponse;
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
public class CompanyMoviesRepository extends AbstractRepository{
	
	protected List<Integer> listDisney = new ArrayList<Integer>();
	protected List<Integer> listDreamworks = new ArrayList<Integer>();
	protected List<Integer> listGhibili = new ArrayList<Integer>();
	protected List<Integer> listPixar = new ArrayList<Integer>();

	protected int qtyCategories = 4;

	protected KeyWordsList keyWordsList = new KeyWordsList();

	/**
	 * This fields represents a list of pages of the response from TMDB API.
	 */
	protected List<MovieResponse> movieResponsePages;

	/**
	 * Returns a list of pages of this <code>CompanyMoviesRepository</code> instance.
	 *
	 * @return a list of pages of this <code>CompanyMoviesRepository</code> instance.
	 */
	public List<MovieResponse> getPages(){
		return movieResponsePages;
	}

	/**
	 * Returns an iterator for the list of <code>pages</code> of this <code>
	 * CompanyMoviesRepository</code> instance.
	 *
	 * @return an iterator for the list of <code>pages</code> of this <code>
	 * CompanyMoviesRepository</code> instance.
	 */
	public Iterator<MovieResponse> getIterator(){
		return movieResponsePages.iterator();
	}

	/**
	 * Returns a specific <code>page</code> of this <code>CompanyMoviesRepository
	 * </code> instance.
	 *
	 * @param numPage
	 *            the number of the <code>page</code>.
	 * @return a specific <code>page</code> of this <code>CompanyMoviesRepository
	 * </code> instance.
	 */
	public MovieResponse getPage(int numPage){
		int i;
		for(i = 0; i < movieResponsePages.size(); i++)
			if(movieResponsePages.get(i).getPage() == numPage) return movieResponsePages.get(i);

		return null;
	}

	@Override
	protected void update() throws InterruptedException{
		Print.updateTime(this.getClass().getName());

		movieResponsePages = requestMovies();

		setChanged();
		notifyObservers();

		System.out.println("---- end of update() in CompanyMoviesRepository -----");
	}

	protected List<MovieResponse> requestMovies(){
		List<MovieResponse> list_movies = new ArrayList<>();

		for(int i = 1; i <= TMDBRequester.MAXREQUEST/4; i++){
			System.out.println("Get page: " + i);

			String apiResponse = TMDBRequester.requestPageDisney(i);
			MovieResponse movieReponse = getMovieResponse(apiResponse);
			for (MovieInfo info : movieReponse.getMovies())
				listDisney.add(info.getId());
			list_movies.add(movieReponse);
			
			apiResponse = TMDBRequester.requestPageDreamworks(i);
			movieReponse = getMovieResponse(apiResponse);
			for (MovieInfo info : movieReponse.getMovies())
				listDreamworks.add(info.getId());
			list_movies.add(movieReponse);
			
			apiResponse = TMDBRequester.requestPageGhibili(i);
			movieReponse = getMovieResponse(apiResponse);
			for (MovieInfo info : movieReponse.getMovies())
				listGhibili.add(info.getId());
			list_movies.add(movieReponse);
			
			apiResponse = TMDBRequester.requestPagePixar(i);
			movieReponse = getMovieResponse(apiResponse);
			for (MovieInfo info : movieReponse.getMovies())
				listPixar.add(info.getId());
			list_movies.add(movieReponse);
		}

		return list_movies;
	}

	protected MovieResponse getMovieResponse(String apiResponse){

		JsonObject jsonObjectFactory = new JsonObject();
		// Here is where we create the first MovieInfo objects, and set their ids
		MovieResponseAPI movieData = jsonObjectFactory.createObject(apiResponse);
		
		/*
			******* DANGEROUS THINGS AHEAD *******
		*/
		List<MovieInfo> tempMI = movieData.getMovies();
		if(tempMI == null) System.out.println("tempMI is null!");
		else
			for(MovieInfo mi : tempMI){
				mi.setId(MovieInfo.base_id);
				MovieInfo.base_id++; // <-- CAREFUL WITH THIS MOTHERFUCKER !
			}
		/*
			******* END OF DANGEROUS THINGS *******
		*/
		
		movieData.labelMovies();

		return MovieResponse.createMovieResponse(movieData);
	}

	protected boolean notFilledAllCategories(int[] qtyCat){
		System.out.println("notFilledAllCategories(): qtyCat { " + qtyCat[0] + " " + qtyCat[1] + " " + qtyCat[2] + " "
				+ qtyCat[3] + " }");

		int min = 6;

		if(qtyCat[0] < min || qtyCat[1] < min || qtyCat[2] < min || qtyCat[3] < min){
			return true;
		}
		return false;
	}

	protected MovieResponse categorySetter(String apiResponse, int[] qty){

		MovieResponse movieData = getMovieResponse(apiResponse);

		Set<Integer> disneyMovieIds = new HashSet<Integer>();
		Set<Integer> pixarMovieIds = new HashSet<Integer>();
		Set<Integer> dreamWorksMovieIds = new HashSet<Integer>();
		Set<Integer> ghibiliMovieIds = new HashSet<Integer>();

		List<MovieInfo> tempMI = movieData.getMovies();
		List<String> tempMovieLabels, tempCatLabels;

		for(MovieInfo m : tempMI){

			tempMovieLabels = m.getLabels();
			for(String label : tempMovieLabels){

				tempCatLabels = keyWordsList.getAdventureList();
				for(String advLabel : tempCatLabels){

					if(label.equals(advLabel)){
						disneyMovieIds.add(m.getId());
						qty[0]++;
					}
				}

				tempCatLabels = keyWordsList.getAnimalList();
				for(String animalLabel : tempCatLabels){

					if(label.equals(animalLabel)){
						pixarMovieIds.add(m.getId());
						qty[1]++;
					}
				}

				tempCatLabels = keyWordsList.getPrincessList();
				for(String prinLabel : tempCatLabels){

					if(label.equals(prinLabel)){
						dreamWorksMovieIds.add(m.getId());
						qty[2]++;
					}
				}

				tempCatLabels = keyWordsList.getTechList();
				for(String techLabel : tempCatLabels){

					if(label.equals(techLabel)){
						ghibiliMovieIds.add(m.getId());
						qty[3]++;
					}
				}
			}
		}
		
		for(Integer id : disneyMovieIds){
			listDisney.add(id);
		}

		for(Integer id : pixarMovieIds){
			listPixar.add(id);
		}

		for(Integer id : dreamWorksMovieIds){
			listDreamworks.add(id);
		}

		for(Integer id : ghibiliMovieIds){
			listGhibili.add(id);
		}

		//Print.labelsPerPage(listAdventure, listAnimal, listPrincess, listTech);

		return movieData;
	}

	@Override
	public void updateIfNeeded() throws InterruptedException{
		if(isEmpty()) update();
	}

	@Override
	public void forceUpdate() throws InterruptedException{
		clear();

		update();
	}

	@Override
	public void clear(){
		movieResponsePages.clear();

		setChanged();
		notifyObservers();
	}

	@Override
	public boolean isEmpty(){
		return movieResponsePages.isEmpty();
	}

	/**
	 * This fields represents a instance of this class.
	 */
	private static CompanyMoviesRepository instance;

	/**
	 * Returns an instance of this class.
	 *
	 * @return an instance of this class.
	 */
	public static synchronized CompanyMoviesRepository getInstance(){
		if(instance == null) instance = new CompanyMoviesRepository();
		return instance;
	}

	/**
	 * Default constructor.
	 */
	protected CompanyMoviesRepository(){
		movieResponsePages = new ArrayList<>();

		addObserver(DataReloadTimer.getTimer());
	}
	
	
	public String getRandomCover(){
		String s = "HARAMBEDIEDFOROURSINS";
		
		Random r = new Random();

		int n_movie=0, movie_id=0;
		int category;
		
		List<Integer> m = new ArrayList<>();
		
		// Categoria aleatória
		category = r.nextInt(qtyCategories);
		
		switch(category){
			case 0:
				m = this.listDisney;
				break;
				
			case 1:
				m = this.listPixar;
				break;
				
			case 2:
				m = this.listDreamworks;
				break;
				
			case 3:
				m = this.listGhibili;
				break;
		}
		
		// Posição aleatória do filme na categoria
		n_movie = r.nextInt(m.size());
		
		// Pega o id do filme naquela categoria
		movie_id = m.get(n_movie);

		s = findMovieById(movie_id).getPoster_path();
		
		return s;
		
	}
	
	public String[] getRandomCoversFromEachCategory(){
		String[] covers = new String[4];
		
		Random r = new Random();
		
		int random_pos, it=0, max=20;
		
		random_pos = r.nextInt(this.listDisney.size());
		covers[0] = findMovieById(listDisney.get(random_pos)).getPoster_path();
		
		it=0;
		do{
			random_pos = r.nextInt(this.listPixar.size());
			covers[1] = findMovieById(listPixar.get(random_pos)).getPoster_path();
			it++;
		} while(covers[1].equals(covers[0]) && it<max);

		it=0;
		do{
			random_pos = r.nextInt(this.listDreamworks.size());
			covers[2] = findMovieById(listDreamworks.get(random_pos)).getPoster_path();
			it++;
		} while((covers[2].equals(covers[0]) || covers[2].equals(covers[1])) && it<max);

		it=0;
		do{
			random_pos = r.nextInt(this.listGhibili.size());
			covers[3] = findMovieById(listGhibili.get(random_pos)).getPoster_path();
			it++;
		} while((covers[3].equals(covers[0]) || covers[3].equals(covers[1]) || covers[3].equals(covers[2])) && it<max);
		
		return covers;
	}
	
	
	public String[] getRandomCoversForResult(int category){
		String[] covers = new String[5];
		
		List<Integer> m = null;
		
		switch(category){
			case 0:
				m = this.listDisney;
				break;
			case 1:
				m = this.listPixar;
				break;
			case 2:
				m = this.listDreamworks;
				break;
			case 3:
				m = this.listGhibili;
				break;
		}
			
		if(m!=null){
			int size_list = m.size();
			Random r = new Random();
			
			for(int i=0; i<5; i++){
				covers[i] = findMovieById(m.get(r.nextInt(size_list))).getPoster_path();			
			}
		}
		
		return covers;
	}
	
	
	public MovieInfo findMovieById(int movie_id){
		MovieInfo this_movie = null;

		// Busca em cada filme de cada página pelo filme que tenha o id procurado
		for(MovieResponse mr : movieResponsePages){
			
			List<MovieInfo> tempListMI = mr.getMovies();
			
			for(MovieInfo mi : tempListMI){
				if(mi.getId() == movie_id){
					//System.out.println("FOUND SOMEONE "+mi.getId()+" "+mi.getTitle());
					this_movie = mi;
					break;
				}
			}
			
			if(this_movie != null){
				break;
			}
		}
		
		return this_movie;
	}
	
}
