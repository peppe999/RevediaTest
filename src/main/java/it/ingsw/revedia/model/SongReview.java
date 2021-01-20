package it.ingsw.revedia.model;

public class SongReview extends Review
{
	private String user;
	private String songName;
	private int albumId;
	
	
	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }
	
	public String getSongName() { return songName; }
	public void setSongName(String songName) { this.songName = songName; }
	
	public int getAlbumId() { return albumId; }
	public void setAlbumId(int albumId) { this.albumId = albumId; }
}
