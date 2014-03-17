package com.example.moviesearchapp.model;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable{
   	/**
	 * 
	 */
	private static final long serialVersionUID = -706831893781872568L;
   	private List<MovieResults> results;

 	public List<MovieResults> getResults(){
		return this.results;
	}
	public void setResults(List<MovieResults> results){
		this.results = results;
	}
	
	
}