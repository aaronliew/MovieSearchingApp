package com.example.moviesearchapp.model;

import java.io.Serializable;

public class MovieResults implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6575827452330832272L;
	private Number id;

 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
	

}
