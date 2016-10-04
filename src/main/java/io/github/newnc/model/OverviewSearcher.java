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
	private ArrayList<MovieInfo> movies;
	
	public void execute(){
		List<String> keyWordsList;
		
		for(MovieInfo movie: movies){
			
			keyWordsList = keyWords.getKeyWordsList();
			
			for(String key_word: keyWordsList){
				if(movie.getOverview().toUpperCase().contains(key_word)){
					System.out.println("Movie "+movie.getTitle()+" has key-word "+key_word);
				}
			}
			
		}
	}
}
