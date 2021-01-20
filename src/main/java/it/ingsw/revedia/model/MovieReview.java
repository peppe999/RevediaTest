package it.ingsw.revedia.model;

public class MovieReview extends Review
{	
	private String user; //primary key
	private String movie; //primary key
	
	public MovieReview() { super(); }
	
	
	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }
	
	public String getMovie() { return movie; }
	public void setMovie(String movie) { this.movie = movie; }
}
