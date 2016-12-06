package io.github.newnc.model.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponse;
import io.github.newnc.model.MovieResponseAPI;
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
public class CompanyMoviesRepository extends MoviesRepository{
	
	protected List<Integer> listDisney;
	protected List<Integer> listDreamworks;
	protected List<Integer> listGhibli;
	protected List<Integer> listPixar;

	protected int qtyCategories = 4;

	protected KeyWordsList keyWordsList = new KeyWordsList();
	
	/**
	 * This fields represents a instance of this class.
	 */
	private static CompanyMoviesRepository instance;

	/**
	 * Returns an instance of this class.
	 *
	 * @return an instance of this class.
	 */
	public static CompanyMoviesRepository getInstance(){
		if(instance == null) 
			instance = new CompanyMoviesRepository();
		
		return instance;
	}
	
	@Override
	public boolean isEmpty() {
		System.out.println("CompanyMovies's isEmpty()");
		
		return (movieResponsePages.isEmpty() ||
				listDisney.isEmpty() ||
				listDreamworks.isEmpty() ||
				listGhibli.isEmpty() ||
				listPixar.isEmpty());
	}


	@Override
	protected void update(){
		System.out.println("---- update() in CompanyMoviesRepository ----");

		movieResponsePages = requestMovies();

		System.out.println("---- end of update() in CompanyMoviesRepository -----");
	}

	protected List<MovieResponse> requestMovies(){
		List<MovieResponse> list_movies = new ArrayList<>();
		int i=1, j=1;
		boolean ret;
		
		int qtyCat[] = new int[qtyCategories];
		
		qtyCat[0] = 0;
		qtyCat[1] = 0;
		qtyCat[2] = 0;
		qtyCat[3] = 0;
		
		ret = notFilledAllCategories(qtyCat);

		while(ret){
			
			while( i <= (j * TMDBRequester.MAXREQUEST/4) ){
	
				String apiResponse = TMDBRequester.requestPageDisney(i);
				MovieResponse movieReponse = getMovieResponse(apiResponse);
				
				for (MovieInfo info : movieReponse.getMovies()){
					listDisney.add(info.getId());
					qtyCat[0]++;
				}
				list_movies.add(movieReponse);
				
				apiResponse = TMDBRequester.requestPageDreamworks(i);
				movieReponse = getMovieResponse(apiResponse);
				
				for (MovieInfo info : movieReponse.getMovies()){
					listDreamworks.add(info.getId());
					qtyCat[1]++;
				}
				list_movies.add(movieReponse);
				
				apiResponse = TMDBRequester.requestPageGhibili(i);
				movieReponse = getMovieResponse(apiResponse);
				
				for (MovieInfo info : movieReponse.getMovies()){
					listGhibli.add(info.getId());
					qtyCat[2]++;
				}
				list_movies.add(movieReponse);
				
				apiResponse = TMDBRequester.requestPagePixar(i);
				movieReponse = getMovieResponse(apiResponse);
				
				for (MovieInfo info : movieReponse.getMovies()){
					listPixar.add(info.getId());
					qtyCat[3]++;
				}
				list_movies.add(movieReponse);
				
				i++;
			}
			
			ret = notFilledAllCategories(qtyCat);
	
			j++;
	
			try{
				System.out.println("Waiting for the API request timelimit (10s)");
				Thread.sleep(10000);
			}catch(InterruptedException e){
				System.out.println("Couldn't sleep");
				e.printStackTrace();
			}
		}

		return list_movies;
	}

	protected MovieResponse getMovieResponse(String apiResponse){

		JsonObject jsonObjectFactory = new JsonObject();
		// Here is where we create the first MovieInfo objects, and set their ids
		MovieResponseAPI movieData = jsonObjectFactory.createObject(apiResponse);
		
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
			listGhibli.add(id);
		}

		return movieData;
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
				m = this.listGhibli;
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
			random_pos = r.nextInt(this.listGhibli.size());
			covers[3] = findMovieById(listGhibli.get(random_pos)).getPoster_path();
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
				m = this.listGhibli;
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
	
	@Override
	protected void init(){
		System.out.println("CompanyMovies's init, called from "+this.getClass());
		
		movieResponsePages = new ArrayList<>();
		
		listDisney = new ArrayList<>();
		listDreamworks = new ArrayList<>();
		listGhibli = new ArrayList<>();
		listPixar = new ArrayList<>();
	}
	
	@Override
	public void clear(){
		movieResponsePages.clear();
		
		listDisney.clear();
		listDreamworks.clear();
		listGhibli.clear();
		listPixar.clear();
	}
	
}
