package it.ingsw.revedia.daoInterfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.AlbumReview;
import it.ingsw.revedia.model.Song;

public interface AlbumDao 
{
	public Album findByPrimaryKey(Integer id) throws SQLException;
	public ArrayList<Album> findByTitle(String name) throws SQLException;
	public int insertAlbum(Album album) throws SQLException;
	public List<Album> findAll() throws SQLException;
	
	public void updateAlbum(Album album) throws SQLException;
	public void deleteAlbum(int id) throws SQLException;
	public ArrayList<Song> getSongs(int id) throws SQLException;
	
	public ArrayList<AlbumReview> getReviews(int albumId) throws SQLException;
	public ArrayList<AlbumReview> getReviewsByUserRater(int albumId, String nickname) throws SQLException;
	public void addReview(AlbumReview review) throws SQLException;
	public void deleteReview(String nickname, int albumId) throws SQLException;
	public void updateReview(AlbumReview review) throws SQLException;
	
	public ArrayList<Album> searchByKeyWords(String keyWords, int limit, int offset) throws SQLException;

	ArrayList<Album> findByGenre(String genre) throws SQLException;

	public void insertAlbumGenres(int albumId, List<String> genres) throws SQLException;
	public ArrayList<String> getGenres(int albumId) throws SQLException;

	public void addGenre(String g) throws SQLException;
	public List<String> getAllGenres() throws SQLException;

	public void upsertAlbumReview(String ownerNickname, int albumId, String raterNickname, boolean rating) throws SQLException;

	public ArrayList<Album> getRandomAlbumsByConditions(int limit, boolean mostRated) throws SQLException;
	
	public ArrayList<Album> getBestAlbums() throws SQLException;
	public ArrayList<Album> getLatestAlbums() throws SQLException;
	public ArrayList<Album> getBestAlbumsByGenre(String genre) throws SQLException;
	public ArrayList<Album> getLatestAlbumsByGenre(String genre) throws SQLException;
	public Integer getAlbumsNumberByGenre(String genre) throws SQLException;
}
