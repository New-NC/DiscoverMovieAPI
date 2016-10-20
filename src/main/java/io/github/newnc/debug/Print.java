package io.github.newnc.debug;

import java.util.List;
import java.util.Map;

import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponse;

public class Print {

	private static boolean ACTIVATED = false;

	public static void activate() {
		ACTIVATED = true;
	}

	private static void title() {
		// 0 -> getStackTrace
		// 1 -> title
		String title = Thread.currentThread().getStackTrace()[2].getMethodName();

		System.out.println("================ " + title + " ================");
	}

	public static void howManyLabels(MovieInfo movie) {
		if (ACTIVATED) {
			title();

			System.out.println("Movie: " + movie.getTitle() + "\tGenres: " + movie.getLabels());
		}
	}

	public static void keyWordFound(MovieInfo movie, String key_word) {
		if (ACTIVATED) {
			title();

			System.out.println("Movie " + movie.getTitle() + " has key-word " + key_word);
		}
	}

	public static void categorizeMovie(MovieInfo movie, String label, String category) {
		if (ACTIVATED) {
			title();

			System.out
					.println("Movie " + movie.getTitle() + " was categorized as " + category + " with label " + label);
		}
	}

	public static void allCategoriesList(List<MovieResponse> pages, Map<Integer, List<Integer>> listAdventure,
			Map<Integer, List<Integer>> listAnimal, Map<Integer, List<Integer>> listPrincess,
			Map<Integer, List<Integer>> listTech) {
		if (ACTIVATED) {
			title();
			System.out.println("listAdventure");
			for (int j : listAdventure.keySet()) {
				System.out.println("listAdventure :: j=" + j);
				if (listAdventure.get(j) != null && listAdventure.get(j).size() > 0)
					for (Integer i : listAdventure.get(j)) {
						System.out.println("listAdventure :: i=" + i);
						if (pages.get(j) != null && pages.get(j).getMovies() != null
								&& pages.get(j).getMovies().size() > i.intValue())
							System.out.println(pages.get(j).getMovies().get(i.intValue()).getTitle());
					}
			}
			System.out.println("\nlistAnimal");
			for (int j : listAnimal.keySet()) {
				System.out.println("listAdventure :: j=" + j);
				if (listAnimal.get(j) != null && listAnimal.get(j).size() > 0)
					for (Integer i : listAnimal.get(j)) {
						System.out.println("listAdventure :: i=" + i);
						if (pages.get(j) != null && pages.get(j).getMovies() != null
								&& pages.get(j).getMovies().size() > i.intValue())
							System.out.println(pages.get(j).getMovies().get(i.intValue()).getTitle());
					}
			}
			System.out.println("\nlistPrincess");
			for (int j : listPrincess.keySet()) {
				System.out.println("listAdventure :: j=" + j);
				if (listPrincess.get(j) != null && listPrincess.get(j).size() > 0)
					for (Integer i : listPrincess.get(j)) {
						System.out.println("listAdventure :: i=" + i);
						if (pages.get(j) != null && pages.get(j).getMovies() != null
								&& pages.get(j).getMovies().size() > i.intValue())
							System.out.println(pages.get(j).getMovies().get(i.intValue()).getTitle());
					}
			}
			System.out.println("\nlistTech");
			for (int j : listTech.keySet()) {
				System.out.println("listAdventure :: j=" + j);
				if (listTech.get(j) != null && listTech.get(j).size() > 0)
					for (Integer i : listTech.get(j)) {
						System.out.println("listAdventure :: i=" + i);
						if (pages.get(j) != null && pages.get(j).getMovies() != null
								&& pages.get(j).getMovies().size() > i.intValue())
							System.out.println(pages.get(j).getMovies().get(i.intValue()).getTitle());
					}
			}
			System.out.println("");
		}
	}

}
