package io.github.newnc.debug;

import java.util.List;
import java.util.Map;

import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponse;

public class Print{

	private static boolean ACTIVATED = false;

	public static void activate(){
		System.out.println("Print activated!");
		ACTIVATED = true;
	}

	private static void title(){
		// 0 -> getStackTrace
		// 1 -> title
		String title = Thread.currentThread().getStackTrace()[2].getMethodName();

		System.out.println("================ " + title + " ================");
	}

	public static void howManyLabels(List<MovieInfo> movies){
		if(ACTIVATED){
			title();

			for(MovieInfo movie : movies)
				System.out.println("Movie: " + movie.getTitle() + "\tGenres: " + movie.getLabels());
		}
	}

	public static void keyWordFound(MovieInfo movie, String key_word){
		if(ACTIVATED){
			title();

			System.out.println("Movie " + movie.getTitle() + " has key-word " + key_word);
		}
	}

	public static void categorizeMovie(MovieInfo movie, String label, String category){
		if(ACTIVATED){
			title();

			System.out
					.println("Movie " + movie.getTitle() + " was categorized as " + category + " with label " + label);
		}
	}

	public static void moviesTitleAndLabels(List<MovieInfo> movies){
		if(ACTIVATED){
			title();

			if(movies != null)
				for(MovieInfo mi : movies)
					System.out.println(mi.getTitle() + " | " + mi.getLabels());
		}
	}

	public static void updateTime(String thisClass){
		if(ACTIVATED){
			title();

			System.out.println("Update at " + System.currentTimeMillis() + "ms" 
					+ ", in " + thisClass);
		}
	}

	public static void allCategoriesList(List<MovieResponse> movieResponsePages,
			Map<Integer, List<Integer>> listAdventure, Map<Integer, List<Integer>> listAnimal,
			Map<Integer, List<Integer>> listPrincess, Map<Integer, List<Integer>> listTech){
		if(ACTIVATED){
			title();
			System.out.println("listAdventure");
			for(int j : listAdventure.keySet()){
				System.out.println("listAdventure :: j=" + j);
				if(listAdventure.get(j) != null && listAdventure.get(j).size() > 0)
					for(Integer i : listAdventure.get(j)){
						System.out.println("listAdventure :: i=" + i);
						if(j < movieResponsePages.size() && movieResponsePages.get(j) != null
								&& movieResponsePages.get(j).getMovies() != null
								&& movieResponsePages.get(j).getMovies().size() > i.intValue())
							System.out.println(movieResponsePages.get(j).getMovies().get(i.intValue()).getTitle());
					}
			}
			System.out.println("\nlistAnimal");
			for(int j : listAnimal.keySet()){
				System.out.println("nlistAnimal :: j=" + j);
				if(listAnimal.get(j) != null && listAnimal.get(j).size() > 0)
					for(Integer i : listAnimal.get(j)){
						System.out.println("nlistAnimal :: i=" + i);
						if(j < movieResponsePages.size() && movieResponsePages.get(j) != null
								&& movieResponsePages.get(j).getMovies() != null
								&& movieResponsePages.get(j).getMovies().size() > i.intValue())
							System.out.println(movieResponsePages.get(j).getMovies().get(i.intValue()).getTitle());
					}
			}
			System.out.println("\nlistPrincess");
			for(int j : listPrincess.keySet()){
				System.out.println("nlistPrincess :: j=" + j);
				if(listPrincess.get(j) != null && listPrincess.get(j).size() > 0)
					for(Integer i : listPrincess.get(j)){
						System.out.println("nlistPrincess :: i=" + i);
						if(j < movieResponsePages.size() && movieResponsePages.get(j) != null
								&& movieResponsePages.get(j).getMovies() != null
								&& movieResponsePages.get(j).getMovies().size() > i.intValue())
							System.out.println(movieResponsePages.get(j).getMovies().get(i.intValue()).getTitle());
					}
			}
			System.out.println("\nlistTech");
			for(int j : listTech.keySet()){
				System.out.println("nlistTech :: j=" + j);
				if(listTech.get(j) != null && listTech.get(j).size() > 0)
					for(Integer i : listTech.get(j)){
						System.out.println("nlistTech :: i=" + i);
						if(j < movieResponsePages.size() && movieResponsePages.get(j) != null
								&& movieResponsePages.get(j).getMovies() != null
								&& movieResponsePages.get(j).getMovies().size() > i.intValue())
							System.out.println(movieResponsePages.get(j).getMovies().get(i.intValue()).getTitle());
					}
			}
			System.out.println("");
		}
	}

	public static void labelsPerPage(int i, Map<Integer, List<Integer>> listAdventure,
			Map<Integer, List<Integer>> listAnimal, Map<Integer, List<Integer>> listPrincess,
			Map<Integer, List<Integer>> listTech){
		if(ACTIVATED){
			System.out.println("listAdventure[" + i + "] size: " + listAdventure.get(i).size());
			System.out.println("listAnimal[" + i + "] size: " + listAnimal.get(i).size());
			System.out.println("listPrincess[" + i + "] size: " + listPrincess.get(i).size());
			System.out.println("listTech[" + i + "] size: " + listTech.get(i).size());
		}
	}

}
