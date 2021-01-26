package it.ingsw.revedia.daoInterfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.Song;
import it.ingsw.revedia.model.SongReview;

public interface SongDao 
{
	public ArrayList<Song> getSong(String name) throws SQLException;
	public Song getSongByArtist(String name, String artist);
	public void insertSong(Song song, String userNickname) throws SQLException;
	public void updateSong(Song song) throws SQLException;
	public void deleteSong(Song song) throws SQLException;
	public Song findByPrimaryKey(String name, int albumKey) throws SQLException;
	public List<Song> findAll() throws SQLException;
	
	public ArrayList<SongReview> getReviews(Song song) throws SQLException;
	public void addReview(SongReview review) throws SQLException;
	public void deleteReview(String nickname, String song, int albumId) throws SQLException;
	public void updateReview(SongReview review) throws SQLException;
	
	public ArrayList<Song> searchByKeyWords(String keyWords, int limit, int offset) throws SQLException;

	public ArrayList<Song> getRandomSongsByConditions(int limit, boolean mostRated) throws SQLException;
}
