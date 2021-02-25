package it.ingsw.revedia.jdbcModels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.ingsw.revedia.daoInterfaces.UserDao;
import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.PasswordManager;
import it.ingsw.revedia.utilities.Permissions;

public class UserJDBC implements UserDao
{
	private DataSource dataSource;

	public UserJDBC()
	{
		super();
	}

	public UserJDBC(DataSource dataSource)
	{
		super();
		this.dataSource = dataSource;
	}

	@Override
	public User getUser(String nickname) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select nickname, firstname, lastname, mail, permissions from users where nickname = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);

		ResultSet result = statment.executeQuery();
		result.next();

		User user = null;
		user = buildUser(result);

		statment.close();
		result.close();
		connection.close();
		return user;
	}

	@Override
	public User getUserByNicknameOrMail(String nickname, String mail) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select nickname, firstname, lastname, mail, permissions from users where nickname = ? " +
				       "or mail = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);
		statment.setString(2,mail);

		ResultSet result = statment.executeQuery();

		User user = null;
		if(result.next()) {
			user = buildUser(result);
		}

		statment.close();
		result.close();
		connection.close();

		return user;
	}

	private static User buildUser(ResultSet result) throws SQLException
	{
		String nick = result.getString("nickname");
		String firstName = result.getString("firstname");
		String lastName = result.getString("lastname");
		String mail = result.getString("mail");
		Permissions permissions = Permissions.valueOf(result.getString("permissions"));
		User user = new User();
		user.setNickname(nick);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setMail(mail);
		user.setPermissions(permissions);

		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "update users set firstname = ?, lastname = ?, mail = ? where nickname = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, user.getFirstName());
		statment.setString(2, user.getLastName());
		statment.setString(3, user.getMail());
		statment.setString(4, user.getNickname());
		statment.executeUpdate();
		statment.close();
		connection.close();
	}

	@Override
	public void insertUser(User user, String password) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "insert into users(nickname, firstname, lastname, passwd, mail, permissions)"
				+ "values (?,?,?,?,?,?)";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, user.getNickname());
		statment.setString(2, user.getFirstName());
		statment.setString(3, user.getLastName());
		statment.setString(4, password);
		statment.setString(5, user.getMail());
		statment.setString(6, Permissions.STANDARD.toString());
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public void deleteUser(String nickname) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "delete from users where nickname = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, nickname);
		statement.execute();
		statement.close();
		connection.close();
	}

	@Override
	public boolean changePassword(String oldPassword, String newPassword, String nickname, String mail)
			throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		if (validateLogin(oldPassword, nickname))
		{
			String query = "update users set passwd = ? where nickname = ?";
			PreparedStatement statment = connection.prepareStatement(query);
			statment.setString(1, newPassword);
			statment.setString(2, nickname);
			statment.executeUpdate();
			statment.close();
			connection.close();
			return true;
		}

		return false;
	}

	@Override
	public void changePermissions(Permissions permissions, String nickname) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "update users set permissions = ? where nickname = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, permissions.toString());
		statment.setString(2, nickname);
		statment.executeUpdate();
		statment.close();
		connection.close();
	}

	@Override
	public boolean validateLogin(String password, String nickname) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select passwd from users where nickname = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);
		ResultSet result = statment.executeQuery();

		while (result.next())
		{
			if (password.equals(result.getString("passwd")))
			{
				statment.close();
				result.close();
				connection.close();
				return true;
			}
		}

		statment.close();
		result.close();
		connection.close();
		return false;
	}

	@Override
	public boolean validateLoginByNicknameOrMail(String nickname, String mail, String password) throws SQLException, TupleNotFoundException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select passwd from users where nickname = ? or mail = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);
		statment.setString(2,mail);
		ResultSet result = statment.executeQuery();
		if(result.next())
		{
			if (password.equals(result.getString("passwd")))
			{
				statment.close();
				result.close();
				connection.close();
				return true;
			}
			else
			{
				statment.close();
				result.close();
				connection.close();
				return false;
			}
		}
		else
		{
			connection.close();
			throw new TupleNotFoundException("No users found with this mail or username");
		}
	}

	@Override
	public Integer getNextGoogleIdValue() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT nextval('user_googleid_seq')";
		PreparedStatement statment = connection.prepareStatement(query);
		ResultSet result = statment.executeQuery();

		result.next();
		Integer value = result.getInt(1);

		statment.close();
		result.close();
		connection.close();

		return value;
	}

	@Override
	public Float getAvgQuality(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT AVG(partialRateAvg) AS userRateAvg FROM " +
				"((SELECT AVG(tmp.rateAvg)*100 AS partialRateAvg FROM ((SELECT AVG(rated::int) AS rateAvg " +
				"FROM user_rates_album_review " +
				"WHERE users = ?) " +
				"UNION " +
				"(SELECT AVG(rated::int) AS rateAvg " +
				"FROM user_rates_song_review " +
				"WHERE users = ?) " +
				"UNION " +
				"(SELECT AVG(rated::int) AS rateAvg " +
				"FROM user_rates_movie_review " +
				"WHERE users = ?) " +
				"UNION " +
				"(SELECT AVG(rated::int) AS rateAvg " +
				"FROM user_rates_book_review " +
				"WHERE users = ?)) AS tmp) " +
				"UNION " +
				"(SELECT AVG(tmp.rateAvg) * 20 AS partialRateAvg FROM ((SELECT AVG(numberofstars) AS rateAvg " +
				"FROM album_review INNER JOIN album ON album_review.album = album.albumid " +
				"WHERE album.users = ?) " +
				"UNION " +
				"(SELECT AVG(numberofstars) AS rateAvg " +
				"FROM song_review INNER JOIN song ON song_review.song = song.name AND song_review.album = song.album " +
				"WHERE song.users = ?) " +
				"UNION " +
				"(SELECT AVG(numberofstars) AS rateAvg " +
				"FROM movie_review INNER JOIN movie ON movie_review.movie = movie.title " +
				"WHERE movie.users = ?) " +
				"UNION " +
				"(SELECT AVG(numberofstars) AS rateAvg " +
				"FROM book_review INNER JOIN book ON book_review.book = book.title " +
				"WHERE book.users = ?)) AS tmp)) AS avgs";
		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= 8; i++)
			statment.setString(i, nickname);

		ResultSet result = statment.executeQuery();

		Float value = null;
		if (result.next())
		{
			value = result.getFloat("userrateavg");
			if(result.wasNull())
				value = null;
		}

		statment.close();
		result.close();
		connection.close();
		return value;
	}

	@Override
	public Float getAvgRating(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT AVG(partialRateAvg) AS userRateAvg FROM " +
				"((SELECT AVG(tmp.rateAvg)*100 AS partialRateAvg FROM ((SELECT AVG(rated::int) AS rateAvg " +
				"FROM user_rates_album_review " +
				"WHERE userthatrates = ?) " +
				"UNION " +
				"(SELECT AVG(rated::int) AS rateAvg " +
				"FROM user_rates_song_review " +
				"WHERE userthatrates = ?) " +
				"UNION " +
				"(SELECT AVG(rated::int) AS rateAvg " +
				"FROM user_rates_movie_review " +
				"WHERE userthatrates = ?) " +
				"UNION " +
				"(SELECT AVG(rated::int) AS rateAvg " +
				"FROM user_rates_book_review " +
				"WHERE userthatrates = ?)) AS tmp) " +
				"UNION " +
				"(SELECT AVG(tmp.rateAvg)*20 AS partialRateAvg FROM ((SELECT AVG(numberofstars) AS rateAvg " +
				"FROM album_review " +
				"WHERE users = ?) " +
				"UNION " +
				"(SELECT AVG(numberofstars) AS rateAvg " +
				"FROM song_review " +
				"WHERE users = ?) " +
				"UNION " +
				"(SELECT AVG(numberofstars) AS rateAvg " +
				"FROM movie_review " +
				"WHERE users = ?) " +
				"UNION " +
				"(SELECT AVG(numberofstars) AS rateAvg " +
				"FROM book_review " +
				"WHERE users = ?)) AS tmp)) AS avgs";
		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= 8; i++)
			statment.setString(i, nickname);

		ResultSet result = statment.executeQuery();

		Float value = null;
		if (result.next())
		{
			value = result.getFloat("userrateavg");
			if(result.wasNull())
				value = null;
		}

		statment.close();
		result.close();
		connection.close();
		return value;
	}

	@Override
	public Integer getNumRatedReviews(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT SUM(tmp.numReviews) AS ratedReviews FROM ((SELECT COUNT(*) AS numReviews " +
				"FROM user_rates_album_review " +
				"WHERE userthatrates = ?) " +
				"UNION " +
				"(SELECT COUNT(*) AS numReviews " +
				"FROM user_rates_song_review " +
				"WHERE userthatrates = ?) " +
				"UNION " +
				"(SELECT COUNT(*) AS numReviews " +
				"FROM user_rates_movie_review " +
				"WHERE userthatrates = ?) " +
				"UNION " +
				"(SELECT COUNT(*) AS numReviews " +
				"FROM user_rates_book_review " +
				"WHERE userthatrates = ?)) AS tmp";
		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= 4; i++)
			statment.setString(i, nickname);

		ResultSet result = statment.executeQuery();

		Integer value = null;
		if (result.next())
		{
			value = result.getInt("ratedreviews");
			if(result.wasNull())
				value = null;
		}

		statment.close();
		result.close();
		connection.close();
		return value;
	}

	@Override
	public String getBestReview(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "WITH reviewRatings AS " +
				"((SELECT name AS contentName, SUM(rated::int) AS sumRate " +
				"FROM user_rates_album_review INNER JOIN album ON user_rates_album_review.album = album.albumid " +
				"WHERE user_rates_album_review.users = ? " +
				"GROUP BY albumid, name) " +
				"UNION " +
				"(SELECT concat(song, ' - ', name) AS contentName, SUM(rated::int) AS sumRate " +
				"FROM user_rates_song_review INNER JOIN album ON user_rates_song_review.album = album.albumid " +
				"WHERE user_rates_song_review.users = ? " +
				"GROUP BY song, albumid, name) " +
				"UNION " +
				"(SELECT movie AS contentName, SUM(rated::int) AS sumRate " +
				"FROM user_rates_movie_review " +
				"WHERE user_rates_movie_review.users = ? " +
				"GROUP BY movie) " +
				"UNION " +
				"(SELECT book AS contentName, SUM(rated::int) AS sumRate " +
				"FROM user_rates_book_review " +
				"WHERE user_rates_book_review.users = ? " +
				"GROUP BY book)) " +
				"SELECT contentname FROM reviewRatings " +
				"WHERE sumRate = (SELECT MAX(sumRate) FROM reviewRatings) " +
				"LIMIT 1";
		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= 4; i++)
			statment.setString(i, nickname);

		ResultSet result = statment.executeQuery();

		String value = null;
		if (result.next())
		{
			value = result.getString("contentname");
			if(result.wasNull())
				value = null;
		}

		statment.close();
		result.close();
		connection.close();
		return value;
	}

	@Override
	public String getFavouriteCat(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "WITH ratings AS " +
				"(SELECT AVG(partRating) AS catRating, 'Musica' AS catname FROM " +
				"(SELECT SUM(rating) AS partRating FROM " +
				"(SELECT SUM(numberofstars) AS rating " +
				"FROM album_review " +
				"WHERE users = ? " +
				"UNION " +
				"SELECT SUM(rated::int) AS rating " +
				"FROM user_rates_album_review " +
				"WHERE userthatrates = ?) " +
				"AS album_rating " +
				"UNION " +
				"SELECT SUM(rating) AS partRating FROM " +
				"(SELECT SUM(numberofstars) AS rating " +
				"FROM song_review " +
				"WHERE users = ? " +
				"UNION " +
				"SELECT SUM(rated::int) AS rating " +
				"FROM user_rates_song_review " +
				"WHERE userthatrates = ?) " +
				"AS song_rating) " +
				"AS music_rating " +
				"UNION " +
				"SELECT SUM(rating) AS catRating, 'Film' AS catname FROM " +
				"(SELECT SUM(numberofstars) AS rating " +
				"FROM movie_review " +
				"WHERE users = ? " +
				"UNION " +
				"SELECT SUM(rated::int) AS rating " +
				"FROM user_rates_movie_review " +
				"WHERE userthatrates = ?) " +
				"AS movie_rating " +
				"UNION " +
				"SELECT SUM(rating) AS catRating, 'Libri' AS catname FROM " +
				"(SELECT SUM(numberofstars) AS rating " +
				"FROM book_review " +
				"WHERE users = ? " +
				"UNION " +
				"SELECT SUM(rated::int) AS rating " +
				"FROM user_rates_book_review " +
				"WHERE userthatrates = ?) " +
				"AS book_rating) " +
				"SELECT catname " +
				"FROM ratings " +
				"WHERE catrating = (SELECT MAX(catrating) FROM ratings) " +
				"LIMIT 1";
		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= 8; i++)
			statment.setString(i, nickname);

		ResultSet result = statment.executeQuery();

		String value = null;
		if (result.next())
		{
			value = result.getString("catname");
			if(result.wasNull())
				value = null;
		}

		statment.close();
		result.close();
		connection.close();
		return value;
	}

	@Override
	public Map<String, Object> getNumReviews(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT SUM(tmp.numReviews) AS contentswithreview, SUM(tmp.numReviews) / (SELECT SUM(tmp.numContents) FROM ((SELECT COUNT(*) AS numContents " +
				"FROM album) " +
				"UNION " +
				"(SELECT COUNT(*) AS numContents " +
				"FROM song) " +
				"UNION " +
				"(SELECT COUNT(*) AS numContents " +
				"FROM movie) " +
				"UNION " +
				"(SELECT COUNT(*) AS numContents " +
				"FROM book)) AS tmp) * 100 AS contentswithreviewperc FROM " +
				"((SELECT COUNT(*) AS numReviews " +
				"FROM album_review " +
				"WHERE users = ?) " +
				"UNION " +
				"(SELECT COUNT(*) AS numReviews " +
				"FROM song_review " +
				"WHERE users = ?) " +
				"UNION " +
				"(SELECT COUNT(*) AS numReviews " +
				"FROM movie_review " +
				"WHERE users = ?) " +
				"UNION " +
				"(SELECT COUNT(*) AS numReviews " +
				"FROM book_review " +
				"WHERE users = ?)) AS tmp";
		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= 4; i++)
			statment.setString(i, nickname);

		ResultSet result = statment.executeQuery();

		Map<String, Object> values = new HashMap<>();
		if (result.next())
		{
			values.put("numReviews", result.getInt("contentswithreview"));
			values.put("numReviewsPerc", result.getFloat("contentswithreviewperc"));
		}

		statment.close();
		result.close();
		connection.close();
		return values;
	}

	@Override
	public Map<String, String> getFavouriteGenreForCat(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "WITH musicalratings AS " +
				"(SELECT musical_genre, AVG(partRating) AS catRating FROM " +
				"(SELECT musical_genre, SUM(rating) AS partRating FROM  " +
				"(SELECT musical_genre, SUM(numberofstars) AS rating " +
				"FROM album_review INNER JOIN musical_genre_album ON album_review.album = musical_genre_album.album " +
				"WHERE users = ? " +
				"GROUP BY musical_genre " +
				"UNION " +
				"SELECT musical_genre, SUM(rated::int) AS rating " +
				"FROM user_rates_album_review INNER JOIN musical_genre_album ON user_rates_album_review.album = musical_genre_album.album " +
				"WHERE userthatrates = ? " +
				"GROUP BY musical_genre) " +
				"AS album_rating " +
				"GROUP BY musical_genre " +
				"UNION " +
				"SELECT musical_genre, SUM(rating) AS partRating FROM  " +
				"(SELECT musical_genre, SUM(numberofstars) AS rating " +
				"FROM song_review INNER JOIN musical_genre_album ON song_review.album = musical_genre_album.album " +
				"WHERE users = ? " +
				"GROUP BY musical_genre " +
				"UNION " +
				"SELECT musical_genre, SUM(rated::int) AS rating " +
				"FROM user_rates_song_review INNER JOIN musical_genre_album ON user_rates_song_review.album = musical_genre_album.album " +
				"WHERE userthatrates = ? " +
				"GROUP BY musical_genre) " +
				"AS song_rating " +
				"GROUP BY musical_genre) " +
				"AS music_rating " +
				"GROUP BY musical_genre), " +
				"moviesratings AS " +
				"(SELECT genre, SUM(rating) AS catRating FROM " +
				"(SELECT genre, SUM(numberofstars) AS rating " +
				"FROM movie_review INNER JOIN genre_movie ON movie_review.movie = genre_movie.movie " +
				"WHERE users = ? " +
				"GROUP BY genre " +
				"UNION " +
				"SELECT genre, SUM(rated::int) AS rating " +
				"FROM user_rates_movie_review INNER JOIN genre_movie ON user_rates_movie_review.movie = genre_movie.movie " +
				"WHERE userthatrates = ? " +
				"GROUP BY genre) " +
				"AS movie_rating " +
				"GROUP BY genre), " +
				"booksratings AS " +
				"(SELECT genre, SUM(rating) AS catRating FROM " +
				"(SELECT genre, SUM(numberofstars) AS rating " +
				"FROM book_review INNER JOIN genre_book ON book_review.book = genre_book.book " +
				"WHERE users = ? " +
				"GROUP BY genre " +
				"UNION " +
				"SELECT genre, SUM(rated::int) AS rating " +
				"FROM user_rates_book_review INNER JOIN genre_book ON user_rates_book_review.book = genre_book.book " +
				"WHERE userthatrates = ? " +
				"GROUP BY genre) " +
				"AS book_rating " +
				"GROUP BY genre) " +
				"(SELECT musical_genre AS genre, 'Musica' AS catname, catRating " +
				"FROM musicalratings " +
				"WHERE catRating = (SELECT MAX(catRating) FROM musicalratings) " +
				"LIMIT 1) " +
				"UNION " +
				"(SELECT genre, 'Film' AS catname, catRating " +
				"FROM moviesratings " +
				"WHERE catRating = (SELECT MAX(catRating) FROM moviesratings) " +
				"LIMIT 1) " +
				"UNION " +
				"(SELECT genre, 'Libri' AS catname, catRating " +
				"FROM booksratings " +
				"WHERE catRating = (SELECT MAX(catRating) FROM booksratings) " +
				"LIMIT 1)";
		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= 8; i++)
			statment.setString(i, nickname);

		ResultSet result = statment.executeQuery();

		Map<String, String> values = new HashMap<>();
		while (result.next())
		{
			values.put(result.getString("catname"), result.getString("genre"));
		}

		statment.close();
		result.close();
		connection.close();
		return values;
	}

	@Override
	public Map<String, List<Object>> getContributeForDay(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT SUM(numcont) AS num, postdate FROM " +
				"(SELECT COUNT(*) * 100 AS numCont, postdate FROM " +
				"album " +
				"WHERE users = ? " +
				"GROUP BY postdate " +
				"UNION " +
				"SELECT COUNT(*) * 100 AS numCont, postdate FROM " +
				"song " +
				"WHERE users = ? " +
				"GROUP BY postdate " +
				"UNION " +
				"SELECT COUNT(*) * 100 AS numCont, postdate FROM " +
				"movie " +
				"WHERE users = ? " +
				"GROUP BY postdate " +
				"UNION " +
				"SELECT COUNT(*) * 100 AS numCont, postdate FROM " +
				"book " +
				"WHERE users = ? " +
				"GROUP BY postdate " +
				"UNION " +
				"SELECT COUNT(*) * 50 AS numCont, postdate FROM " +
				"album_review " +
				"WHERE users = ? " +
				"GROUP BY postdate " +
				"UNION " +
				"SELECT COUNT(*) * 50 AS numCont, postdate FROM " +
				"song_review " +
				"WHERE users = ? " +
				"GROUP BY postdate " +
				"UNION " +
				"SELECT COUNT(*) * 50 AS numCont, postdate FROM " +
				"movie_review " +
				"WHERE users = ? " +
				"GROUP BY postdate " +
				"UNION " +
				"SELECT COUNT(*) * 50 AS numCont, postdate FROM " +
				"book_review " +
				"WHERE users = ? " +
				"GROUP BY postdate " +
				"UNION " +
				"SELECT COUNT(*) * 25 AS numCont, postdate FROM " +
				"user_rates_album_review " +
				"WHERE userthatrates = ? " +
				"GROUP BY postdate " +
				"UNION " +
				"SELECT COUNT(*) * 25 AS numCont, postdate FROM " +
				"user_rates_song_review " +
				"WHERE userthatrates = ? " +
				"GROUP BY postdate " +
				"UNION " +
				"SELECT COUNT(*) * 25 AS numCont, postdate FROM " +
				"user_rates_movie_review " +
				"WHERE userthatrates = ? " +
				"GROUP BY postdate " +
				"UNION " +
				"SELECT COUNT(*) * 25 AS numCont, postdate FROM " +
				"user_rates_book_review " +
				"WHERE userthatrates = ? " +
				"GROUP BY postdate) AS tmp " +
				"GROUP BY postdate";
		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= 12; i++)
			statment.setString(i, nickname);

		ResultSet result = statment.executeQuery();

		Map<String, List<Object>> values = new HashMap<>();
		List<Object> days = new ArrayList<>();
		List<Object> contribute = new ArrayList<>();
		while (result.next())
		{
			days.add(result.getDate("postdate"));
			contribute.add(result.getInt("num"));
		}

		values.put("days", days);
		values.put("contribute", contribute);

		statment.close();
		result.close();
		connection.close();
		return values;
	}

    @Override
    public Integer getNumLoadedAlbums(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT COUNT(*) AS num FROM album WHERE users = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);

		ResultSet result = statment.executeQuery();

		result.next();
		Integer value = result.getInt("num");

		statment.close();
		result.close();
		connection.close();
		return value;
    }

	@Override
	public Integer getNumLoadedSongs(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT COUNT(*) AS num FROM song WHERE users = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);

		ResultSet result = statment.executeQuery();

		result.next();
		Integer value = result.getInt("num");

		statment.close();
		result.close();
		connection.close();
		return value;
	}

	@Override
	public Integer getNumLoadedMovies(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT COUNT(*) AS num FROM movie WHERE users = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);

		ResultSet result = statment.executeQuery();

		result.next();
		Integer value = result.getInt("num");

		statment.close();
		result.close();
		connection.close();
		return value;
	}

	@Override
	public Integer getNumLoadedBooks(String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT COUNT(*) AS num FROM book WHERE users = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);

		ResultSet result = statment.executeQuery();

		result.next();
		Integer value = result.getInt("num");

		statment.close();
		result.close();
		connection.close();
		return value;
	}
}