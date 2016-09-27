package io.github.newnc.util;

import com.google.gson.Gson;

import io.github.newnc.model.MovieResponseAPI;

public class JsonObject implements ObjectFactory {

	@Override
	public MovieResponseAPI[] createObject(String JsonFile){
		Gson gson = new Gson();
		JsonUtils jsonBracket = new JsonUtils();
		String jsonStr = jsonBracket.addBracketJson(JsonFile);
		MovieResponseAPI[] dadosFilmesAPI = gson.fromJson(jsonStr , MovieResponseAPI[].class);
		return dadosFilmesAPI;
	}
	


}
