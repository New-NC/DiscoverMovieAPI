package io.github.newnc.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.newnc.model.DadosFilmesAPI;
import io.github.newnc.model.RespostaFilmesAPI;
import io.github.newnc.util.PaginaFilmesObjeto;
import io.github.newnc.util.TMDBRequester;

@RestController
public class FilmesService {

  @RequestMapping(value = "/filmes/", method = RequestMethod.GET)
  public RespostaFilmesAPI[] doTMDB() {
	String respostaDaAPI = TMDBRequester.requisitarPelaPagina(1);

	PaginaFilmesObjeto filmeJsonToJava = new PaginaFilmesObjeto();
	RespostaFilmesAPI[] dadosFilmesAPI = filmeJsonToJava.criarObjeto(respostaDaAPI);

	return dadosFilmesAPI;
  }

  @RequestMapping(value = "/filmes/{page}", method = RequestMethod.GET)
  public RespostaFilmesAPI[] doTMDBNaPagina(@PathVariable Integer page) {
	String respostaDaAPI = TMDBRequester.requisitarPelaPagina(page);

	PaginaFilmesObjeto filmeJsonToJava = new PaginaFilmesObjeto();
	RespostaFilmesAPI[] dadosFilmesAPI = filmeJsonToJava.criarObjeto(respostaDaAPI);

	return dadosFilmesAPI;
  }
}
