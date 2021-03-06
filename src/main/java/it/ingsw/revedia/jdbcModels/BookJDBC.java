package it.ingsw.revedia.jdbcModels;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.daoInterfaces.BookDao;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.AlbumReview;
import it.ingsw.revedia.model.Book;
import it.ingsw.revedia.model.BookReview;

public class BookJDBC implements BookDao {
	private DataSource dataSource;

	public BookJDBC() {
		super();
	}

	public BookJDBC(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public Book findByPrimaryKey(String title) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select * from book where title = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		ResultSet result = statment.executeQuery();

		Book book = null;
		while (result.next()) {
			book = buildBook(result);
		}

		result.close();
		statment.close();
		connection.close();

		return book;
	}

	@Override
	public ArrayList<Book> findByPublisher(String publisher) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, imageid, rating " + "from book " + "where publishinghouse = ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, publisher);
		ResultSet result = statment.executeQuery();
		ArrayList<Book> books = new ArrayList<Book>();

		while (result.next()) {
			books.add(buildSimplifiedBook(result));
		}

		result.close();
		statment.close();
		connection.close();
		if (books.size() > 0) {
			return books;
		} else {
			throw new RuntimeException("No books avalaible with this publisher");
		}
	}

	@Override
	public ArrayList<Book> findByArtist(String artist) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, imageid, rating " + "from book " + "where artist = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, artist);
		ResultSet result = statment.executeQuery();
		ArrayList<Book> books = new ArrayList<Book>();

		while (result.next()) {
			books.add(buildSimplifiedBook(result));
		}

		result.close();
		statment.close();
		connection.close();
		if (books.size() > 0) {
			return books;
		} else {
			throw new RuntimeException("No books avalaible with this artist");
		}
	}

	@Override
	public ArrayList<Book> findByGenre(String genre, Integer offset, Integer modality, Integer order) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, imageid, rating " + "from book " + "inner join genre_book "
				+ "on book.title = genre_book.book " + "where genre_book.genre = ?";

		String orderString = (order == 0) ? "ASC" : "DESC";

		if(modality == 0)
			query += " order by title " + orderString;
		else
			query += " order by postdate " + orderString + ", imageid " + orderString;

		query += " limit 20 offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		statment.setInt(2, offset);
		ResultSet result = statment.executeQuery();
		ArrayList<Book> books = new ArrayList<Book>();

		while (result.next()) {
			books.add(buildSimplifiedBook(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (books.size() > 0) {
			return books;
		} else {
			throw new RuntimeException("No book found in this genre");
		}
	}

    @Override
    public Book findBook(Book book) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select * " + "from book " + "where title = ? and numberofpages = ? and publishinghouse = ? and artist = ? limit 1";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, book.getTitle());
		statment.setInt(2, book.getNumberOfPages());
		statment.setString(3, book.getPublishingHouse());
		statment.setString(4, book.getArtist());
		ResultSet result = statment.executeQuery();

		Book dbBook = null;
		if(result.next())
			dbBook = buildBook(result);

		result.close();
		statment.close();
		connection.close();
		return dbBook;
    }

    @Override
	public void updateBook(Book book) throws SQLException {
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
	public int insertBook(Book book) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "insert into book(title, numberofpages,description,link,publishingHouse,users, artist) values(?,?,?,?,?,?,?) returning imageid";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, book.getTitle());
		statment.setShort(2, book.getNumberOfPages());
		statment.setString(3, book.getDescription());
		statment.setString(4, book.getLink());
		statment.setString(5, book.getPublishingHouse());
		statment.setString(6, book.getUser());
		statment.setString(7, book.getArtist());
		ResultSet result = statment.executeQuery();
		result.next();
		int id = result.getInt(1);
		// statment.execute();
		statment.close();
		connection.close();

		insertBookGenres(book.getTitle(), book.getGenres());

		return id;
	}

	@Override
	public void deleteBook(String title) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "delete from book where title = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		statment.execute();
		statment.close();
		connection.close();

	}

	private Book buildBook(ResultSet result) throws SQLException {
		Book book = buildSimplifiedBook(result);

		short numOfPages = result.getShort("numberofpages");
		String description = result.getString("description");
		String link = result.getString("link");
		String publishingHouse = result.getString("publishinghouse");
		Date postDate = result.getDate("postdate");
		String artist = result.getString("artist");

		book.setNumberOfPages(numOfPages);
		book.setDescription(description);
		book.setLink(link);
		book.setPublishingHouse(publishingHouse);
		book.setPostDate(postDate);
		book.setArtist(artist);

		book.setGenres(getGenres(book.getTitle()));

		return book;
	}

	private Book buildSimplifiedBook(ResultSet result) throws SQLException {
		String title = result.getString("title");
		String user = result.getString("users");
		float rating = result.getFloat("rating");
		int imageId = result.getInt("imageid");

		Book book = new Book();
		book.setTitle(title);
		book.setUser(user);
		book.setRating(rating);
		book.setImageId(imageId);

		return book;
	}

	@Override
	public List<Book> findAll() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		List<Book> books = new ArrayList<>();

		Book book = null;
		String query = "select title, users, imageid, rating from book";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			book = buildSimplifiedBook(result);
			books.add(book);
		}

		result.close();
		statement.close();
		connection.close();

		return books;
	}

	@Override
	public void insertBookGenres(String title, List<String> genres) throws SQLException {
		for (String genre : genres) {
			Connection connection = this.dataSource.getConnection();

			String query = "insert into genre_book(book, genre) values (?,?)";
			PreparedStatement statment = connection.prepareStatement(query);
			statment.setString(1, title);
			statment.setString(2, genre);
			statment.execute();
			statment.close();
			connection.close();
		}
	}

	@Override
	public ArrayList<String> getGenres(String title) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select genre from genre_book where book = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);

		ResultSet result = statment.executeQuery();
		ArrayList<String> genres = new ArrayList<String>();

		while (result.next()) {
			genres.add(result.getString("genre"));
		}

		statment.close();
		result.close();
		connection.close();

		return genres;
	}

	@Override
	public void addGenre(String g) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "insert into genre(name) values (?)";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, g);
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public List<String> getAllGenres() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select distinct genre from genre_book";

		PreparedStatement statment = connection.prepareStatement(query);

		ResultSet result = statment.executeQuery();
		List<String> genres = new ArrayList<>();

		while (result.next()) {
			genres.add(result.getString("genre"));
		}

		result.close();
		statment.close();
		connection.close();

		return genres;
	}

	@Override
	public void upsertBookReview(String ownerNickname, String title, String raterNickname, boolean rating)
			throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "INSERT INTO user_rates_book_review(users, book, userthatrates, rated) VALUES(?, ?, ?, ?) "
				+ "ON CONFLICT ON CONSTRAINT user_rates_book_review_pkey DO UPDATE SET rated = EXCLUDED.rated";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, ownerNickname);
		statment.setString(2, title);
		statment.setString(3, raterNickname);
		statment.setBoolean(4, rating);
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public ArrayList<BookReview> getReviews(String title, Integer offset) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select users, book, numberofstars, description, postdate, likeNumber, dislikeNumber " + "from book_review reviewT, LATERAL " +
				"(select COUNT(*) as likeNumber FROM user_rates_book_review WHERE users = reviewT.users and book = reviewT.book and rated = true) likeT, LATERAL " +
				"(select COUNT(*) as dislikeNumber FROM user_rates_book_review WHERE users = reviewT.users and book = reviewT.book and rated = false) dislikeT "
				+ "where book = ? ORDER BY postdate DESC, likeNumber DESC, dislikeNumber ASC, numberofstars DESC, reviewT.users DESC " +
				"limit 15 offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		statment.setInt(2, offset);
		ResultSet result = statment.executeQuery();

		ArrayList<BookReview> reviews = new ArrayList<BookReview>();
		while (result.next()) {
			reviews.add(buildReview(result, false));
		}

		statment.close();
		result.close();
		connection.close();

		return reviews;
	}

	@Override
	public BookReview getUserReview(String title, String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select users, book, numberofstars, description, postdate, likeNumber, dislikeNumber " + "from book_review reviewT, LATERAL " +
				"(select COUNT(*) as likeNumber FROM user_rates_book_review WHERE users = reviewT.users and book = reviewT.book and rated = true) likeT, LATERAL " +
				"(select COUNT(*) as dislikeNumber FROM user_rates_book_review WHERE users = reviewT.users and book = reviewT.book and rated = false) dislikeT "
				+ "where book = ? and users = ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		statment.setString(2, nickname);
		ResultSet result = statment.executeQuery();

		BookReview review = null;
		if (result.next()) {
			review = buildReview(result, false);
		}

		result.close();
		statment.close();
		connection.close();
		return review;
	}


	@Override
	public ArrayList<BookReview> getReviewsByUserRater(String title, String nickname, Integer offset) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT reviewT.users, reviewT.book, numberofstars, description, reviewT.postdate, rat.rated, likeNumber, dislikeNumber "
				+ "FROM book_review reviewT LEFT JOIN (SELECT users, book, rated FROM user_rates_book_review WHERE userthatrates = ?) as rat "
				+ "ON reviewT.users = rat.users and reviewT.book = rat.book, LATERAL " +
				"(select COUNT(*) as likeNumber FROM user_rates_book_review WHERE users = reviewT.users and book = reviewT.book and rated = true) likeT, LATERAL " +
				"(select COUNT(*) as dislikeNumber FROM user_rates_book_review WHERE users = reviewT.users and book = reviewT.book and rated = false) dislikeT "
				+ "where reviewT.book = ? ORDER BY reviewT.postdate DESC, likeNumber DESC, dislikeNumber ASC, numberofstars DESC, reviewT.users DESC " +
				"limit 15 offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);
		statment.setString(2, title);
		statment.setInt(3, offset);
		ResultSet result = statment.executeQuery();

		ArrayList<BookReview> reviews = new ArrayList<BookReview>();
		while (result.next()) {
			reviews.add(buildReview(result, true));
		}

		result.close();
		statment.close();
		connection.close();

		return reviews;
	}

	private BookReview buildReview(ResultSet result, boolean withRateMode) throws SQLException {
		String user = result.getString("users");
		String book = result.getString("book");
		short numberOfStars = result.getShort("numberofstars");
		String description = result.getString("description");
		Date postDate = result.getDate("postdate");
		Integer likeNumber = result.getInt("likeNumber");
		Integer dislikeNumber = result.getInt("dislikeNumber");

		BookReview review = new BookReview();
		review.setUser(user);
		review.setBook(book);
		review.setDescription(description);
		review.setNumberOfStars(numberOfStars);
		review.setPostDate(postDate);
		review.setLikeNumber(likeNumber);
		review.setDislikeNumber(dislikeNumber);

		if (withRateMode) {
			Boolean rating = result.getBoolean("rated");
			if (result.wasNull()) {
				rating = null;
			}

			review.setActualUserRate(rating);
		}

		return review;
	}

	@Override
	public ArrayList<Book> searchByKeyWords(String[] keyWords, int limit, int offset) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		int keySize = keyWords.length;

		String query = "with tokens as (select unnest(array[";

		for(int i = 0; i < keySize; i++) {
			query += "?";
			if(i < keySize - 1)
				query += ",";
		}

		query += "]) AS tok) " +
				"select title, users, imageid, rating, COUNT(*) AS numTok, SUM(occ) AS sumOcc from book bookT, tokens tokenT, LATERAL (select count(*) - 1 as occ from regexp_split_to_table(bookT.title, tokenT.tok, 'i')) occT " +
				"where occ != 0 " +
				"GROUP BY title, users, imageid, rating " +
				"ORDER BY numTok DESC, sumOcc DESC, rating DESC, imageid DESC " +
				"LIMIT ? OFFSET ?";

		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= keySize; i++) {
			statment.setString(i, keyWords[i - 1]);
		}
		statment.setInt(keySize + 1, limit);
		statment.setInt(keySize + 2, offset);

		ResultSet result = statment.executeQuery();

		ArrayList<Book> books = new ArrayList<Book>();
		while (result.next()) {
			books.add(buildSimplifiedBook(result));
		}

		result.close();
		statment.close();
		connection.close();

		return books;
	}

	@Override
	public ArrayList<Book> searchByUser(String user, Integer offset, Integer modality, Integer order) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, imageid, rating " + "from book " +
				"where users = ?";

		String orderString = (order == 0) ? "ASC" : "DESC";

		if(modality == 0)
			query += " order by title " + orderString;
		else
			query += " order by postdate " + orderString + ", imageid " + orderString;

		query += " limit 20 offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, user);
		statment.setInt(2, offset);
		ResultSet result = statment.executeQuery();
		ArrayList<Book> books = new ArrayList<Book>();

		while (result.next()) {
			books.add(buildSimplifiedBook(result));
		}

		result.close();
		statment.close();
		connection.close();

		return books;
	}

	@Override
	public ArrayList<Book> searchByUserWithKeyWords(String user, String[] keyWords, Integer offset, Integer modality, Integer order) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		int keySize = keyWords.length;

		String query = "with tokens as (select unnest(array[";

		for(int i = 0; i < keySize; i++) {
			query += "?";
			if(i < keySize - 1)
				query += ",";
		}

		query += "]) AS tok) " +
				"select title, users, imageid, rating, COUNT(*) AS numTok, SUM(occ) AS sumOcc from book bookT, tokens tokenT, LATERAL (select count(*) - 1 as occ from regexp_split_to_table(bookT.title, tokenT.tok, 'i')) occT " +
				"where occ != 0 and users = ? " +
				"GROUP BY title, users, imageid, rating ";

		String orderString = (order == 0) ? "ASC" : "DESC";

		if(modality == 0)
			query += " order by title " + orderString;
		else
			query += " order by postdate " + orderString + ", imageid " + orderString;

		query += " limit 20 offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= keySize; i++) {
			statment.setString(i, keyWords[i - 1]);
		}
		statment.setString(keySize + 1, user);
		statment.setInt(keySize + 2, offset);

		ResultSet result = statment.executeQuery();

		ArrayList<Book> books = new ArrayList<Book>();
		while (result.next()) {
			books.add(buildSimplifiedBook(result));
		}

		result.close();
		statment.close();
		connection.close();

		return books;
	}

    @Override
    public Integer getUserCountWithKeyWords(String user, String[] keyWords) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		int keySize = keyWords.length;

		String query = "with tokens as (select unnest(array[";

		for(int i = 0; i < keySize; i++) {
			query += "?";
			if(i < keySize - 1)
				query += ",";
		}

		query += "]) AS tok) " +
				"select count(distinct title) as num from book bookT, tokens tokenT, LATERAL (select count(*) - 1 as occ from regexp_split_to_table(bookT.title, tokenT.tok, 'i')) occT " +
				"where occ != 0 and users = ? ";

		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= keySize; i++) {
			statment.setString(i, keyWords[i - 1]);
		}
		statment.setString(keySize + 1, user);

		ResultSet result = statment.executeQuery();

		Integer num = null;

		result.next();

		num = result.getInt("num");

		result.close();
		statment.close();
		connection.close();

		return num;
    }

    @Override
	public void addReview(BookReview review) throws SQLException {
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
	public void deleteReview(String nickname, String title) throws SQLException {
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
	public void updateReview(BookReview review) throws SQLException {
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
	public ArrayList<Book> getBestBooks() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, rating, imageid from book Order by rating DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		ResultSet result = statment.executeQuery();
		ArrayList<Book> books = new ArrayList<Book>();

		while (result.next()) {

			books.add(buildSimplifiedBook(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (books.size() > 0) {
			return books;
		} else {
			throw new RuntimeException("No books found in this genre");
		}
	}

	@Override
	public ArrayList<Book> getLatestBooks() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, rating, imageid from book Order by postdate DESC, imageid DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		ResultSet result = statment.executeQuery();
		ArrayList<Book> books = new ArrayList<Book>();

		while (result.next()) {

			books.add(buildSimplifiedBook(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (books.size() > 0) {
			return books;
		} else {
			throw new RuntimeException("No books found in this genre");
		}
	}

	@Override
	public ArrayList<Book> getRandomBooksByConditions(int limit, boolean mostRated) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		
		String query = "select title, users, rating, imageid " +
					   "from book ";

		if (mostRated) {
			query += "where rating = (select max(rating) from book) ";
		}

		query += "order by random() limit ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, limit);

		ResultSet result = statement.executeQuery();
		ArrayList<Book> books = new ArrayList<>();
		while (result.next()) {
			books.add(buildSimplifiedBook(result));
		}

		try {
			return books;
		} finally {
			connection.close();
			result.close();
			statement.close();
		}
	}

	@Override
	public ArrayList<Book> getBestBooksByGenre(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select distinct title, users, imageid, rating from book inner join genre_book on book.title = genre_book.book where genre_book.genre = ? Order by rating DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		ResultSet result = statment.executeQuery();
		ArrayList<Book> books = new ArrayList<Book>();

		while (result.next()) {
			books.add(buildSimplifiedBook(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (books.size() > 0) {
			return books;
		} else {
			throw new RuntimeException("No books found in this genre");
		}
	}

	@Override
	public ArrayList<Book> getLatestBooksByGenre(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, imageid, rating from book inner join genre_book on book.title = genre_book.book where genre_book.genre = ? Order by postdate DESC, imageid DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		ResultSet result = statment.executeQuery();
		ArrayList<Book> books = new ArrayList<Book>();

		while (result.next()) {
			books.add(buildSimplifiedBook(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (books.size() > 0) {
			return books;
		} else {
			throw new RuntimeException("No books found in this genre");
		}
	}

	@Override
	public Integer getBooksNumberByGenre(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "SELECT COUNT(DISTINCT book) as count from genre_book where genre = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		ResultSet result = statment.executeQuery();
		result.next();
		Integer count = result.getInt("count");

		result.close();
		statment.close();
		connection.close();

		if (count > 0) {
			return count;
		} else {
			throw new RuntimeException("No books found in this genre");
		}
	}

	@Override
	public ArrayList<Book> getRandomBooks(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "select title, users, imageid, rating from book inner join genre_book on book.title = genre_book.book where genre_book.genre = ? Order by random() limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		ResultSet result = statment.executeQuery();
		ArrayList<Book> books = new ArrayList<Book>();

		while (result.next()) {
			books.add(buildSimplifiedBook(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (books.size() > 0) {
			return books;
		} else {
			throw new RuntimeException("No books found in this genre");
		}
	}
}
