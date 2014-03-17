
package com.example.moviesearchapp.model;

public class PersonResults{
   	private boolean adult;
   	private Number id;
   	private String name;
   	private Number popularity;
   	private String profile_path;

 	public boolean getAdult(){
		return this.adult;
	}
	public void setAdult(boolean adult){
		this.adult = adult;
	}
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
 	public Number getPopularity(){
		return this.popularity;
	}
	public void setPopularity(Number popularity){
		this.popularity = popularity;
	}
 	public String getProfile_path(){
		return this.profile_path;
	}
	public void setProfile_path(String profile_path){
		this.profile_path = profile_path;
	}
}
