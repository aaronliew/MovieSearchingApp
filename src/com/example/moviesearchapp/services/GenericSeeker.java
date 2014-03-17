package com.example.moviesearchapp.services;

import java.net.URLEncoder;
import java.util.ArrayList;

import android.util.Log;

import com.example.moviesearchapp.model.Movie;
import com.example.moviesearchapp.model.Person;

public abstract class GenericSeeker<E> {
	//https://api.themoviedb.org/3/search/movie?api_key=e3deb5d586800ccb02c69aaad8f66562&query=inception
	protected static final String BASE_URL = "https://api.themoviedb.org/3/search/";
	protected static final String Imdb_BASE_URL = "https://api.themoviedb.org/3/";
	protected static final String API_KEY = "?api_key=e3deb5d586800ccb02c69aaad8f66562&query=";
	protected static final String Imdb_API_KEY = "?api_key=e3deb5d586800ccb02c69aaad8f66562";

	protected HttpRetriever httpRetriever = new HttpRetriever();

	public abstract String retrieveSearchMethodPath();
	
	protected String constructSearchUrl(String query) {
		StringBuffer sb = new StringBuffer();
		sb.append(BASE_URL);
		sb.append(retrieveSearchMethodPath());
		sb.append(API_KEY);
		sb.append(URLEncoder.encode(query));
		return sb.toString();
	}
	
	protected String constructImdbUrl(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append(Imdb_BASE_URL);
		sb.append(retrieveSearchMethodPath());
		sb.append("/"+URLEncoder.encode(id));
		sb.append(Imdb_API_KEY);
		
		return sb.toString();
	}



}