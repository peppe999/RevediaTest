package it.ingsw.revedia.daoInterfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.AlbumReview;
import it.ingsw.revedia.model.Book;
import it.ingsw.revedia.model.BookReview;

public interface BookDao {
	public Book findByPrimaryKey(String title) throws SQLException;

	public ArrayList<Book> findByPublisher(String publisher) throws SQLException;

	public ArrayList<Book> findByArtist(String artist) throws SQLException;

	public ArrayList<Book> findByGenre(String genre, Integer offset, Integer modality, Integer order) throws SQLException;
	public Book findBook(Book book) throws SQLException;

	public void updateBook(Book book) throws SQLException;

	public int insertBook(Book book) throws SQLException;

	public void deleteBook(String title) throws SQLException;

	public ArrayList<BookReview> getReviews(String title, Integer offset) throws SQLException;
	public BookReview getUserReview(String title, String nickname) throws SQLException;
	public ArrayList<BookReview> getReviewsByUserRater(String title, String nickname, Integer offset) throws SQLException;

	public void addReview(BookReview review) throws SQLException;

	public void deleteReview(String nickname, String title) throws SQLException;

	public void updateReview(BookReview review) throws SQLException;

	public ArrayList<Book> searchByKeyWords(String[] keyWords, int limit, int offset) throws SQLException;
	public ArrayList<Book> searchByUser(String user, Integer offset, Integer modality, Integer order) throws SQLException;
	public ArrayList<Book> searchByUserWithKeyWords(String user, String[] keyWords, Integer offset, Integer modality, Integer order) throws SQLException;

	public Integer getUserCountWithKeyWords(String user, String[] keyWords) throws SQLException;

	public List<Book> findAll() throws SQLException;

	public ArrayList<Book> getRandomBooksByConditions(int limit, boolean mostRated) throws SQLException;

	public void insertBookGenres(String title, List<String> genres) throws SQLException;

	public ArrayList<String> getGenres(String title) throws SQLException;

	public void addGenre(String g) throws SQLException;

	public List<String> getAllGenres() throws SQLException;

	public ArrayList<Book> getBestBooks() throws SQLException;

	public ArrayList<Book> getLatestBooks() throws SQLException;


	public void upsertBookReview(String ownerNickname, String title, String raterNickname, boolean rating)
			throws SQLException;

	public ArrayList<Book> getBestBooksByGenre(String genre) throws SQLException;

	public ArrayList<Book> getLatestBooksByGenre(String genre) throws SQLException;

	public Integer getBooksNumberByGenre(String genre) throws SQLException;

	public ArrayList<Book> getRandomBooks(String genre) throws SQLException;
}
