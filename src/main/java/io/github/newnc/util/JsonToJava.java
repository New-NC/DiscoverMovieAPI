package io.github.newnc.util;

import io.github.newnc.model.RespostaFilmesAPI;

public interface JsonToJava {
	RespostaFilmesAPI[] criarObjeto(String arquivoJson);
}
