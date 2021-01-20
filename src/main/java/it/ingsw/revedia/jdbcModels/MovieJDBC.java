package it.ingsw.revedia.jdbcModels;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.model.Movie;
import it.ingsw.revedia.model.MovieReview;

public class MovieJDBC implements MovieDao
{

	private DataSource dataSource;

	public MovieJDBC()
	{
		super();
	}

	public MovieJDBC(DataSource dataSource)
	{
		super();
		this.dataSource = dataSource;
	}

	@Override
	public Movie findByPrimaryKey(String title) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select select title, length, description, link, users from movie where title = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		ResultSet result = statment.executeQuery();

		connection.close();

		Movie movie = null;
		while (result.next())
		{
			movie = buildMovie(result);
		}

		result.close();
		statment.close();

		if (movie != null)
		{
			return movie;
		} else
		{
			throw new RuntimeException("Movie not found with this title");
		}
	}

	@Override
	public ArrayList<Movie> getMoviesByGenre(String genre) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select distinct title, length, description, link, users " + "from movie "
				+ "inner join genre_movie " + "on movie.title = genre_movie.movie " + "where genre_movie.genre = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		ResultSet result = statment.executeQuery();
		ArrayList<Movie> movies = new ArrayList<Movie>();

		while (result.next())
		{
			movies.add(buildMovie(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (movies.size() > 0)
		{
			return movies;
		} else
		{
			throw new RuntimeException("No movies found in this genre");
		}
	}

	@Override
	public void insertMovie(Movie movie) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "insert into movie(title, length, description, link, users) values(?,?,?,?,?)";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, movie.getTitle());
		statment.setFloat(2, movie.getLength());
		statment.setString(3, movie.getDescription());
		statment.setString(4, movie.getLink());
		statment.setString(5, movie.getUser());
		statment.execute();
		statment.close();

		query = "insert into genre_movie(genre, movie) values(?,?)";
		statment = connection.prepareStatement(query);
		for (String genre : movie.getGenres())
		{
			statment.setString(1, genre);
			statment.setString(2, movie.getTitle());
			statment.execute();
		}

		statment.close();
		connection.close();
	}

	@Override
	public void deleteMovie(String title) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "delete from movie where title = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public void updateMovie(Movie movie) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "update movie set length = ?, description = ?, link = ? " + "where title = ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setFloat(1, movie.getLength());
		statment.setString(2, movie.getDescription());
		statment.setString(3, movie.getLink());
		statment.setString(4, movie.getTitle());
		statment.executeUpdate();
		statment.close();
		connection.close();
	}

	private Movie buildMovie(ResultSet result) throws SQLException
	{
		String title = result.getString("title");
		float length = result.getFloat("length");
		String description = result.getString("description");
		String link = result.getString("link");
		String user = result.getString("users");
		float rating = result.getFloat("rating");
		Date postDate = result.getDate("postdate");

		Movie movie = new Movie();
		movie.setTitle(title);
		movie.setLength(length);
		movie.setDescription(description);
		movie.setLink(link);
		movie.setUser(user);
		movie.setGenres(getGenres(title));
		movie.setRating(rating);
		movie.setPostDate(postDate);

		return movie;
	}

	private ArrayList<String> getGenres(String title) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select genre from genre_movie where movie = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		ResultSet result = statment.executeQuery();

		ArrayList<String> genres = new ArrayList<String>();
		while (result.next())
		{
			genres.add(result.getString("genre"));
		}

		result.close();
		statment.close();
		connection.close();

		return genres;

	}

	@Override
	public ArrayList<MovieReview> getReviews(String title) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select users, movie, numberofstars, description, postdate " + "from movie_review "
				+ "where movie = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		ResultSet result = statment.executeQuery();

		ArrayList<MovieReview> reviews = new ArrayList<MovieReview>();
		while (result.next())
		{
			reviews.add(buildReview(result));
		}

		result.close();
		statment.close();
		connection.close();

		return reviews;
	}

	private MovieReview buildReview(ResultSet result) throws SQLException
	{
		String user = result.getString("users");
		String movie = result.getString("movie");
		short numberOfStars = result.getShort("numberofstars");
		String description = result.getString("description");
		Date postDate = result.getDate("postdate");

		MovieReview review = new MovieReview();
		review.setUser(user);
		review.setMovie(movie);
		review.setNumberOfStars(numberOfStars);
		review.setDescription(description);
		review.setPostDate(postDate);

		return review;
	}

	@Override
	public ArrayList<Movie> searchByKeyWords(String keyWords, int limit, int offset) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select title, length, description, link, users, rating, postdate " + "from movie "
				+ "where title similar to ? " + "limit ? offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, keyWords);
		statment.setInt(2, limit);
		statment.setInt(3, offset);

		ResultSet result = statment.executeQuery();
		ArrayList<Movie> movies = new ArrayList<Movie>();

		while (result.next())
		{
			movies.add(buildMovie(result));
		}

		result.close();
		statment.close();
		connection.close();

		return movies;
	}

	@Override
	public void addReview(MovieReview review) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "insert into movie_review(users, movie, numberofstars, description) values(?,?,?,?)";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, review.getUser());
		statment.setString(2, review.getMovie());
		statment.setShort(3, review.getNumberOfStars());
		statment.setString(4, review.getDescription());
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public void deleteReview(String nickname, String title) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "delete from movie_review where users = ? and movie = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);
		statment.setString(2, title);
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public void updateReview(MovieReview review) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "update movie_review set numberofstars = ?, description = ? " + "where users = ? and title = ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setShort(1, review.getNumberOfStars());
		statement.setString(2, review.getDescription());
		statement.setString(3, review.getUser());
		statement.setString(4, review.getMovie());
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

	@Override
	public List<Movie> findAll() throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		List<Movie> movies = new ArrayList<>();

		Movie movie = null;

		String query = "select * from book";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		while (result.next())
		{
			movie = findByPrimaryKey(result.getString("title"));
			movies.add(movie);
		}

		result.close();
		connection.close();

		return movies;
	}
}
