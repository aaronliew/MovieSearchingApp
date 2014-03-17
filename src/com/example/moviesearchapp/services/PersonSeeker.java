package com.example.moviesearchapp.services;

import com.example.moviesearchapp.model.Person;
import com.google.gson.Gson;

public class PersonSeeker extends GenericSeeker<Person>{
	
	private static final String MOVIE_SEARCH_PATH = "person";
	private Gson gson= new Gson();
	
	public Person find(String query) {
		Person moviesList = retrieveMoviesList(query);
		return moviesList;
	}
	
	
	private Person retrieveMoviesList(String query) {
		String url = constructSearchUrl(query);
		String response = httpRetriever.retrieve(url);
		Person obj = gson.fromJson(response, Person.class);
		//Log.d("aaron",obj.getResults().get(1).getPopularity().toString());
		//Log.d(getClass().getSimpleName(), response);
		return obj;
	}

	@Override
	public String retrieveSearchMethodPath() {
		return MOVIE_SEARCH_PATH;
	}
}
