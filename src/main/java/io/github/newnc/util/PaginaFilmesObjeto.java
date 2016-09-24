package io.github.newnc.util;

import java.util.ArrayList;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;


import com.google.gson.reflect.TypeToken;

import io.github.newnc.model.RespostaFilmesAPI;

public class PaginaFilmesObjeto implements JsonToJava {

	@Override
	public RespostaFilmesAPI[] criarObjeto(String arquivoJson){
		Gson gson = new Gson();
		String jsonStr = adicionarColcheteJson(arquivoJson);
		RespostaFilmesAPI[] dadosFilmesAPI = gson.fromJson(jsonStr , RespostaFilmesAPI[].class);
		return dadosFilmesAPI;
	}
	
	public static String adicionarColcheteJson(String json){
		StringBuilder jsonColchete = new StringBuilder(json);
		jsonColchete.insert(0, '[');
		jsonColchete.insert(jsonColchete.length(), ']');
		return jsonColchete.toString();
	}


}
