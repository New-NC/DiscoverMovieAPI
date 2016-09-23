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
		 Reader reader = null;
		try {
			reader = new InputStreamReader(PaginaFilmesObjeto.class.getResourceAsStream(arquivoJson), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 RespostaFilmesAPI[] dadosFilmesAPI = gson.fromJson(reader, RespostaFilmesAPI[].class);
		 return dadosFilmesAPI;
	 }
	 
	
}
	    