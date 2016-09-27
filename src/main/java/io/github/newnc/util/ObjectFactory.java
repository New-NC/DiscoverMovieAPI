package io.github.newnc.util;

import io.github.newnc.model.RespostaFilmesAPI;

/*A factory to create objects from any format (JSON, XML) */
public interface ObjectFactory {
	RespostaFilmesAPI[] createObject(String JsonFile);
}
