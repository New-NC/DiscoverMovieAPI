package io.github.newnc.util;

import java.util.List;
import java.util.ArrayList;

/*
	Pesquisar como deixar sabagaça estática depois
	(não precisar recriar o vetor de palavras-chave o tempo todo

*/

public class KeyWordsList {
	private List<String> good, bad, animal, tech, princess, adventure;

	public KeyWordsList(){
		good = new ArrayList<String>();
		bad = new ArrayList<String>();
		
		animal = new ArrayList<String>();
		tech = new ArrayList<String>();
		princess = new ArrayList<String>();
		adventure = new ArrayList<String>();
		

		// The good ones
		
		String s;
		s = "ROBOT";
		good.add(s);
		tech.add(s);
		
		s = "PRINCESS";
		good.add(s);
		princess.add(s);
		
		s = "PRINCE";
		good.add(s);
		princess.add(s);
		
		s = "UNICORN";
		good.add(s);
		animal.add(s);

		s = "PONY";
		good.add(s);
		animal.add(s);

		s = "PONIES";
		good.add(s);
		animal.add(s);

		s = "CAT";
		good.add(s);
		animal.add(s);

		s = "DOG";
		good.add(s);
		animal.add(s);

		s = "DRAGON";
		good.add(s);
		animal.add(s);

		s = "MONSTER";
		good.add(s);
		adventure.add(s);

		s = "FISH";
		good.add(s);
		animal.add(s);

		s = "FAIRY";
		good.add(s);
		princess.add(s);

		s = "FAIRIES";
		good.add(s);
		princess.add(s);

		s = "DWARF";
		good.add(s);
		princess.add(s);

		s = "DWARVES";
		good.add(s);
		princess.add(s);

		s = "RACE CAR";
		good.add(s);
		adventure.add(s);

		s = "LION";
		good.add(s);
		animal.add(s);

		s = "FRIENDSHIP";
		good.add(s);
		adventure.add(s);

		s = "SNOOPY";
		good.add(s);
		animal.add(s);

		s = "TOY";
		good.add(s);
		adventure.add(s);


		// key_words_good.add("MOTHA_FUCKIN_DRAGONS");


		// The bad ones
		bad.add("HORRIFIC");
		bad.add("MURDER");
		bad.add("VICTIM");
		bad.add("HOMICIDE");
		bad.add("GAY");
		bad.add("MARRI");
		bad.add("PROSTITUTE");
		bad.add("SATAN");
		bad.add("SEX");
		bad.add("DRUGS");
		bad.add("BITCH");
		bad.add("PREY");
		
	}

	public List<String> getKeyWordsGoodList(){
		return good;
	}
	
	public List<String> getKeyWordsBadList(){
		return bad;
	}
	
	public List<String> getAnimalList(){
		return animal;
	}
	
	public List<String> getAdventureList(){
		return adventure;
	}
	
	public List<String> getPrincessList(){
		return princess;
	}
	
	public List<String> getTechList(){
		return tech;
	}

}

