package io.github.newnc.util;

import com.google.gson.Gson;

import io.github.newnc.model.MovieResponseAPI;

public class JsonObject implements ObjectFactory {

	@Override
	public MovieResponseAPI createObject(String JsonFile){
		//System.out.println(JsonFile);
		return (MovieResponseAPI) new Gson().fromJson(JsonFile, MovieResponseAPI.class);
	}
	


}
