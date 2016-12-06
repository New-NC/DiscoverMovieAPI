package io.github.newnc.model.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponse;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.DataReloadTimer;
import io.github.newnc.util.JsonObject;
import io.github.newnc.util.KeyWordsList;

/**
 * This class represents an in-memory repository of movies gets from TMDB API.
 *
 * This class implements the singleton pattern.
 *
 * @see <a href="http://www.oodesign.com/singleton-pattern.html">Singleton
	 *      Pattern</a>
 */
public class MovieRepository extends AbstractRepository{
	/*
		listAdventure
		listAnimal
		listPrincess
		listTech
	*/

	protected List<Integer> listAdventure;
	protected List<Integer> listAnimal;
	protected List<Integer> listPrincess;
	protected List<Integer> listTech;

	protected int qtyCategories = 4;

	protected KeyWordsList keyWordsList = new KeyWordsList();

	/**
	 * This fields represents a list of pages of the response from TMDB API.
	 */
	protected List<MovieResponse> movieResponsePages;

	/**
	 * Returns a specific <code>page</code> of this <code>MovieRepository
	 * </code> instance.
	 *
	 * @param numPage
	 *            the number of the <code>page</code>.
	 * @return a specific <code>page</code> of this <code>MovieRepository
	 * </code> instance.
	 */
	public MovieResponse getPage(int numPage){
		int i;
		for(i = 0; i < movieResponsePages.size(); i++)
			if(movieResponsePages.get(i).getPage() == numPage) return movieResponsePages.get(i);

		return null;
	}

	protected MovieResponse getMovieResponse(String apiResponse){

		JsonObject jsonObjectFactory = new JsonObject();
		// Here is where we create the first MovieInfo objects, and set their ids
		MovieResponseAPI movieData = jsonObjectFactory.createObject(apiResponse);
		
		/*
			******* DANGEROUS THINGS AHEAD *******
		*/
		List<MovieInfo> tempMI = movieData.getMovies();
		for(MovieInfo mi : tempMI){
			//mi.setId(MovieInfo.base_id);
			MovieInfo.base_id++; // <-- CAREFUL WITH THIS!
		}
		/*
			******* END OF DANGEROUS THINGS *******
		*/
		
		movieData.labelMovies();

		return MovieResponse.createMovieResponse(movieData);
	}

	protected boolean notFilledAllCategories(int[] qtyCat){
		System.out.println("notFilledAllCategories(): qtyCat { "
				+ qtyCat[0] + " " 
				+ qtyCat[1] + " " 
				+ qtyCat[2] + " " 
				+ qtyCat[3] + " }");

		int min = 6;

		return (qtyCat[0] < min || qtyCat[1] < min || qtyCat[2] < min || qtyCat[3] < min);
	}

	protected MovieResponse categorySetter(String apiResponse, int[] qty){

		MovieResponse movieData = getMovieResponse(apiResponse);

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
						qty[0]++;
					}
				}

				tempCatLabels = keyWordsList.getAnimalList();
				for(String animalLabel : tempCatLabels){

					if(label.equals(animalLabel)){
						animalMovieIds.add(m.getId());
						qty[1]++;
					}
				}

				tempCatLabels = keyWordsList.getPrincessList();
				for(String prinLabel : tempCatLabels){

					if(label.equals(prinLabel)){
						princessMovieIds.add(m.getId());
						qty[2]++;
					}
				}

				tempCatLabels = keyWordsList.getTechList();
				for(String techLabel : tempCatLabels){

					if(label.equals(techLabel)){
						techMovieIds.add(m.getId());
						qty[3]++;
					}
				}
			}
		}
		
		for(Integer id : adventureMovieIds){
			listAdventure.add(id);
		}

		for(Integer id : animalMovieIds){
			listAnimal.add(id);
		}

		for(Integer id : princessMovieIds){
			listPrincess.add(id);
		}

		for(Integer id : techMovieIds){
			listTech.add(id);
		}

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
		
		listAdventure.clear();
		listAnimal.clear();
		listPrincess.clear();
		listTech.clear();
		
		System.out.println("MovieRepository cleared");

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
	private static MovieRepository instance;

	/**
	 * Returns an instance of this class.
	 *
	 * @return an instance of this class.
	 */
	public static synchronized MovieRepository getInstance(){
		if(instance == null) instance = new MovieRepository();
		return instance;
	}

	/**
	 * Default constructor.
	 */
	protected MovieRepository(){
		init();

		addObserver(DataReloadTimer.getTimer());
	}
	
	protected void init(){
		System.out.println("Init called from "+this.getClass());
		
		this.movieResponsePages = new ArrayList<>();
		
		this.listAdventure = new ArrayList<>();
		this.listAnimal = new ArrayList<>();
		this.listPrincess = new ArrayList<>();
		this.listTech = new ArrayList<>();
	}
	
	
	public String getRandomCover(){
		String s = "HITLERDIDNOTHINGWRONG";
		
		Random r = new Random();

		int n_movie=0, movie_id=0;
		int category;
		
		List<Integer> m = new ArrayList<>();
		
		// Categoria aleatória
		category = r.nextInt(qtyCategories);
		
		switch(category){
			case 0:
				m = this.listAdventure;
				break;
				
			case 1:
				m = this.listAnimal;
				break;
				
			case 2:
				m = this.listPrincess;
				break;
				
			case 3:
				m = this.listTech;
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
		
		random_pos = r.nextInt(this.listAdventure.size());
		covers[0] = findMovieById(listAdventure.get(random_pos)).getPoster_path();
		
		it=0;
		do{
			random_pos = r.nextInt(this.listAnimal.size());
			covers[1] = findMovieById(listAnimal.get(random_pos)).getPoster_path();
			it++;
		} while(covers[1].equals(covers[0]) && it<max);

		it=0;
		do{
			random_pos = r.nextInt(this.listPrincess.size());
			covers[2] = findMovieById(listPrincess.get(random_pos)).getPoster_path();
			it++;
		} while((covers[2].equals(covers[0]) || covers[2].equals(covers[1])) && it<max);

		it=0;
		do{
			random_pos = r.nextInt(this.listTech.size());
			covers[3] = findMovieById(listTech.get(random_pos)).getPoster_path();
			it++;
		} while((covers[3].equals(covers[0]) || covers[3].equals(covers[1]) || covers[3].equals(covers[2])) && it<max);
		
		return covers;
	}
	
	
	public String[] getRandomCoversForResult(int category){
		String[] covers = new String[10];
		
		List<Integer> m = null;
		
		switch(category){
			case 0:
				m = this.listAdventure;
				break;
			case 1:
				m = this.listAnimal;
				break;
			case 2:
				m = this.listPrincess;
				break;
			case 3:
				m = this.listTech;
				break;
		}
			
		if(m!=null){
			int size_list = m.size();
			Random r = new Random();
			
			for(int i=0; i<5; i++){
				MovieInfo movieInfo = findMovieById(m.get(r.nextInt(size_list)));
				covers[i*2] = movieInfo.getPoster_path();
				covers[i*2+1] = Integer.toString(movieInfo.getId());
				System.out.println(covers[i*2]);
				System.out.println(covers[i*2+1]);
				System.out.println(movieInfo.getId());
				System.out.println("" + movieInfo.getId());
			}
		}
		
		return covers;
	}
	
	
	public MovieInfo findMovieById(int movie_id){
		MovieInfo found_it = null;

		// Busca em cada filme de cada página pelo filme que tenha o id procurado
		for(MovieResponse mr : movieResponsePages){
			
			List<MovieInfo> tempListMI = mr.getMovies();
			
			for(MovieInfo mi : tempListMI){
				if(mi.getId() == movie_id){
					//System.out.println("FOUND SOMEONE "+mi.getId()+" "+mi.getTitle());
					found_it = mi;
					break;
				}
			}
			
			if(found_it != null){
				break;
			}
		}
		
		return found_it;
	}

	@Override
	protected void update() throws InterruptedException{
		// Not used
		
	}
	
}
