package it.ingsw.revedia.model;

public class AlbumReview extends Review
{	
	private String user;
	private int albumId;
	
	public AlbumReview() { super(); }

	
	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }

	public int getAlbumId() { return albumId; }
	public void setAlbumId(int albumId) { this.albumId = albumId; }
}
