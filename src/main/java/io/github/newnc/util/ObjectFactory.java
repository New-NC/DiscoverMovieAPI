package io.github.newnc.util;

import io.github.newnc.model.MovieResponseAPI;

/*A factory to create objects from any format (JSON, XML) */
public interface ObjectFactory {
	MovieResponseAPI[] createObject(String JsonFile);
}
