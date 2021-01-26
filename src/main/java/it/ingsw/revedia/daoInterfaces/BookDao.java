package it.ingsw.revedia.daoInterfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.Book;
import it.ingsw.revedia.model.Song;
import it.ingsw.revedia.model.BookReview;

public interface BookDao
{
	public Book getBook(String title) throws SQLException;
	public ArrayList<Book> getBooksByPublisher(String publisher) throws SQLException;
	public ArrayList<Book> getBooksByArtist(String artist) throws SQLException;
	public void updateBook(Book book) throws SQLException;
	public void insertBook(Book book) throws SQLException;
	public void deleteBook(String title) throws SQLException;
	
	public ArrayList<BookReview> getReviews(String title) throws SQLException;
	public void addReview(BookReview review) throws SQLException;
	public void deleteReview(String nickname, String title) throws SQLException;
	public void updateReview(BookReview review) throws SQLException;
	
	public ArrayList<Book> searchByKeyWords(String keyWords, int limit, int offset) throws SQLException;
	public List<Book> findAll() throws SQLException;

	public ArrayList<Book> getRandomBooksByConditions(int limit, boolean mostRated) throws SQLException;
}
