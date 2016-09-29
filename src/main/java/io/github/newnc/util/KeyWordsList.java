package io.github.newnc.util;

import java.util.ArrayList;

/*
	Pesquisar como deixar sabagaça estática depois
	(não precisar recriar o vetor de palavras-chave o tempo todo
	
*/

public class KeyWordsList {
	public ArrayList<String> key_words;
	
	public KeyWordsList(){
		key_words.add("MOTHA_FUCKING_DRAGONS");
		key_words.add("ROBOT");
		key_words.add("PRINCESS");
		key_words.add("PRINCE");
		key_words.add("UNICORN");
		key_words.add("PONY");
		key_words.add("CAT");
		key_words.add("DOG");
		key_words.add("DRAGONS");
		// ETC...
	}
	
	public ArrayList<String> getKeyWordsList(){
		return key_words;
	}
	
}
