package it.ingsw.revedia.model;

import java.sql.Date;
import java.util.ArrayList;

public class Movie
{
	private String title;
	private float length;
	private String description;
	private String link;
	private String user;
	private ArrayList<String> genres;
	private float rating;
	private Date postDate;
	
	public Movie() 
	{
		this.genres = new ArrayList<String>();
	}

	public Movie(String title, float length, String description, String link, String user, ArrayList<String> genres)
	{
		super();
		this.title = title;
		this.length = length;
		this.description = description;
		this.link = link;
		this.user = user;
		this.genres = genres;
	}

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public float getLength() { return length; }
	public void setLength(float length) { this.length = length; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getLink() { return link; }
	public void setLink(String link) { this.link = link; }

	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }

	public ArrayList<String> getGenres() { return genres; }
	public void setGenres(ArrayList<String> genres) { this.genres = genres; }
	
	public float getRating() { return rating; }
	public void setRating(float rating) { this.rating = rating; }

	public Date getPostDate() { return postDate; }
	public void setPostDate(Date postDate) { this.postDate = postDate; }
}