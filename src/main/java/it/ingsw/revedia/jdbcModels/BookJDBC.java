package it.ingsw.revedia.jdbcModels;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.daoInterfaces.BookDao;
import it.ingsw.revedia.model.Book;
import it.ingsw.revedia.model.BookReview;

public class BookJDBC implements BookDao
{
	private DataSource dataSource;

	public BookJDBC()
	{
		super();
	}

	public BookJDBC(DataSource dataSource)
	{
		super();
		this.dataSource = dataSource;
	}

	@Override
	public Book getBook(String title) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select title, numberOfPages, description, link, publishinghouse, users, rating, postdate, artist "
				+ "from book " 
				+ "where title = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		ResultSet result = statment.executeQuery();

		Book book = null;
		while (result.next())
		{
			book = buildBook(result);
		}

		result.close();
		statment.close();
		connection.close();
		if (book != null)
		{
			return book;
		} else
		{
			throw new RuntimeException("No books avaible with this title");
		}
	}

	@Override
	public ArrayList<Book> getBooksByPublisher(String publisher) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select title, numberOfPages, description, link, publishinghouse, artist, genre " 
					 + "from book "
					 + "where publishinghouse = ?";
		
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, publisher);
		ResultSet result = statment.executeQuery();
		ArrayList<Book> books = new ArrayList<Book>();

		while (result.next())
		{
			books.add(buildBook(result));
		}

		result.close();
		statment.close();
		connection.close();
		if (books.size() > 0)
		{
			return books;
		} else
		{
			throw new RuntimeException("No books avaible with this publisher");
		}
	}

	@Override
	public ArrayList<Book> getBooksByArtist(String artist) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select title, numberOfPages, description, link, publishinghouse, artist, genre " 
					 + "from book "
					 + "where artist = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, artist);
		ResultSet result = statment.executeQuery();
		ArrayList<Book> books = new ArrayList<Book>();

		while (result.next())
		{
			books.add(buildBook(result));
		}

		result.close();
		statment.close();
		connection.close();
		if (books.size() > 0)
		{
			return books;
		} else
		{
			throw new RuntimeException("No books avaible with this artist");
		}
	}

	@Override
	public void updateBook(Book book) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "update book set numberofpages = ?, description = ?, link = ?, publishinghouse = ?, artist = ?"
					 + " where title = ?";
		
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setShort(1, book.getNumberOfPages());
		statment.setString(2, book.getDescription());
		statment.setString(3, book.getLink());
		statment.setString(4, book.getPublishingHouse());
		statment.setString(5, book.getTitle());
		statment.setString(6, book.getArtist());
		statment.executeUpdate();
		statment.close();
		connection.close();
	}

	@Override
	public void insertBook(Book book) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "insert into book(title, numberofpages,description,link,publishingHouse,users, artist) values(?,?,?,?,?,?,?)";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, book.getTitle());
		statment.setShort(2, book.getNumberOfPages());
		statment.setString(3, book.getDescription());
		statment.setString(4, book.getLink());
		statment.setString(5, book.getPublishingHouse());
		statment.setString(6, book.getUser());
		statment.setString(7, book.getArtist());
		statment.execute();
		statment.close();

		query = "insert into genre_book(genre, book) values (?,?)";
		statment = connection.prepareStatement(query);
		for (String genre : book.getGenres())
		{
			statment.setString(1, genre);
			statment.setString(2, book.getTitle());
			statment.execute();
		}
		statment.close();
		connection.close();
	}

	@Override
	public void deleteBook(String title) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();
		String query = "delete from book where title = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		statment.execute();
		statment.close();
		connection.close();

	}

	private Book buildBook(ResultSet result) throws SQLException
	{
		String title = result.getString("title");
		short numOfPages = result.getShort("numberofpages");
		String description = result.getString("description");
		String link = result.getString("link");
		String publishingHouse = result.getString("publishinghouse");
		String user = result.getString("users");
		float rating = result.getFloat("rating");
		Date postDate = result.getDate("postdate");
		String artist = result.getString("artist");

		Book book = new Book();
		book.setTitle(title);
		book.setNumberOfPages(numOfPages);
		book.setDescription(description);
		book.setLink(link);
		book.setPublishingHouse(publishingHouse);
		book.setUser(user);
		book.setRating(rating);
		book.setPostDate(postDate);
		book.setGenres(getGenres(title));
		book.setArtist(artist);

		return book;
	}

	@Override
	public List<Book> findAll() throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		List<Book> books = new ArrayList<>();

		Book book = null;

		String query = "select title from book";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		while (result.next())
		{
			book = getBook(result.getString("title"));
			books.add(book);
		}

		result.close();
		connection.close();

		return books;
	}

	private ArrayList<String> getGenres(String title) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select genre from genre_book where book = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);

		ResultSet result = statment.executeQuery();
		ArrayList<String> genres = new ArrayList<String>();

		while (result.next())
		{
			genres.add(result.getString("genre"));
		}

		statment.close();
		result.close();
		connection.close();

		return genres;
	}

	@Override
	public ArrayList<BookReview> getReviews(String title) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select users, book, numberofstars, description, postdate " + "from book_review "
				+ "where book = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		ResultSet result = statment.executeQuery();

		ArrayList<BookReview> reviews = new ArrayList<BookReview>();
		while (result.next())
		{
			reviews.add(buildReview(result));
		}

		statment.close();
		result.close();
		connection.close();

		return reviews;
	}

	private BookReview buildReview(ResultSet result) throws SQLException
	{
		String user = result.getString("users");
		String book = result.getString("book");
		short numberOfStars = result.getShort("numberofstars");
		String description = result.getString("description");
		Date postDate = result.getDate("postdate");

		BookReview review = new BookReview();
		review.setUser(user);
		review.setBook(book);
		review.setDescription(description);
		review.setNumberOfStars(numberOfStars);
		review.setPostDate(postDate);

		return review;
	}

	@Override
	public ArrayList<Book> searchByKeyWords(String keyWords, int limit, int offset) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select title, numberOfPages, description, link, publishinghouse, users, rating, postdate, artist "
				+ "from book " 
				+ "where title similar to ? or artist similar to ? " 
				+ "limit ? offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, keyWords);
		statment.setString(2, keyWords);
		statment.setInt(3, limit);
		statment.setInt(4, offset);

		ResultSet result = statment.executeQuery();

		ArrayList<Book> books = new ArrayList<Book>();
		while (result.next())
		{
			books.add(buildBook(result));
		}

		result.close();
		statment.close();
		connection.close();

		return books;
	}

	@Override
	public void addReview(BookReview review) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "insert into book_review(users,book,numberofstars,description) values(?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, review.getUser());
		statement.setString(2, review.getBook());
		statement.setShort(3, review.getNumberOfStars());
		statement.setString(4, review.getDescription());
		statement.execute();
		statement.close();
		connection.close();
	}

	@Override
	public void deleteReview(String nickname, String title) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "delete from book_review where users = ? and book = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);
		statment.setString(2, title);
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public void updateReview(BookReview review) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "update book_review set numberofstars = ?, description = ? " + "where users = ? and book = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setShort(1, review.getNumberOfStars());
		statment.setString(2, review.getDescription());
		statment.setString(3, review.getUser());
		statment.setString(4, review.getBook());
		statment.executeUpdate();
		statment.close();
		connection.close();
	}

	@Override
	public ArrayList<Book> getRandomBooksByConditions(int limit, boolean mostRated) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();
		String query = "select title, users, rating " +
					   "from book ";

		if(mostRated)
			query += "where rating = (select max(rating) from book) ";

		query += "order by random() limit ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1,limit);

		ResultSet result = statement.executeQuery();
		ArrayList<Book> books = new ArrayList<>();
		while (result.next())
			books.add(buildShortBook(result));

		try
		{
			return books;
		}
		finally
		{
			connection.close();
			result.close();
			statement.close();
		}
	}

	private Book buildShortBook(ResultSet result) throws SQLException
	{
		String title = result.getString("title");
		String user = result.getString("users");
		float rating = result.getFloat("rating");

		Book book = new Book();
		book.setTitle(title);
		book.setUser(user);
		book.setRating(rating);

		return book;
	}
}
