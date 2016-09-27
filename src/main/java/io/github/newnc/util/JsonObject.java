package io.github.newnc.util;

import java.util.ArrayList;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;


import com.google.gson.Gson;


import com.google.gson.reflect.TypeToken;

import io.github.newnc.model.RespostaFilmesAPI;

public class JsonObject implements ObjectFactory {

	@Override
	public RespostaFilmesAPI[] createObject(String JsonFile){
		Gson gson = new Gson();
		JsonUtils jsonBracket = new JsonUtils();
		String jsonStr = jsonBracket.addBracketJson(JsonFile);
		RespostaFilmesAPI[] dadosFilmesAPI = gson.fromJson(jsonStr , RespostaFilmesAPI[].class);
		return dadosFilmesAPI;
	}
	


}
