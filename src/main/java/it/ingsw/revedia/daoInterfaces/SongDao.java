package it.ingsw.revedia.daoInterfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

import it.ingsw.revedia.model.Movie;
import it.ingsw.revedia.model.Song;
import it.ingsw.revedia.model.SongReview;

public interface SongDao 
{
	public ArrayList<Song> findByTitle(String name) throws SQLException;
	public void insertSong(Song song, String userNickname) throws SQLException;
	public void updateSong(Song song) throws SQLException;
	public void deleteSong(Song song) throws SQLException;
	public Song findByPrimaryKey(String name, int albumKey) throws SQLException;
	public List<Song> findAll() throws SQLException;
	
	public ArrayList<SongReview> getReviews(Song song) throws SQLException;
	public ArrayList<SongReview> getReviewsByUserRater(String name, int albumId, String nickname) throws SQLException;
	public void addReview(SongReview review) throws SQLException;
	public void deleteReview(String nickname, String song, int albumId) throws SQLException;
	public void updateReview(SongReview review) throws SQLException;
	
	public ArrayList<Song> searchByKeyWords(String keyWords, int limit, int offset) throws SQLException;

	public ArrayList<Song> getRandomSongsByConditions(int limit, boolean mostRated) throws SQLException;
	public ArrayList<Song> findByGenre(String genre, Integer offset, Integer modality, Integer order) throws SQLException;

	public ArrayList<String> getGenres(int albumId) throws SQLException;
	
	
	public ArrayList<Song> getBestSongs() throws SQLException;
	public ArrayList<Song> getLatestSongs() throws SQLException;
	public ArrayList<Song> getBestSongsByGenre(String genre) throws SQLException;
	public ArrayList<Song> getLatestSongsByGenre(String genre) throws SQLException;
	public ArrayList<Song> getRandomSongs(String genre) throws SQLException;
	public Integer getSongsNumberByGenre(String genre) throws SQLException;
	
	public void upsertSongReview(String ownerNickname, String name, int albumId, String raterNickname, boolean rating) throws SQLException;
}
