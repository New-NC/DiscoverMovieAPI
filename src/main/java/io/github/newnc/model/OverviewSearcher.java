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

	public List<MovieInfo> execute(List<MovieInfo> movies){
		List<String> keyWordsList;
		List<String> keywords_by_movie = new ArrayList<>();

		/* find movie keywords */
		for(int i=0; i < movies.size(); i++){

			keyWordsList = keyWords.getKeyWordsList();
			keywords_by_movie.clear();

			// This doesn't cover everything (fairies or ponies, for example), but it's good
			for(String key_word: keyWordsList){
				if(
					movies.get(i).getOverview().toUpperCase().contains(" "+key_word+" ") ||
					movies.get(i).getOverview().toUpperCase().contains(" "+key_word+".") ||
					movies.get(i).getOverview().toUpperCase().contains(" "+key_word+",") ||
					movies.get(i).getOverview().toUpperCase().contains(" "+key_word+"S ") ||
					movies.get(i).getOverview().toUpperCase().contains(" "+key_word+"ES ") ||
					movies.get(i).getOverview().toUpperCase().contains(" "+key_word+"AGE ") ||
					// Added title search, 'cause reasons.
					movies.get(i).getTitle().toUpperCase().contains(" "+key_word+" ") ||
					movies.get(i).getTitle().toUpperCase().contains(" "+key_word+"S") ||
					movies.get(i).getTitle().toUpperCase().contains(" "+key_word+"ES")
				  ){

					keywords_by_movie.add(key_word);

					System.out.println("Movie "+movies.get(i).getTitle()+" has key-word "+key_word);
				}
			}

			/* classify movie genre */
			movies.get(i).setLabel(keywords_by_movie);

			if(debug) System.out.println("Movie: "+movies.get(i).getTitle()+
							"\tGenres: "+movies.get(i).getLabels());
		}
		
		return movies;

	}
}
