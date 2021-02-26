package it.ingsw.revedia.jdbcModels;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.model.*;

public class MovieJDBC implements MovieDao {

	private DataSource dataSource;

	public MovieJDBC() {
		super();
	}

	public MovieJDBC(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public Movie findByPrimaryKey(String title) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select * from movie where title = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		ResultSet result = statment.executeQuery();

		Movie movie = null;
		while (result.next()) {
			movie = buildMovie(result);
		}

		result.close();
		statment.close();
		connection.close();

		return movie;
	}

	@Override
	public ArrayList<Movie> findByGenre(String genre, Integer offset, Integer modality, Integer order) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, imageid, rating " + "from movie " + "inner join genre_movie "
				+ "on movie.title = genre_movie.movie " + "where genre_movie.genre = ?";

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
		ArrayList<Movie> movies = new ArrayList<Movie>();

		while (result.next()) {
			movies.add(buildSimplifiedMovie(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (movies.size() > 0) {
			return movies;
		} else {
			throw new RuntimeException("No movies found in this genre");
		}
	}

    @Override
    public Movie findMovie(Movie movie) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select * " + "from movie " + "where title = ? and length = ? limit 1";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, movie.getTitle());
		statment.setFloat(2, movie.getLength());
		ResultSet result = statment.executeQuery();

		Movie dbMovie = null;
		if(result.next())
			dbMovie = buildMovie(result);

		result.close();
		statment.close();
		connection.close();
		return dbMovie;
    }

    @Override
	public ArrayList<Movie> getBestMovies() throws SQLException {

		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, imageid, rating from movie Order by rating DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		ResultSet result = statment.executeQuery();
		ArrayList<Movie> movies = new ArrayList<Movie>();

		while (result.next()) {

			movies.add(buildSimplifiedMovie(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (movies.size() > 0) {
			return movies;
		} else {
			throw new RuntimeException("No movies found in this genre");
		}

	}

	@Override
	public ArrayList<Movie> getLatestMovies() throws SQLException {

		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, imageid, rating from movie Order by postdate DESC, imageid DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		ResultSet result = statment.executeQuery();
		ArrayList<Movie> movies = new ArrayList<Movie>();

		while (result.next()) {

			movies.add(buildSimplifiedMovie(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (movies.size() > 0) {
			return movies;
		} else {
			throw new RuntimeException("No movies found in this genre");
		}

	}

	@Override
	public int insertMovie(Movie movie) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "insert into movie(title, length, description, link, users) values(?,?,?,?,?) returning imageid";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, movie.getTitle());
		statment.setFloat(2, movie.getLength());
		statment.setString(3, movie.getDescription());
		statment.setString(4, movie.getLink());
		statment.setString(5, movie.getUser());
		ResultSet result = statment.executeQuery();
		result.next();
		int id = result.getInt(1);
		// statment.execute();
		statment.close();
		connection.close();

		insertMovieGenres(movie.getTitle(), movie.getGenres());

		return id;
	}

	@Override
	public void deleteMovie(String title) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "delete from movie where title = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public void updateMovie(Movie movie) throws SQLException {
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

	private Movie buildMovie(ResultSet result) throws SQLException {
		Movie movie = buildSimplifiedMovie(result);

		float length = result.getFloat("length");
		String description = result.getString("description");
		String link = result.getString("link");
		Date postDate = result.getDate("postdate");

		movie.setLength(length);
		movie.setDescription(description);
		movie.setLink(link);
		movie.setPostDate(postDate);

		movie.setGenres(getGenres(movie.getTitle()));

		return movie;
	}

	private Movie buildSimplifiedMovie(ResultSet result) throws SQLException {
		String title = result.getString("title");
		String user = result.getString("users");
		float rating = result.getFloat("rating");
		int imageId = result.getInt("imageid");

		Movie movie = new Movie();
		movie.setTitle(title);
		movie.setUser(user);
		movie.setRating(rating);
		movie.setImageId(imageId);

		return movie;
	}

	@Override
	public ArrayList<String> getGenres(String title) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select genre from genre_movie where movie = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		ResultSet result = statment.executeQuery();

		ArrayList<String> genres = new ArrayList<String>();
		while (result.next()) {
			genres.add(result.getString("genre"));
		}

		result.close();
		statment.close();
		connection.close();

		return genres;
	}

	@Override
	public List<String> getAllGenres() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select distinct genre from genre_movie";

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
	public void upsertMovieReview(String ownerNickname, String title, String raterNickname, boolean rating)
			throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "INSERT INTO user_rates_movie_review(users, movie, userthatrates, rated) VALUES(?, ?, ?, ?) "
				+ "ON CONFLICT ON CONSTRAINT user_rates_movie_review_pkey DO UPDATE SET rated = EXCLUDED.rated";
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
	public ArrayList<MovieReview> getReviews(String title, Integer offset) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select users, movie, numberofstars, description, postdate, likeNumber, dislikeNumber " + "from movie_review reviewT, LATERAL " +
				"(select COUNT(*) as likeNumber FROM user_rates_movie_review WHERE users = reviewT.users and movie = reviewT.movie and rated = true) likeT, LATERAL " +
				"(select COUNT(*) as dislikeNumber FROM user_rates_movie_review WHERE users = reviewT.users and movie = reviewT.movie and rated = false) dislikeT "
				+ "where movie = ? ORDER BY postdate DESC, likeNumber DESC, dislikeNumber ASC, numberofstars DESC, reviewT.users DESC " +
				"limit 15 offset ?";
		
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		statment.setInt(2, offset);
		ResultSet result = statment.executeQuery();

		ArrayList<MovieReview> reviews = new ArrayList<MovieReview>();
		while (result.next()) {
			reviews.add(buildReview(result, false));
		}

		result.close();
		statment.close();
		connection.close();

		return reviews;
	}

	@Override
	public MovieReview getUserReview(String title, String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select users, movie, numberofstars, description, postdate, likeNumber, dislikeNumber " + "from movie_review reviewT, LATERAL " +
				"(select COUNT(*) as likeNumber FROM user_rates_movie_review WHERE users = reviewT.users and movie = reviewT.movie and rated = true) likeT, LATERAL " +
				"(select COUNT(*) as dislikeNumber FROM user_rates_movie_review WHERE users = reviewT.users and movie = reviewT.movie and rated = false) dislikeT "
				+ "where movie = ? and users = ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, title);
		statment.setString(2, nickname);
		ResultSet result = statment.executeQuery();

		MovieReview review = null;
		if (result.next()) {
			review = buildReview(result, false);
		}

		result.close();
		statment.close();
		connection.close();
		return review;
	}


	@Override
	public ArrayList<MovieReview> getReviewsByUserRater(String title, String nickname, Integer offset) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT reviewT.users, reviewT.movie, numberofstars, description, reviewT.postdate, rat.rated, likeNumber, dislikeNumber "
				+ "FROM movie_review reviewT LEFT JOIN (SELECT users, movie, rated FROM user_rates_movie_review WHERE userthatrates = ?) as rat "
				+ "ON reviewT.users = rat.users and reviewT.movie = rat.movie, LATERAL " +
				"(select COUNT(*) as likeNumber FROM user_rates_movie_review WHERE users = reviewT.users and movie = reviewT.movie and rated = true) likeT, LATERAL " +
				"(select COUNT(*) as dislikeNumber FROM user_rates_movie_review WHERE users = reviewT.users and movie = reviewT.movie and rated = false) dislikeT "
				+ "where reviewT.movie = ? ORDER BY reviewT.postdate DESC, likeNumber DESC, dislikeNumber ASC, numberofstars DESC, reviewT.users DESC " +
				"limit 15 offset ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);
		statment.setString(2, title);
		statment.setInt(3, offset);
		ResultSet result = statment.executeQuery();

		ArrayList<MovieReview> reviews = new ArrayList<MovieReview>();
		while (result.next()) {
			reviews.add(buildReview(result, true));
		}

		result.close();
		statment.close();
		connection.close();

		return reviews;
	}

	private MovieReview buildReview(ResultSet result, boolean withRateMode) throws SQLException {
		String user = result.getString("users");
		String movie = result.getString("movie");
		short numberOfStars = result.getShort("numberofstars");
		String description = result.getString("description");
		Date postDate = result.getDate("postdate");
		Integer likeNumber = result.getInt("likeNumber");
		Integer dislikeNumber = result.getInt("dislikeNumber");

		MovieReview review = new MovieReview();
		review.setUser(user);
		review.setMovie(movie);
		review.setNumberOfStars(numberOfStars);
		review.setDescription(description);
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
	public ArrayList<Movie> searchByKeyWords(String[] keyWords, int limit, int offset) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		int keySize = keyWords.length;

		String query = "with tokens as (select unnest(array[";

		for(int i = 0; i < keySize; i++) {
			query += "?";
			if(i < keySize - 1)
				query += ",";
		}

		query += "]) AS tok) " +
				"select title, users, imageid, rating, COUNT(*) AS numTok, SUM(occ) AS sumOcc from movie movieT, tokens tokenT, LATERAL (select count(*) - 1 as occ from regexp_split_to_table(movieT.title, tokenT.tok, 'i')) occT " +
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
		ArrayList<Movie> movies = new ArrayList<Movie>();

		while (result.next()) {
			movies.add(buildSimplifiedMovie(result));
		}

		result.close();
		statment.close();
		connection.close();

		return movies;
	}

	@Override
	public ArrayList<Movie> searchByUser(String user, Integer offset, Integer modality, Integer order) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, imageid, rating " + "from movie " +
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
		ArrayList<Movie> movies = new ArrayList<Movie>();

		while (result.next()) {
			movies.add(buildSimplifiedMovie(result));
		}

		result.close();
		statment.close();
		connection.close();

		return movies;
	}

	@Override
	public ArrayList<Movie> searchByUserWithKeyWords(String user, String[] keyWords, Integer offset, Integer modality, Integer order) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		int keySize = keyWords.length;

		String query = "with tokens as (select unnest(array[";

		for(int i = 0; i < keySize; i++) {
			query += "?";
			if(i < keySize - 1)
				query += ",";
		}

		query += "]) AS tok) " +
				"select title, users, imageid, rating, COUNT(*) AS numTok, SUM(occ) AS sumOcc from movie movieT, tokens tokenT, LATERAL (select count(*) - 1 as occ from regexp_split_to_table(movieT.title, tokenT.tok, 'i')) occT " +
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
		ArrayList<Movie> movies = new ArrayList<Movie>();

		while (result.next()) {
			movies.add(buildSimplifiedMovie(result));
		}

		result.close();
		statment.close();
		connection.close();

		return movies;
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
				"select count(distinct title) as num from movie movieT, tokens tokenT, LATERAL (select count(*) - 1 as occ from regexp_split_to_table(movieT.title, tokenT.tok, 'i')) occT " +
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
	public void addReview(MovieReview review) throws SQLException {
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
	public void deleteReview(String nickname, String title) throws SQLException {
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
	public void updateReview(MovieReview review) throws SQLException {
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
	public List<Movie> findAll() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		List<Movie> movies = new ArrayList<>();

		Movie movie = null;
		String query = "select title, users, imageid, rating from movie";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			movie = buildSimplifiedMovie(result);
			movies.add(movie);
		}

		result.close();
		statement.close();
		connection.close();

		return movies;
	}

	@Override
	public void insertMovieGenres(String movieTitle, List<String> genres) throws SQLException {
		for (String genre : genres) {
			Connection connection = this.dataSource.getConnection();

			String query = "insert into genre_movie(movie, genre) values (?,?)";
			PreparedStatement statment = connection.prepareStatement(query);
			statment.setString(1, movieTitle);
			statment.setString(2, genre);
			statment.execute();
			statment.close();
			connection.close();
		}
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
	public ArrayList<Movie> getRandomMoviesByConditions(int limit, boolean mostRated) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "select title, users, rating, imageId " + "from movie ";

		if (mostRated) {
			query += "where rating = (select max(rating) from movie) ";
		}

		query += "order by random() limit ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, limit);

		ResultSet result = statement.executeQuery();
		ArrayList<Movie> movies = new ArrayList<>();
		while (result.next()) {
			movies.add(buildSimplifiedMovie(result));
		}

		try {
			return movies;
		} finally {
			connection.close();
			result.close();
			statement.close();
		}
	}

	@Override
	public ArrayList<Movie> getBestMoviesByGenre(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select distinct title, users, imageid, rating from movie inner join genre_movie on movie.title = genre_movie.movie where genre_movie.genre = ? Order by rating DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		ResultSet result = statment.executeQuery();
		ArrayList<Movie> movies = new ArrayList<Movie>();

		while (result.next()) {
			movies.add(buildSimplifiedMovie(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (movies.size() > 0) {
			return movies;
		} else {
			throw new RuntimeException("No movies found in this genre");
		}
	}

	@Override
	public ArrayList<Movie> getLatestMoviesByGenre(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select title, users, imageid, rating from movie inner join genre_movie on movie.title = genre_movie.movie where genre_movie.genre = ? Order by postdate DESC, imageid DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		ResultSet result = statment.executeQuery();
		ArrayList<Movie> movies = new ArrayList<Movie>();

		while (result.next()) {
			movies.add(buildSimplifiedMovie(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (movies.size() > 0) {
			return movies;
		} else {
			throw new RuntimeException("No movies found in this genre");
		}
	}

	@Override
	public Integer getMoviesNumberByGenre(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "SELECT COUNT(DISTINCT movie) as count from genre_movie where genre = ?";
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
			throw new RuntimeException("No movies found in this genre");
		}
	}

	@Override
	public ArrayList<Movie> getRandomMovies(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "select title, users, imageid, rating from movie inner join genre_movie on movie.title = genre_movie.movie where genre_movie.genre = ? Order by random() limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		ResultSet result = statment.executeQuery();
		ArrayList<Movie> movies = new ArrayList<Movie>();

		while (result.next()) {
			movies.add(buildSimplifiedMovie(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (movies.size() > 0) {
			return movies;
		} else {
			throw new RuntimeException("No movies found in this genre");
		}
	}
}
