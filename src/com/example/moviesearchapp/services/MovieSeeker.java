package com.example.moviesearchapp.services;

import java.util.ArrayList;

import android.util.Log;

import com.example.moviesearchapp.model.ImdbMovie;
import com.example.moviesearchapp.model.LatestMovie;
import com.example.moviesearchapp.model.Movie;
import com.google.gson.Gson;

public class MovieSeeker extends GenericSeeker<Movie> {
		
	private static final String MOVIE_SEARCH_PATH = "movie";
	private static final String LATEST_MOVIE_SEARCH_PATH = "movie/latest";
	private ArrayList<ImdbMovie> list= new ArrayList<ImdbMovie>();
	private Gson gson= new Gson();
	
	public ArrayList<ImdbMovie> find(String query) {
		ArrayList<ImdbMovie> moviesList = retrieveMovieList(query);
		return moviesList;
	}
	
	private ArrayList<ImdbMovie> retrieveMovieList(String id) {
		list.clear();
		String url = constructSearchUrl(id);
		String response = httpRetriever.retrieve(url);
		Movie obj = gson.fromJson(response, Movie.class);
		int Size= obj.getResults().size();
		for (int i=0 ; i <Size ; i++)
		{
			url = constructImdbUrl(obj.getResults().get(i).getId().toString());
			response = httpRetriever.retrieve(url);
			ImdbMovie imdbData = gson.fromJson(response, ImdbMovie.class);
			list.add(imdbData);
			
		}
		return list;
	}

	@Override
	public String retrieveSearchMethodPath() {
		return MOVIE_SEARCH_PATH;
	}
	
	@Override
	public String retrieveSearchLatestMovieMethodPath() {
		return LATEST_MOVIE_SEARCH_PATH;
	}

	public LatestMovie findLatest() {
		String url = constructLatestMovieUrl();
		String response = httpRetriever.retrieve(url);
		LatestMovie obj = gson.fromJson(response, LatestMovie.class);
		return obj;
	}






}
