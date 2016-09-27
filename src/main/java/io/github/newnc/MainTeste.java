package io.github.newnc;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.PaginaFilmesObjeto;
import net.minidev.json.JSONObject;


public class MainTeste {
	public static void main(String[] args) throws IOException {
		String json = requisicaoAPI();
		PaginaFilmesObjeto filmeJsonToJava = new PaginaFilmesObjeto(); 
		
		//objeto criado a partir do JSON
		MovieResponseAPI[] dadosFilmesAPI = filmeJsonToJava.criarObjeto(json);
		//System.out.println(dadosFilmesAPI[0]); //imprime a funcao toString
		imprimirListaFilmesPagina(dadosFilmesAPI);
		
	}
	
	public static String requisicaoAPI(){
		try {
			HttpResponse<JsonNode> response = Unirest.get(
					"https://api.themoviedb.org/3/discover/movie?"
					+ "api_key=988bfbce3f85f6688647dfb4f5d7a5a9&"
					+ "certification_country=US&certification.lte=G&"
					+ "sort_by=popularity.desc&page=6").asJson();
			return response.getBody().toString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void imprimirListaFilmesPagina(MovieResponseAPI[] filmes){
		List<MovieInfo> results = filmes[0].getResults();
		//posicao 0 porque só temos uma página por enquanto
		for(int i = 0; i<results.size(); i++){
			System.out.println("Título: " + results.get(i).getTitle() + " - " + "Resenha: "
					+ results.get(i).getOverview());
		}
	}

}
