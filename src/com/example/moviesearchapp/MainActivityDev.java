package com.example.moviesearchapp;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.moviesearchapp.model.ImdbMovie;
import com.example.moviesearchapp.model.Movie;
import com.example.moviesearchapp.services.JSONReader;
import com.google.gson.Gson;
public class MainActivityDev extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	final ArrayList<ImdbMovie> list= new ArrayList<ImdbMovie>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    	final Gson gson= new Gson();
    	
		new Thread(new Runnable() {
	        public void run() {
	        	try {
	        		 String json = (String) JSONReader.readJsonFromUrl("https://api.themoviedb.org/3/search/movie?" +
	        		 		"api_key=e3deb5d586800ccb02c69aaad8f66562&query=world%20war%20z");
	        		 
	        		 Movie obj = gson.fromJson(json, Movie.class);
	        	     Log.d("aaron",obj.getResults().get(0).getId().toString());

	        	     json = (String) JSONReader.readJsonFromUrl("https://api.themoviedb.org/3/movie/" 
	        	    		 + obj.getResults().get(0).getId().toString() + 
	        	    		 "?api_key=e3deb5d586800ccb02c69aaad8f66562");
	        	     
	        	     ImdbMovie imdbData = gson.fromJson(json, ImdbMovie.class);
	        	     
	        	     list.add(imdbData);

		        	 json = (String) JSONReader.readJsonFromUrl("https://api.themoviedb.org/3/movie/" 
		        	    		 + obj.getResults().get(1).getId().toString() + 
		        	    		 "?api_key=e3deb5d586800ccb02c69aaad8f66562");
		        	     
		        	 imdbData = gson.fromJson(json, ImdbMovie.class);
		        	 list.add(imdbData);
		        	     
	        	     Log.d("aaron",list.get(0).getImdb_id().toString());
	        	     Log.d("aaron",list.get(1).getImdb_id().toString());
	        	    	
				} catch (IOException e) {
					Log.e("aaron", e.getMessage());
				} catch (JSONException e) {
					Log.e("aaron", e.getMessage());
				}	
	        }
	    }).start();
    }   
  }
    


