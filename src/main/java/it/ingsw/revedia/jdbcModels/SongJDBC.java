package it.ingsw.revedia.jdbcModels;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Song;
import it.ingsw.revedia.model.SongReview;

public class SongJDBC implements SongDao {
	private DataSource dataSource;

	public SongJDBC() {
		super();
	}

	public SongJDBC(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public ArrayList<Song> findByTitle(String name) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select album.albumid, song.name as songname, album.name as albumname,"
				+ " song.link, song.decription, song.users, song.length, song.rating, song.postdate" + " from song"
				+ " inner join album" + " on song.album = album.albumid" + " where song.name = ?";

		ArrayList<Song> songs = new ArrayList<Song>();

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, name);
		ResultSet result = statment.executeQuery();
		while (result.next()) {
			songs.add(buildSong(result));
		}

		statment.close();
		result.close();
		connection.close();
		return songs;
	}

	@Override
	public Song findByPrimaryKey(String name, int albumKey) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select album.albumid, song.name as songname, album.name as albumname,"
				+ " song.link, song.decription, song.users, song.length, song.rating, song.postdate" + " from song"
				+ " inner join album" + " on song.album = album.albumid" + " where song.name = ? and song.album = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, name);
		statment.setInt(2, albumKey);
		ResultSet result = statment.executeQuery();

		Song song = null;
		while (result.next()) {
			song = buildSong(result);
		}

		result.close();
		statment.close();
		connection.close();

		if (song != null) {
			return song;
		} else {
			throw new RuntimeException("No song in this album with this name");
		}
	}

	private Song buildSong(ResultSet result) throws SQLException {
		Song song = buildSimplifiedSong(result);

		String link = result.getString("link");
		String description = result.getString("decription");
		float length = result.getFloat("length");
		Date postDate = result.getDate("postdate");

		song.setLink(link);
		song.setDescription(description);
		song.setLength(length);
		song.setPostDate(postDate);

		song.setGenres(getGenres(song.getAlbumID()));

		return song;
	}

	private Song buildSimplifiedSong(ResultSet result) throws SQLException {
		String songName = result.getString("songname");
		String albumName = result.getString("albumname");
		int albumID = result.getInt("albumid");
		String user = result.getString("users");
		float rating = result.getFloat("rating");

		Song song = new Song();
		song.setName(songName);
		song.setAlbumID(albumID);
		song.setAlbumName(albumName);
		song.setUser(user);
		song.setRating(rating);

		return song;
	}

	@Override
	public void insertSong(Song song, String userNickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "insert into song(name, album, link, decription, users, length) values (?,?,?,?,?,?) ";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, song.getName());
		statment.setInt(2, song.getAlbumID());
		statment.setString(3, song.getLink());
		statment.setString(4, song.getDescription());
		statment.setString(5, userNickname);
		statment.setFloat(6, song.getLength());

		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public void updateSong(Song song) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "update song set link = ?, decription = ?, length = ? where name = ? and album = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, song.getLink());
		statment.setString(2, song.getDescription());
		statment.setFloat(3, song.getLength());
		statment.setString(4, song.getName());
		statment.setInt(5, song.getAlbumID());

		statment.executeUpdate();
		statment.close();
		connection.close();
	}

	@Override
	public void deleteSong(Song song) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "delete from song where name = ? and album = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, song.getName());
		statment.setInt(2, song.getAlbumID());
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public ArrayList<SongReview> getReviews(Song song) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select users, song, album, numberofstars, description, postdate " + "from song_review "
				+ "where song = ? and album = ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, song.getName());
		statment.setInt(2, song.getAlbumID());

		ResultSet result = statment.executeQuery();
		ArrayList<SongReview> reviews = new ArrayList<SongReview>();
		while (result.next()) {
			reviews.add(buildSongReview(result));
		}

		result.close();
		statment.close();
		connection.close();

		return reviews;
	}

	private SongReview buildSongReview(ResultSet result) throws SQLException {

		String user = result.getString("users");
		String songTitle = result.getString("song");
		int album = result.getInt("album");
		short numberOfStars = result.getShort("numberofstars");
		String description = result.getString("description");
		Date postDate = result.getDate("postdate");

		SongReview review = new SongReview();
		review.setUser(user);
		review.setAlbumId(album);
		review.setSongName(songTitle);
		review.setNumberOfStars(numberOfStars);
		review.setDescription(description);
		review.setPostDate(postDate);

		return review;
	}

	@Override
	public ArrayList<Song> searchByKeyWords(String keyWords, int limit, int offset) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select album.albumid, song.name as songname, album.name as albumname, song.users, song.rating"
				+ " from song" + " inner join album" + " on song.album = album.albumid"
				+ " where songname similar to ? or albumname similar to ?" + " limit ? offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, keyWords);
		statment.setString(2, keyWords);
		statment.setInt(3, limit);
		statment.setInt(4, offset);

		ResultSet result = statment.executeQuery();

		ArrayList<Song> songs = new ArrayList<Song>();
		while (result.next()) {
			songs.add(buildSimplifiedSong(result));
		}

		result.close();
		statment.close();
		connection.close();

		return songs;
	}

	@Override
	public ArrayList<Song> findByGenre(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select album.albumid, song.name as songname, album.name as albumname, song.users, song.rating "
				+ "from song inner join album on song.album = album.albumid " + "where exists "
				+ "(select * from musical_genre_album " + "where album = albumid and musical_genre = ?)";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		ResultSet result = statment.executeQuery();

		ArrayList<Song> songs = new ArrayList<>();
		while (result.next()) {
			songs.add(buildSimplifiedSong(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (songs.size() > 0) {
			return songs;
		} else {
			throw new RuntimeException("No songs found in this genre");
		}
	}

	@Override
	public ArrayList<String> getGenres(int albumId) throws SQLException {
		return DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().getGenres(albumId);
	}

	@Override
	public void addReview(SongReview review) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "insert into song_review(users,song,album,numberofstars,description) values(?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, review.getUser());
		statement.setString(2, review.getSongName());
		statement.setInt(3, review.getAlbumId());
		statement.setShort(4, review.getNumberOfStars());
		statement.setString(5, review.getDescription());
		statement.execute();
		statement.close();
		connection.close();
	}

	@Override
	public void deleteReview(String nickname, String song, int albumId) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "delete from song_review where users = ? and song = ? and album = ?";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);
		statment.setString(2, song);
		statment.setInt(3, albumId);
		statment.execute();
		statment.close();
		connection.close();
	}

	@Override
	public void updateReview(SongReview review) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "update song_review set numberofstars = ?, description = ? "
				+ "where users = ? and song = ? and album = ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setShort(1, review.getNumberOfStars());
		statment.setString(2, review.getDescription());
		statment.setString(3, review.getUser());
		statment.setString(4, review.getSongName());
		statment.setInt(5, review.getAlbumId());
		statment.executeUpdate();
		statment.close();
		connection.close();
	}

	@Override
	public List<Song> findAll() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		List<Song> songs = new ArrayList<>();

		Song song = null;

		String query = "select album.albumid, song.name as songname, album.name as albumname, song.users, song.rating"
				+ " from song" + " inner join album" + " on song.album = album.albumid";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			song = buildSimplifiedSong(result);
			songs.add(song);
		}

		result.close();
		connection.close();

		return songs;
	}

	@Override
	public ArrayList<Song> getHighRateSongs() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select * from song Order by rating DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		ResultSet result = statment.executeQuery();
		ArrayList<Song> songs = new ArrayList<Song>();

		while (result.next()) {

			songs.add(buildSimplifiedSong(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (songs.size() > 0) {
			return songs;
		} else {
			throw new RuntimeException("No songs found in this genre");
		}
	}

	@Override
	public ArrayList<Song> getLatestSongs() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select * from song Order by postdate DESC limit 3";
		PreparedStatement statment = connection.prepareStatement(query);
		ResultSet result = statment.executeQuery();
		ArrayList<Song> songs = new ArrayList<Song>();

		while (result.next()) {

			songs.add(buildSimplifiedSong(result));
		}

		result.close();
		statment.close();
		connection.close();

		if (songs.size() > 0) {
			return songs;
		} else {
			throw new RuntimeException("No songs found in this genre");
		}
	}
}
