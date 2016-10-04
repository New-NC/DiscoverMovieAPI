package io.github.newnc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.newnc.util.KeyWordsList;

/*
	This class searches the overview of a movie for key-words
	and gives a genre to the movie based on the key-words present in it
*/

public class OverviewSearcher {
	
	private KeyWordsList keyWords = new KeyWordsList();
	private List<MovieInfo> movies = new ArrayList<>();
	
	public void execute(){
		List<String> keyWordsList;
		List<String> keywords_by_movie = new ArrayList<>();
		
		/* find movie keywords */
		for(MovieInfo movie: movies){
			
			keyWordsList = keyWords.getKeyWordsList();
			keywords_by_movie.clear();
			
			for(String key_word: keyWordsList){
				if(movie.getOverview().toUpperCase().contains(key_word)){
					keywords_by_movie.add(key_word);
					
					System.out.println("Movie "+movie.getTitle()+" has key-word "+key_word);
				}
			}
			
			/* count movie labels */
			Collections.sort(keywords_by_movie);
			String key_word_temp = keywords_by_movie.get(0);
			int max=1, actual=0, count=0;
			
			for(int i=1; i<keywords_by_movie.size(); i++){
				if(keywords_by_movie.get(i) != key_word_temp){
					if(max < count){ /* gets the first one in draw scenario */
						max = count;
						actual = i;
					}
					count = 0;
					key_word_temp = keywords_by_movie.get(i);
				}
				else{
					count++;
				}
			}
			
			/* classify movie genre */
			movie.setGenre(keywords_by_movie.get(actual));
			
		}

	}
}
