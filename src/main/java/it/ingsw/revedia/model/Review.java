package it.ingsw.revedia.model;

import java.sql.Date;

public abstract class Review
{
	private String description;
	private short numberOfStars;
	private Date postDate;
	private Boolean actualUserRate;
	private int likeNumber;
	private int dislikeNumber;
	
	public short getNumberOfStars() { return numberOfStars; }
	public void setNumberOfStars(short numberOfStars) { this.numberOfStars = numberOfStars; }
	
	public void setDescription(String description) { this.description = description; }
	public String getDescription() { return this.description; }
	
	public Date getPostDate() { return postDate; }
	public void setPostDate(Date postDate) { this.postDate = postDate; }

	public Boolean getActualUserRate() {
		return actualUserRate;
	}

	public void setActualUserRate(Boolean actualUserRate) {
		this.actualUserRate = actualUserRate;
	}

	public int getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(int likeNumber) {
		this.likeNumber = likeNumber;
	}

	public int getDislikeNumber() {
		return dislikeNumber;
	}

	public void setDislikeNumber(int dislikeNumber) {
		this.dislikeNumber = dislikeNumber;
	}
}
