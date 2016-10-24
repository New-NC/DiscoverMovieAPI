package io.github.newnc.model;

import java.util.ArrayList;
import java.util.List;

import io.github.newnc.util.KeyWordsList;

/*
	This class searches the overview of a movie for key-words
	and gives a genre to the movie based on the key-words present in it
*/

public class OverviewSearcher {

	private KeyWordsList keyWords;
	private boolean debug = false;

	public OverviewSearcher(){
		keyWords = new KeyWordsList();
	}

	public void execute(List<MovieInfo> movies){
		List<String> keyWordsGoodList, keyWordsBadList;
		List<String> keywords_by_movie = null;

		/* find movie keywords */
		if (movies != null)
		for(MovieInfo m : movies){

			keyWordsGoodList = keyWords.getKeyWordsGoodList();
			keyWordsBadList = keyWords.getKeyWordsBadList();
			
			keywords_by_movie = new ArrayList<>();
			
			String ov = m.getOverview().toUpperCase();

			/* Stores the good keywords */
			for(String key_word : keyWordsGoodList){
				if( ov.contains(" "+key_word+" ") ||
					ov.contains(" "+key_word+",") ||
					ov.contains(" "+key_word+".") ||
					ov.contains(" "+key_word+"S") ){
					
					keywords_by_movie.add(key_word);
				}
			}
			
			/* If there's any of the bad ones, clear and break */
			for(String key_word : keyWordsBadList){
				if( ov.contains(" "+key_word+" ") ||
					ov.contains(" "+key_word+",") ||
					ov.contains(" "+key_word+".") ||
					ov.contains(" "+key_word+"S") ){
					
					keywords_by_movie.clear();
					break;
				}
			}

			/* classify movie genre */
			m.setLabels(keywords_by_movie);

			if(debug) System.out.println("Movie: "+m.getTitle()+" --- Genres: "+m.getLabels());
		}

	}
	
}
