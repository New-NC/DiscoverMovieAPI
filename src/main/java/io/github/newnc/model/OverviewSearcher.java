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
			String title = m.getTitle().toUpperCase();

			/* Stores the good keywords */
			for(String key_word : keyWordsGoodList){
				if( title.contains(key_word+" ") ||
					ov.contains(" "+key_word+" ") ||
					ov.contains(" "+key_word+",") ||
					ov.contains(" "+key_word+".") ||
					ov.contains(" "+key_word+"S") ){

					keywords_by_movie.add(key_word);
				}
			}
			
			/* Special case 'cause it never appears with an space before //*/
			/*
			if(title.contains("LEGO ")){
				keywords_by_movie.add("LEGO");
			}
			*/

			/* If there's any of the bad ones, clear and break */
			for(String key_word : keyWordsBadList){
				if( title.contains(key_word) ||
					ov.contains(" "+key_word+" ") ||
					ov.contains(" "+key_word+",") ||
					ov.contains(" "+key_word+".") ||
					ov.contains(" "+key_word+"S")
					){

					keywords_by_movie.clear();
					break;
				}
			}
			
			m.setLabels(keywords_by_movie);
		}
	}

}
