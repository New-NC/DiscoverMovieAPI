package io.github.newnc.model;

import java.util.ArrayList;
import java.util.List;

import io.github.newnc.debug.Print;
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

	public List<MovieInfo> execute(MovieInfo[] movies){
		List<MovieInfo> returnList = new ArrayList<>();
		
		List<String> keyWordsList;
		List<String> keywords_by_movie = new ArrayList<>();

		/* find movie keywords */
		if (movies != null)
		for(int i=0; i < movies.length; i++){

			keyWordsList = keyWords.getKeyWordsList();
			keywords_by_movie.clear();

			// This doesn't cover everything (fairies or ponies, for example), but it's good
			for(String key_word: keyWordsList){
				if(
					(movies[i].getOverview().toUpperCase().contains(" "+key_word+" ") ||
					movies[i].getOverview().toUpperCase().contains(" "+key_word+".") ||
					movies[i].getOverview().toUpperCase().contains(" "+key_word+",") ||
					movies[i].getOverview().toUpperCase().contains(" "+key_word+"S ") ||
					movies[i].getOverview().toUpperCase().contains(" "+key_word+"ES ") ||
					movies[i].getOverview().toUpperCase().contains(" "+key_word+"AGE ") ||
					// Added title search, 'cause reasons.
					movies[i].getTitle().toUpperCase().contains(" "+key_word+" ") ||
					movies[i].getTitle().toUpperCase().contains(" "+key_word+"S") ||
					movies[i].getTitle().toUpperCase().contains(" "+key_word+"ES"))
					// Black list
					/*&& !movies.get(i).getTitle().toUpperCase().contains(" SATAN ")
					&& !movies.get(i).getTitle().toUpperCase().contains(" SEX ")
					&& !movies.get(i).getTitle().toUpperCase().contains(" DRUGS ")
					&& !movies.get(i).getTitle().toUpperCase().contains(" SEXIEST ")
					&& !movies.get(i).getTitle().toUpperCase().contains(" BITCH ")
					&& !movies.get(i).getTitle().toUpperCase().contains(" JEWISH ")
					&& !movies.get(i).getTitle().toUpperCase().contains(" PREY ")*/
				  ){

					keywords_by_movie.add(key_word);

					Print.keyWordFound(movies[i], key_word);
				}
			}

			/* classify movie genre */
			movies[i].setLabel(keywords_by_movie);

			Print.howManyLabels(movies[i]);
			
			if (!movies[i].getLabels().isEmpty())
				returnList.add(movies[i]);
		}

		return returnList;
	}
}
