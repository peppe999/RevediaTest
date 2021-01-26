package it.ingsw.revedia.daoInterfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.model.*;

public interface BookDao
{
	public Book findByPrimaryKey(String title) throws SQLException;
	public ArrayList<Book> findByPublisher(String publisher) throws SQLException;
	public ArrayList<Book> findByArtist(String artist) throws SQLException;
	public ArrayList<Book> findByGenre(String genre) throws SQLException;

	public void updateBook(Book book) throws SQLException;
	public int insertBook(Book book) throws SQLException;
	public void deleteBook(String title) throws SQLException;
	
	public ArrayList<BookReview> getReviews(String title) throws SQLException;
	public ArrayList<BookReview> getReviewsByUserRater(String title, String nickname) throws SQLException;
	public void addReview(BookReview review) throws SQLException;
	public void deleteReview(String nickname, String title) throws SQLException;
	public void updateReview(BookReview review) throws SQLException;
	
	public ArrayList<Book> searchByKeyWords(String keyWords, int limit, int offset) throws SQLException;
	public List<Book> findAll() throws SQLException;

	public ArrayList<Book> getRandomBooksByConditions(int limit, boolean mostRated) throws SQLException;

	public void insertBookGenres(String title, List<String> genres) throws SQLException;
	public ArrayList<String> getGenres(String title) throws SQLException;

	public void addGenre(String g) throws SQLException;
	public List<String> getAllGenres() throws SQLException;
	
	public ArrayList<Book> getHighRateBooks() throws SQLException;
	public ArrayList<Book> getLatestBooks() throws SQLException;
	public ArrayList<String> getRandomGenres() throws SQLException;

	public void upsertBookReview(String ownerNickname, String title, String raterNickname, boolean rating) throws SQLException;
}
