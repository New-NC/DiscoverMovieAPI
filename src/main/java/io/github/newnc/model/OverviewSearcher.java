package io.github.newnc.model;

import java.util.ArrayList;
import java.util.List;

import io.github.newnc.util.KeyWordsList;

/*
	This class searches the overview of a movie for key-words
	and gives a genre to the movie based on the key-words present in it
*/

public class OverviewSearcher {

	private KeyWordsList keyWords = new KeyWordsList();
	private List<MovieInfo> movies = new ArrayList<>();

	public List<MovieInfo> execute(List<MovieInfo> m){
		List<String> keyWordsList;
		List<String> keywords_by_movie = new ArrayList<>();
		this.movies = m;

		/* find movie keywords */
		for(MovieInfo movie: movies){

			keyWordsList = keyWords.getKeyWordsList();
			keywords_by_movie.clear();

			// This doesn't cover everything (fairies or ponies, for example), but it's good
			for(String key_word: keyWordsList){
				if(
					movie.getOverview().toUpperCase().contains(" "+key_word+" ") ||
					movie.getOverview().toUpperCase().contains(" "+key_word+".") ||
					movie.getOverview().toUpperCase().contains(" "+key_word+",") ||
					movie.getOverview().toUpperCase().contains(" "+key_word+"S") ||
					movie.getOverview().toUpperCase().contains(" "+key_word+"ES") ||
					movie.getOverview().toUpperCase().contains(" "+key_word+"AGE") ||
					// Added title search, 'cause reasons.
					movie.getTitle().toUpperCase().contains(" "+key_word+" ") ||
					movie.getTitle().toUpperCase().contains(" "+key_word+"S") ||
					movie.getTitle().toUpperCase().contains(" "+key_word+"ES")
				  ){

					keywords_by_movie.add(key_word);

					//System.out.println("Movie "+movie.getTitle()+" has key-word "+key_word);
				}
			}

			/* classify movie genre */
			movie.setLabel(keywords_by_movie);

			//System.out.println("Movie: "+movie.getTitle()+"\tGenres: "+movie.getLabels());
		}

		return movies;

	}
}
