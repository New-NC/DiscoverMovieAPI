package io.github.newnc.util;

//a class for the JSON manipulation functions.
public class JsonUtils {
	
	public String addBracketJson(String json){
		StringBuilder jsonBracket = new StringBuilder(json);
		jsonBracket.insert(0, '[');
		jsonBracket.insert(jsonBracket.length(), ']');
		return jsonBracket.toString();
	}
	
}
