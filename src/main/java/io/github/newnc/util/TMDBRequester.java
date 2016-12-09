package io.github.newnc.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TMDBRequester {

	private static final String APIKEY = "988bfbce3f85f6688647dfb4f5d7a5a9";
	public static final int MAXREQUEST = 35;

	public static String requestPage(int page){
		System.out.println("requestPage("+page+")");
		try {
			HttpResponse<JsonNode> response =
					Unirest.get("https://api.themoviedb.org/3/discover/movie"
							+ "?api_key=" + APIKEY
							+ "&certification_country=US&certification.lte=G"
							+ "&primary_release_date.gte=2012-09-15"
							+ "&sort_by=popularity.desc&page=" + page).asJson();
			return response.getBody().toString();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String requestPageTopRated(int page) {
		String s = "https://api.themoviedb.org/3/discover/movie"
				+ "?api_key=" + APIKEY
				+ "&certification_country=US&certification.lte=G"
				+ "&sort_by=popularity.desc&page=" + page;
		return requestPage(s);
	}

	public static String requestPageNewest(int page ){
		String s = "https://api.themoviedb.org/3/discover/movie"
				+ "?api_key=" + APIKEY
				+ "&certification_country=US&certification.lte=G"
				+ "&sort_by=release_date.desc&page=" + page;
		
		return requestPage(s);
	}
	
	private static String requestPage(String s){
		
		HttpResponse<JsonNode> response = null;
		boolean f = true;
		int c = 0;
		
		while(f && c<50){
			f = false;
			
			try {
				
				response = Unirest.get(s).asJson();

				// System.out.format("%2d >> No UnknowHostException apparently\n", c);
				
				return response.getBody().toString();

			} catch (Exception e) {
				
				f = true;
				System.out.println("c: "+c++);
				
				System.out.println("Exception at requestPage()"
						+ ", it's either Unirest or UnknownHost Exception");
				//e.printStackTrace();
			}
			
		}
		
		return null;
	}
	
	public static String requestPageDisney(int page) {
		return requestPageCompanyBased(page, 2);
	}
	
	public static String requestPagePixar(int page) {
		return requestPageCompanyBased(page, 3);
	}
	
	public static String requestPageDreamworks(int page) {
		return requestPageCompanyBased(page, 521);
	}
	
	public static String requestPageGhibili(int page) {
		return requestPageCompanyBased(page, 10342);
	}

	public static String requestPageCompanyBased(int page, int company){
		String s = "https://api.themoviedb.org/3/discover/movie"
				+ "?api_key=" + APIKEY
				+ "&certification_country=US&certification.lte=G"
				+ "&with_company=" + company + "&page=" + page;
		
		return requestPage(s);
	}

}
