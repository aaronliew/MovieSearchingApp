
package com.example.moviesearchapp.model;

import java.io.Serializable;

public class Genres implements Serializable{

	private static final long serialVersionUID = 3490078506907682992L;
	private Number id;
   	private String name;

 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
}
