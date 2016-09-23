package io.github.newnc;

import java.io.IOException;
import java.util.List;

import io.github.newnc.model.DadosFilmesAPI;
import io.github.newnc.model.RespostaFilmesAPI;
import io.github.newnc.util.PaginaFilmesObjeto;


public class MainTeste {
	public static void main(String[] args) throws IOException {
		String arquivoJson = "/teste.json";
		PaginaFilmesObjeto filmeJsonToJava = new PaginaFilmesObjeto();
		RespostaFilmesAPI[] dadosFilmesAPI = filmeJsonToJava.criarObjeto(arquivoJson);
		//System.out.println(dadosFilmesAPI[0]); //imprime a funcao toString
		imprimirListaFilmesPagina(dadosFilmesAPI);
	}

	public static void imprimirListaFilmesPagina(RespostaFilmesAPI[] filmes){
		List<DadosFilmesAPI> results = filmes[0].getResults();
		for(int i = 0; i<results.size(); i++){ //posicao 0 porque só temos uma página por enquanto
			System.out.println("Título: " + results.get(i).getTitle() + " - " + "Resenha: " 
					+ results.get(i).getOverview());
		}
	}


}
