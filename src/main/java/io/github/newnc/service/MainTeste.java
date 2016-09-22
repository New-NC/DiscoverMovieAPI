package io.github.newnc.service;

import java.io.IOException;


public class MainTeste {
	public static void main(String[] args) throws IOException {
		String arquivoJson = "/teste.json";
		PaginaFilmesObjeto filmeJsonToJava = new PaginaFilmesObjeto();
		RespostaFilmesAPI[] dadosFilmesAPI = filmeJsonToJava.criarObjeto(arquivoJson);
		//System.out.println(dadosFilmesAPI[0]); //imprime a funcao toString
		imprimirListaFilmesPagina(dadosFilmesAPI);
	}

	public static void imprimirListaFilmesPagina(RespostaFilmesAPI[] filmes){
		for(int i = 0; i<filmes[0].results.size(); i++){ //posicao 0 porque só temos uma página por enquanto
			System.out.println("Título: " + filmes[0].results.get(i).getTitle() + " - " + "Resenha: " 
					+ filmes[0].results.get(i).getOverview());
		}
	}


}
