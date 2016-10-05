package io.github.newnc;

import java.awt.FlowLayout;
import java.awt.MediaTracker;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.github.newnc.model.MovieInfo;
import io.github.newnc.model.MovieResponseAPI;
import io.github.newnc.util.JsonObject;


public class MainTeste {
	public static void main(String[] args) throws IOException {
		String json = requisicaoAPI();
		JsonObject filmeJsonToJava = new JsonObject(); 
		
		//objeto criado a partir do JSON
		MovieResponseAPI[] dadosFilmesAPI = filmeJsonToJava.createObject(json);	
		
		imprimirCapaFilme(dadosFilmesAPI);
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
	
	public static String requisicaoImagemAPI(MovieResponseAPI[] filmes){
		List<MovieInfo> results = filmes[0].getMovies();
		try {
			HttpResponse<JsonNode> response = Unirest.get(
					"https://image.tmdb.org/t/p/w500"
					+ results.get(1).getPoster_path()).asJson();
			return response.getBody().toString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void imprimirListaFilmesPagina(MovieResponseAPI[] filmes){
		List<MovieInfo> results = filmes[0].getMovies();
		//posicao 0 porque só temos uma página por enquanto
		for(int i = 0; i<results.size(); i++){
			System.out.println("Título: " + results.get(i).getTitle() + " - " + "Poster_path: " + 
					results.get(i).getPoster_path() +  " - " + "Resenha: " + results.get(i).getOverview());
		}
	}
	
	/*teste para imprimir a capa de um filme*/
	public static void imprimirCapaFilme(MovieResponseAPI[] filmes) throws MalformedURLException{
		JFrame frame = new JFrame(); // cria frame (janela)
        // seta preferencias do frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(580, 250);

        // inicializa painel
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());

        // inicializa label
        JLabel lblImg = new JLabel(); 

        // inicializa a imagem URL dentro de um objeto ImageIcon
        List<MovieInfo> results = filmes[0].getMovies();
        URL urlImg = new URL("https://image.tmdb.org/t/p/w500" + results.get(0).getPoster_path());
        ImageIcon imgIcon = new ImageIcon(urlImg);
        // faz o preload da imagem
        while(imgIcon.getImageLoadStatus() == MediaTracker.LOADING); 

        // injeta o icone no label
        lblImg.setIcon(imgIcon);
        // adicina o label no panel
        p.add(lblImg);

        frame.getContentPane().add(p);

        // abre a janela (frame)
        frame.setVisible(true);     
		
	}

}
