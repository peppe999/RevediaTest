package it.ingsw.revedia.model;

import java.sql.Date;

public class Song 
{
	private String name;
	private int albumID;
	private String albumName;
	private float length;
	private String link;
	private String description;
	private String user;
	private String genre;
	private float rating;
	private Date postDate;
	
	public Song() {}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public float getLength() { return length; }
	public void setLength(float length) { this.length = length; }

	public String getLink() { return link; }
	public void setLink(String link) { this.link = link; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }
	
	public float getRating() { return rating; }
	public void setRating(float rating) { this.rating = rating; }

	public String getGenre() { return genre; }
	public void setGenre(String genre) { this.genre = genre; }

	public int getAlbumID() { return albumID; }
	public void setAlbumID(int albumID) { this.albumID = albumID; }

	public String getAlbumName() { return albumName; }
	public void setAlbumName(String albumName) { this.albumName = albumName; }

	public Date getPostDate() { return postDate; }
	public void setPostDate(Date postDate) { this.postDate = postDate; }
}