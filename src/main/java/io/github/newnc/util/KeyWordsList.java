package io.github.newnc.util;

import java.util.List;
import java.util.ArrayList;

/*
	Pesquisar como deixar sabagaça estática depois
	(não precisar recriar o vetor de palavras-chave o tempo todo

*/

public class KeyWordsList {
	public List<String> key_words;

	public KeyWordsList(){
		key_words = new ArrayList<String>();

		// key_words.add("");

		// The good ones

		key_words.add("ROBOT");
		key_words.add("PRINCESS");
		key_words.add("PRINCE");
		key_words.add("UNICORN");
		key_words.add("PONY");
		key_words.add("CAT");
		key_words.add("DOG");
		key_words.add("DRAGON");
		key_words.add("MONSTER");
		key_words.add("FISH");
		key_words.add("FAIRY");
		key_words.add("DWARF");
		key_words.add("RACE CAR");
		key_words.add("LION");
		key_words.add("FRIENDSHIP");
		key_words.add("TOY");
		key_words.add("SNOOPY");

		// key_words.add("MOTHA_FUCKIN_DRAGONS");


		// The bad ones
		/*
		key_words.add("HORRIFIC");
		key_words.add("MURDER");
		key_words.add("VICTIM");
		key_words.add("HOMICIDE");
		key_words.add("GAY");
		key_words.add("MARRI");
		key_words.add("PROSTITUTE");
		key_words.add("");
		//*/
	}

	public List<String> getKeyWordsList(){
		return key_words;
	}

}
