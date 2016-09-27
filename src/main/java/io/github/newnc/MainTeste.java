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

import io.github.newnc.model.DadosFilmesAPI;
import io.github.newnc.model.RespostaFilmesAPI;
import io.github.newnc.util.JsonObject;
import net.minidev.json.JSONObject;


public class MainTeste {
	public static void main(String[] args) throws IOException {
		String json = requisicaoAPI();
		JsonObject filmeJsonToJava = new JsonObject(); 
		RespostaFilmesAPI[] dadosFilmesAPI = filmeJsonToJava.criarObjeto(json); //objeto criado a partir do JSON
		//System.out.println(dadosFilmesAPI[0]); //imprime a funcao toString
		imprimirListaFilmesPagina(dadosFilmesAPI);
		
		
	}
	
	public static String requisicaoAPI(){
		try {
			HttpResponse<JsonNode> response = Unirest.get("https://api.themoviedb.org/3/discover/movie?api_key=988bfbce3f85f6688647dfb4f5d7a5a9&certification_country=US&certification.lte=G&sort_by=popularity.desc&page=6").asJson();
			return response.getBody().toString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void imprimirListaFilmesPagina(RespostaFilmesAPI[] filmes){
		List<DadosFilmesAPI> results = filmes[0].getResults();
		for(int i = 0; i<results.size(); i++){ //posicao 0 porque só temos uma página por enquanto
			System.out.println("Título: " + results.get(i).getTitle() + " - " + "Resenha: "
					+ results.get(i).getOverview());
		}
	}
	

	


}
