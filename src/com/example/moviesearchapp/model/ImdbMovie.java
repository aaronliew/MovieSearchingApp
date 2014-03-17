
package com.example.moviesearchapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImdbMovie implements Serializable
{

	private static final long serialVersionUID = -6814886315783830255L;
	public ArrayList<Image> imagesList;
	private boolean adult;
   	private String backdrop_path;
   	private String belongs_to_collection;
   	private Number budget;
   	private List<Genres> genres;
   	private String homepage;
   	private Number id;
   	private String imdb_id;
   	private String original_title;
   	private String overview;
   	private Double popularity;
   	private String poster_path;
   	private String release_date;
   	private Number revenue;
   	private Number runtime;
   	private String status;
   	private String tagline;
   	private String title;
   	private Number vote_average;
   	private Number vote_count;

 	public boolean getAdult(){
		return this.adult;
	}
	public void setAdult(boolean adult){
		this.adult = adult;
	}
 	public String getBackdrop_path(){
		return this.backdrop_path;
	}
	public void setBackdrop_path(String backdrop_path){
		this.backdrop_path = backdrop_path;
	}
 	public String getBelongs_to_collection(){
		return this.belongs_to_collection;
	}
	public void setBelongs_to_collection(String belongs_to_collection){
		this.belongs_to_collection = belongs_to_collection;
	}
 	public Number getBudget(){
		return this.budget;
	}
	public void setBudget(Number budget){
		this.budget = budget;
	}
 	public List<Genres> getGenres(){
		return this.genres;
	}
	public void setGenres(List<Genres> genres){
		this.genres = genres;
	}
 	public String getHomepage(){
		return this.homepage;
	}
	public void setHomepage(String homepage){
		this.homepage = homepage;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public String getImdb_id(){
		return this.imdb_id;
	}
	public void setImdb_id(String imdb_id){
		this.imdb_id = imdb_id;
	}
 	public String getOriginal_title(){
		return this.original_title;
	}
	public void setOriginal_title(String original_title){
		this.original_title = original_title;
	}
 	public String getOverview(){
		return this.overview;
	}
	public void setOverview(String overview){
		this.overview = overview;
	}
 	public Double getPopularity(){
 		popularity= (double) Math.round(popularity*100)/100;
		return this.popularity;
	}
	public void setPopularity(Double popularity){
		this.popularity = popularity;
	}
 	public String getPoster_path(){
		return this.poster_path;
	}
	public void setPoster_path(String poster_path){
		this.poster_path = poster_path;
	}
	
 	public String getRelease_date(){
		return this.release_date;
	}
	public void setRelease_date(String release_date){
		this.release_date = release_date;
	}
 	public Number getRevenue(){
		return this.revenue;
	}
	public void setRevenue(Number revenue){
		this.revenue = revenue;
	}
 	public Number getRuntime(){
		return this.runtime;
	}
	public void setRuntime(Number runtime){
		this.runtime = runtime;
	}
	
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
 	public String getTagline(){
		return this.tagline;
	}
	public void setTagline(String tagline){
		this.tagline = tagline;
	}
 	public String getTitle(){
		return this.title;
	}
	public void setTitle(String title){
		this.title = title;
	}
 	public Number getVote_average(){
		return this.vote_average;
	}
	public void setVote_average(Number vote_average){
		this.vote_average = vote_average;
	}
 	public Number getVote_count(){
		return this.vote_count;
	}
	public void setVote_count(Number vote_count){
		this.vote_count = vote_count;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Movie [name=");
		builder.append(title);
		builder.append("]");
		return builder.toString();
	}
	
	public String retrieveThumbnail() {
		final String BASE_URL = "http://image.tmdb.org/t/p/w500/";
		//http://image.tmdb.org/t/p/w500/gAt1PrsrFY1nX6UzebeiHP8njE9.jpg
		if (poster_path!=null ) { 
					return (BASE_URL+poster_path);
				}
		
		
		return null;
		
	}
	
	public String retrieveCoverImage() {
		final String BASE_URL = "http://image.tmdb.org/t/p/w500/";
		//http://image.tmdb.org/t/p/w500/gAt1PrsrFY1nX6UzebeiHP8njE9.jpg
		if (backdrop_path!=null ) { 
					return (BASE_URL+backdrop_path);
				}
		
		
		return null;
	}
}
