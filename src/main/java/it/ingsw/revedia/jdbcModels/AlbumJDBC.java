package it.ingsw.revedia.jdbcModels;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.AlbumReview;
import it.ingsw.revedia.model.Song;

public class AlbumJDBC implements AlbumDao {
	private DataSource dataSource;

	public AlbumJDBC() {
		super();
	}

	public AlbumJDBC(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public Album findByPrimaryKey(Integer id) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select * " + "from album " + "where albumid = ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setInt(1, id);
		ResultSet result = statment.executeQuery();
		result.next();

		Album album = buildAlbum(result);

		result.close();
		statment.close();
		connection.close();
		return album;
	}

	private Album buildAlbum(ResultSet result) throws SQLException {
		Album album = buildSimplifiedAlbum(result);

		short numberOfSongs = result.getShort("numberofsongs");
		Date releaseDate = result.getDate("releasedate");
		String label = result.getString("label");
		String artist = result.getString("artist");
		Date postDate = result.getDate("postdate");

		album.setNumberOfSongs(numberOfSongs);
		album.setReleaseDate(releaseDate);
		album.setLabel(label);
		album.setArtist(artist);
		album.setPostDate(postDate);

		album.setGenre(getGenres(album.getId()));

		return album;
	}

	private Album buildSimplifiedAlbum(ResultSet result) throws SQLException {
		int albumId = result.getInt("albumid");
		String name = result.getString("name");
		String user = result.getString("users");
		float rating = result.getFloat("rating");

		Album album = new Album();
		album.setId(albumId);
		album.setName(name);
		album.setUser(user);
		album.setRating(rating);

		return album;
	}

	@Override
	public ArrayList<Album> findByTitle(String name) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "select albumid, name, users, rating " + "from album " + "where name = ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, name);
		ResultSet result = statment.executeQuery();

		ArrayList<Album> albums = new ArrayList<Album>();
		while (result.next()) {
			albums.add(buildSimplifiedAlbum(result));
		}

		result.close();
		statment.close();
		connection.close();
		return albums;
	}

	@Override
	public int insertAlbum(Album album) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "insert into album(name,releaseDate, label, users, artist) values (?,?,?,?,?) returning albumid";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, album.getName());
		statment.setDate(2, album.getReleaseDate());
		statment.setString(3, album.getLabel());
		statment.setString(4, album.getUser());
		statment.setString(5, album.getArtist());
		ResultSet result = statment.executeQuery();
		result.next();
		int id = result.getInt(1);
		// statment.execute();
		statment.close();
		connection.close();

		insertAlbumGenres(id, album.getGenre());

		return id;
	}

	@Override
	public List<Album> findAll() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		List<Album> albums = new ArrayList<>();

		Album album = null;

		String query = "select albumid, name, users, rating from album";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			album = buildSimplifiedAlbum(result);
			albums.add(album);
		}

		result.close();
		statement.close();
		connection.close();

		return albums;
	}

	@Override
	public ArrayList<AlbumReview> getReviews(int albumId) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select users, album, numberofStars, description, postdate " + "from album_review "
				+ "where album = ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setInt(1, albumId);
		ResultSet result = statment.executeQuery();

		ArrayList<AlbumReview> reviews = new ArrayList<AlbumReview>();
		while (result.next()) {
			reviews.add(buildReview(result, false));
		}

		result.close();
		statment.close();
		connection.close();
		return reviews;
	}

	@Override
	public ArrayList<AlbumReview> getReviewsByUserRater(int albumId, String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT album_review.users, album_review.album, numberofstars, description, album_review.postdate, rat.rated "
				+ "FROM album_review LEFT JOIN (SELECT users, album, rated FROM user_rates_album_review WHERE userthatrates = ?) as rat "
				+ "ON album_review.users = rat.users and album_review.album = rat.album "
				+ "WHERE album_review.album = ?;";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);
		statment.setInt(2, albumId);
		ResultSet result = statment.executeQuery();

		ArrayList<AlbumReview> reviews = new ArrayList<AlbumReview>();
		while (result.next()) {
			reviews.add(buildReview(result, true));
		}

		result.close();
		statment.close();
		connection.close();

		return reviews;
	}

	private AlbumReview buildReview(ResultSet result, boolean withRateMode) throws SQLException {
		String user = result.getString("users");
		int albumId = result.getInt("album");
		short numberOfStars = result.getShort("numberOfStars");
		String description = result.getString("description");
		Date postDate = result.getDate("postdate");

		AlbumReview review = new AlbumReview();
		review.setUser(user);
		review.setAlbumId(albumId);
		review.setNumberOfStars(numberOfStars);
		review.setDescription(description);
		review.setPostDate(postDate);

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
	public ArrayList<Album> searchByKeyWords(String keyWords, int limit, int offset) throws SQLException {

		Connection connection = this.dataSource.getConnection();

		String query = "select albumid, name, users, rating " + "from album albumT, lateral (" +
				"select count(*) - 1 as occ from regexp_split_to_table(albumT.name, ?, 'i')) occT " +
				"where name ~* ? "
				+ "order by occ desc, rating desc, albumid desc limit ? offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, keyWords);
		statment.setString(2, keyWords);
		statment.setInt(3, limit);
		statment.setInt(4, offset);

		ResultSet result = statment.executeQuery();
		ArrayList<Album> albums = new ArrayList<Album>();

		while (result.next()) {
			albums.add(buildSimplifiedAlbum(result));
		}

		result.close();
		statment.close();
		connection.close();

		return albums;
	}

	@Override
	public ArrayList<Album> findByGenre(String genre, Integer offset, Integer modality, Integer order, boolean excludeSingles) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select albumid, name, users, rating " + "from album "
				+ "inner join musical_genre_album " + "on album.albumid = musical_genre_album.album "
				+ "where musical_genre_album.musical_genre = ?";

		if(excludeSingles) {
			query += " and numberofsongs > 1";
		}

		String orderString = (order == 0) ? "ASC" : "DESC";

		if(modality == 0)
			query += " order by name " + orderString;
		else
			query += " order by postdate " + orderString + ", albumid " + orderString;

		query += " limit 20 offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		statment.setInt(2, offset);
		ResultSet result = statment.executeQuery();
		ArrayList<Album> albums = new ArrayList<>();

		while (result.next()) {
			albums.add(buildSimplifiedAlbum(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (albums.size() > 0) {
			return albums;
		} else {
			throw new RuntimeException("No albums found in this genre");
		}
	}

	@Override
	public void insertAlbumGenres(int albumId, List<String> genres) throws SQLException {
		for (String genre : genres) {
			Connection connection = this.dataSource.getConnection();

			String query = "insert into musical_genre_album(album, musical_genre) values (?,?)";
			PreparedStatement statment = connection.prepareStatement(query);
			statment.setInt(1, albumId);
			statment.setString(2, genre);
			statment.execute();
			statment.close();
			connection.close();
		}
	}

	@Override
	public void addGenre(String g) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "insert into musical_genre(name) values (?)";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, g);
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public ArrayList<String> getGenres(int albumId) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select musical_genre from musical_genre_album where album = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setInt(1, albumId);
		ResultSet result = statment.executeQuery();

		ArrayList<String> genres = new ArrayList<String>();
		while (result.next()) {
			genres.add(result.getString("musical_genre"));
		}

		result.close();
		statment.close();
		connection.close();

		return genres;
	}

	@Override
	public List<String> getAllGenres() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select name from musical_genre";

		PreparedStatement statment = connection.prepareStatement(query);

		ResultSet result = statment.executeQuery();
		List<String> genres = new ArrayList<>();

		while (result.next()) {
			genres.add(result.getString("name"));
		}

		result.close();
		statment.close();
		connection.close();

		return genres;
	}

	@Override
	public void upsertAlbumReview(String ownerNickname, int albumId, String raterNickname, boolean rating)
			throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "INSERT INTO user_rates_album_review(users, album, userthatrates, rated) VALUES(?, ?, ?, ?) "
				+ "ON CONFLICT ON CONSTRAINT user_rates_album_review_pkey DO UPDATE SET rated = EXCLUDED.rated";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, ownerNickname);
		statment.setInt(2, albumId);
		statment.setString(3, raterNickname);
		statment.setBoolean(4, rating);
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public void addReview(AlbumReview review) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "insert into album_review(users, album, numberofstars, description) values(?,?,?,?)";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, review.getUser());
		statment.setInt(2, review.getAlbumId());
		statment.setShort(3, review.getNumberOfStars());
		statment.setString(4, review.getDescription());

		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public void deleteReview(String nickname, int albumId) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "delete from album_review where users = ? and album = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);
		statment.setInt(2, albumId);
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public void updateReview(AlbumReview review) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "update album_review set numberofStars = ?, description = ? " + "where users = ? and album = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setShort(1, review.getNumberOfStars());
		statement.setString(2, review.getDescription());
		statement.setString(3, review.getUser());
		statement.setInt(4, review.getAlbumId());
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

	@Override
	public void updateAlbum(Album album) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "update album set name  = ?, releasedate = ?, label = ? , artist = ? " + "where albumid = ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, album.getName());
		statement.setDate(2, album.getReleaseDate());
		statement.setString(3, album.getLabel());
		statement.setInt(4, album.getId());
		statement.setString(5, album.getArtist());
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

	@Override
	public void deleteAlbum(int id) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "delete from album where albumid = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		statement.execute();
		statement.close();
		connection.close();
	}

	@Override
	public ArrayList<Song> getSongs(int id) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select album.albumid, song.name as songname, album.name as albumname,"
				+ " song.length, song.rating" + " from song " + " inner join album on song.album = album.albumid"
				+ " where albumid = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		ArrayList<Song> songs = new ArrayList<Song>();

		while (result.next()) {
			String name = result.getString("songname");
			int albumId = result.getInt("albumid");
			float length = result.getFloat("length");
			float rating = result.getFloat("rating");
			String albumName = result.getString("albumname");

			Song song = new Song();
			song.setName(name);
			song.setLength(length);
			song.setRating(rating);
			song.setAlbumID(albumId);
			song.setAlbumName(albumName);

			songs.add(song);
		}

		result.close();
		statement.close();
		connection.close();
		return songs;
	}

	@Override
	public ArrayList<Album> getRandomAlbumsByConditions(int limit, boolean mostRated) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "select album.albumid, album.name, album.users, album.rating " + "from album ";

		if (mostRated) {
			query += "where rating = (select max(rating) from album) ";
		}

		query += "order by random() limit ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, limit);

		ResultSet result = statement.executeQuery();
		ArrayList<Album> albums = new ArrayList<>();
		while (result.next()) {
			albums.add(buildSimplifiedAlbum(result));
		}

		try {
			return albums;
		} finally {
			connection.close();
			statement.close();
			result.close();
		}
	}

	@Override
	public ArrayList<Album> getBestAlbums() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select albumid, name, users, rating from album Order by rating DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		ResultSet result = statment.executeQuery();
		ArrayList<Album> albums = new ArrayList<Album>();

		while (result.next()) {

			albums.add(buildSimplifiedAlbum(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (albums.size() > 0) {
			return albums;
		} else {
			throw new RuntimeException("No albums found in this genre");
		}
	}

	@Override
	public ArrayList<Album> getLatestAlbums() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select albumid, name, users, rating from album Order by postdate, albumid DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		ResultSet result = statment.executeQuery();
		ArrayList<Album> albums = new ArrayList<Album>();

		while (result.next()) {

			albums.add(buildSimplifiedAlbum(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (albums.size() > 0) {
			return albums;
		} else {
			throw new RuntimeException("No albums found in this genre");
		}
	}

	@Override
	public ArrayList<Album> getBestAlbumsByGenre(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "select distinct albumid, name, users, rating from album inner join musical_genre_album on album.albumid = musical_genre_album.album where musical_genre_album.musical_genre = ? Order by rating DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		ResultSet result = statment.executeQuery();
		ArrayList<Album> albums = new ArrayList<Album>();

		while (result.next()) {

			albums.add(buildSimplifiedAlbum(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (albums.size() > 0) {
			return albums;
		} else {
			throw new RuntimeException("No albums found in this genre");
		}
	}

	@Override
	public ArrayList<Album> getLatestAlbumsByGenre(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "select albumid, name, users, rating from album inner join musical_genre_album on album.albumid = musical_genre_album.album where musical_genre_album.musical_genre = ? Order by postdate, album.albumid DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		ResultSet result = statment.executeQuery();
		ArrayList<Album> albums = new ArrayList<Album>();

		while (result.next()) {

			albums.add(buildSimplifiedAlbum(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (albums.size() > 0) {
			return albums;
		} else {
			throw new RuntimeException("No albums found in this genre");
		}
	}

	@Override
	public Integer getAlbumsNumberByGenre(String genre, boolean excludeSingles) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "SELECT COUNT(albumid) as count from musical_genre_album INNER JOIN album ON musical_genre_album.album = album.albumid where musical_genre = ?";

		if(excludeSingles) {
			query += " and numberofsongs > 1";
		}

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
			throw new RuntimeException("No albums found in this genre");
		}
	}
}
