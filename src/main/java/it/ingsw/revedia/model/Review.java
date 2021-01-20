package it.ingsw.revedia.model;

import java.sql.Date;

public abstract class Review
{
	private String description;
	private short numberOfStars;
	private Date postDate;
	
	public short getNumberOfStars() { return numberOfStars; }
	public void setNumberOfStars(short numberOfStars) { this.numberOfStars = numberOfStars; }
	
	public void setDescription(String description) { this.description = description; }
	public String getDescription() { return this.description; }
	
	public Date getPostDate() { return postDate; }
	public void setPostDate(Date postDate) { this.postDate = postDate; }
}
