package io.github.newnc.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.newnc.model.DadosFilmesAPI;
import io.github.newnc.model.RespostaFilmesAPI;
import io.github.newnc.util.PaginaFilmesObjeto;

@RestController
public class FilmesService {

  @RequestMapping(value = "/filmes/", method = RequestMethod.GET)
  public RespostaFilmesAPI[] fromJsonFile() {
    String arquivoJson = "/teste.json";

    PaginaFilmesObjeto filmeJsonToJava = new PaginaFilmesObjeto();
		RespostaFilmesAPI[] dadosFilmesAPI = filmeJsonToJava.criarObjeto(arquivoJson);

    return dadosFilmesAPI;
  }

  @RequestMapping(value = "/filmes/{arquivoJson}", method = RequestMethod.GET)
  public RespostaFilmesAPI[] fromJsonFile(@PathVariable String arquivoJson) {
    PaginaFilmesObjeto filmeJsonToJava = new PaginaFilmesObjeto();
		RespostaFilmesAPI[] dadosFilmesAPI = filmeJsonToJava.criarObjeto(arquivoJson);

    return dadosFilmesAPI;
  }

  @RequestMapping(value = "/filmes/page/{page}", method = RequestMethod.GET)
  public List<DadosFilmesAPI> getFilmesByPage(@PathVariable Integer page) {
    List<DadosFilmesAPI> results = fromJsonFile()[page].getResults();

    return results;
  }
}
