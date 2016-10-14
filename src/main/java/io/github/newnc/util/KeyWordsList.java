package io.github.newnc.util;

import java.util.List;
import java.util.ArrayList;

/*
	Pesquisar como deixar sabagaça estática depois
	(não precisar recriar o vetor de palavras-chave o tempo todo
	
*/

public class KeyWordsList {
	public static final List<String> keyWords;
	
	static {
		keyWords = new ArrayList<String>();
		
		keyWords.add("MOTHA_FUCKIN_DRAGONS");
		keyWords.add("ROBOT");
		keyWords.add("PRINCESS");
		keyWords.add("PRINCE");
		keyWords.add("UNICORN");
		keyWords.add("PONY");
		keyWords.add("CAT");
		keyWords.add("DOG");
		keyWords.add("DRAGONS");
		// ETC...
	}
	
}
