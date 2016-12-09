package io.github.newnc.model.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponse;
import io.github.newnc.util.KeyWordsList;

public class CategoryMoviesRepository extends MoviesRepository{

	protected List<Integer> listAdventure;
	protected List<Integer> listAnimal;
	protected List<Integer> listPrincess;
	protected List<Integer> listTech;

	protected int qtyCategories = 4;

	protected KeyWordsList keyWordsList = new KeyWordsList();
	
	@Override
	public boolean isEmpty() {
		System.out.println("CategoryMovies's isEmpty()");
		
		return (movieResponsePages.isEmpty() ||
				listAdventure.isEmpty() || 
				listAnimal.isEmpty() || 
				listPrincess.isEmpty() || 
				listTech.isEmpty());
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
		
		if(movieData == null){
			System.out.println("movieData is null at categotySetter(), returning null");
			return null;
		}

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
	public void clear(){
		System.out.println("CategoryMovies's clear()");
		
		movieResponsePages.clear();
		
		listAdventure.clear();
		listAnimal.clear();
		listPrincess.clear();
		listTech.clear();
	}
	
	@Override
	protected void init(){
		System.out.println("CategoryMovies's init, called from "+this.getClass());
		
		movieResponsePages = new ArrayList<>();
		
		listAdventure = new ArrayList<>();
		listAnimal = new ArrayList<>();
		listPrincess = new ArrayList<>();
		listTech = new ArrayList<>();
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
				m = listAdventure;
				break;
				
			case 1:
				m = listAnimal;
				break;
				
			case 2:
				m = listPrincess;
				break;
				
			case 3:
				m = listTech;
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

}
