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
import it.ingsw.revedia.model.AlbumReview;
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

		String query = "select album.albumid, song.name as songname, album.name as albumname, song.users, song.rating"
				+ " from song" + " inner join album" + " on song.album = album.albumid" + " where song.name = ?";

		ArrayList<Song> songs = new ArrayList<Song>();

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, name);
		ResultSet result = statment.executeQuery();
		while (result.next()) {
			songs.add(buildSimplifiedSong(result));
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
	public ArrayList<SongReview> getReviews(String name, int albumId, Integer offset) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select users, song, album, numberofstars, description, postdate, likeNumber, dislikeNumber " + "from song_review reviewT, LATERAL " +
				"(select COUNT(*) as likeNumber FROM user_rates_song_review WHERE users = reviewT.users and song = reviewT.song and album = reviewT.album and rated = true) likeT, LATERAL " +
				"(select COUNT(*) as dislikeNumber FROM user_rates_song_review WHERE users = reviewT.users and song = reviewT.song and album = reviewT.album and rated = false) dislikeT "
				+ "where song = ? and album = ? ORDER BY postdate DESC, likeNumber DESC, dislikeNumber ASC, numberofstars DESC, reviewT.users DESC " +
				"limit 15 offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, name);
		statment.setInt(2, albumId);
		statment.setInt(3, offset);

		ResultSet result = statment.executeQuery();
		ArrayList<SongReview> reviews = new ArrayList<SongReview>();
		while (result.next()) {
			reviews.add(buildReview(result, false));
		}

		result.close();
		statment.close();
		connection.close();

		return reviews;
	}

	@Override
	public SongReview getUserReview(String name, int albumId, String nickname) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select users, song, album, numberofstars, description, postdate, likeNumber, dislikeNumber " + "from song_review reviewT, LATERAL " +
				"(select COUNT(*) as likeNumber FROM user_rates_song_review WHERE users = reviewT.users and song = reviewT.song and album = reviewT.album and rated = true) likeT, LATERAL " +
				"(select COUNT(*) as dislikeNumber FROM user_rates_song_review WHERE users = reviewT.users and song = reviewT.song and album = reviewT.album and rated = false) dislikeT "
				+ "where song = ? and album = ? and users = ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, name);
		statment.setInt(2, albumId);
		statment.setString(3, nickname);
		ResultSet result = statment.executeQuery();

		SongReview review = null;
		if (result.next()) {
			review = buildReview(result, false);
		}

		result.close();
		statment.close();
		connection.close();
		return review;
	}

	@Override
	public ArrayList<SongReview> getReviewsByUserRater(String name, int albumId, String nickname, Integer offset) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "SELECT reviewT.users, reviewT.song, reviewT.album, numberofstars, description, reviewT.postdate, rat.rated, likeNumber, dislikeNumber "
				+ "FROM song_review reviewT LEFT JOIN (SELECT users, song, rated FROM user_rates_song_review WHERE userthatrates = ?) as rat "
				+ "ON reviewT.users = rat.users and reviewT.song = rat.song, LATERAL " +
				"(select COUNT(*) as likeNumber FROM user_rates_song_review WHERE users = reviewT.users and song = reviewT.song and album = reviewT.album and rated = true) likeT, LATERAL " +
				"(select COUNT(*) as dislikeNumber FROM user_rates_song_review WHERE users = reviewT.users and song = reviewT.song and album = reviewT.album and rated = false) dislikeT "
				+ "where reviewT.song = ? and reviewT.album = ? ORDER BY reviewT.postdate DESC, likeNumber DESC, dislikeNumber ASC, numberofstars DESC, reviewT.users DESC " +
				"limit 15 offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, nickname);
		statment.setString(2, name);
		statment.setInt(3, albumId);
		statment.setInt(4, offset);
		ResultSet result = statment.executeQuery();

		ArrayList<SongReview> reviews = new ArrayList<SongReview>();
		while (result.next()) {
			reviews.add(buildReview(result, true));
		}

		result.close();
		statment.close();
		connection.close();

		return reviews;
	}

	private SongReview buildReview(ResultSet result, boolean withRateMode) throws SQLException {

		String user = result.getString("users");
		String songTitle = result.getString("song");
		int album = result.getInt("album");
		short numberOfStars = result.getShort("numberofstars");
		String description = result.getString("description");
		Date postDate = result.getDate("postdate");
		Integer likeNumber = result.getInt("likeNumber");
		Integer dislikeNumber = result.getInt("dislikeNumber");

		SongReview review = new SongReview();
		review.setUser(user);
		review.setAlbumId(album);
		review.setSongName(songTitle);
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
	public ArrayList<Song> searchByKeyWords(String[] keyWords, int limit, int offset) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		int keySize = keyWords.length;

		String query = "with tokens as (select unnest(array[";

		for(int i = 0; i < keySize; i++) {
			query += "?";
			if(i < keySize - 1)
				query += ",";
		}

		query += "]) AS tok) " +
				"select albumT.albumid, songT.name as songname, albumT.name as albumname, songT.users, songT.rating, COUNT(*) AS numTok, SUM(occ) AS sumOcc from song songT inner join album albumT on songT.album = albumT.albumid, tokens tokenT, LATERAL (select count(*) - 1 as occ from regexp_split_to_table(songT.name, tokenT.tok, 'i')) occT " +
				"where occ != 0 " +
				"GROUP BY songT.name, albumT.albumid, albumT.name, songT.users, songT.rating " +
				"ORDER BY numTok DESC, sumOcc DESC, rating DESC, albumT.albumid DESC, songT.name DESC " +
				"LIMIT ? OFFSET ?";

		PreparedStatement statment = connection.prepareStatement(query);
		for(int i = 1; i <= keySize; i++) {
			statment.setString(i, keyWords[i - 1]);
		}
		statment.setInt(keySize + 1, limit);
		statment.setInt(keySize + 2, offset);

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
	public ArrayList<Song> findByGenre(String genre, Integer offset, Integer modality, Integer order) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select album.albumid, song.name as songname, album.name as albumname, song.users, song.rating "
				+ "from song inner join album on song.album = album.albumid " + "where exists "
				+ "(select * from musical_genre_album " + "where album = albumid and musical_genre = ?)";

		String orderString = (order == 0) ? "ASC" : "DESC";

		if(modality == 0)
			query += " order by songname " + orderString;
		else
			query += " order by song.postdate " + orderString + ", song.album " + orderString;

		query += " limit 20 offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
		statment.setInt(2, offset);
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
	public void upsertSongReview(String ownerNickname, String name, int albumId, String raterNickname, boolean rating)
			throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "INSERT INTO user_rates_song_review(users, song, album, userthatrates, rated) VALUES(?, ?, ?, ?, ?) "
				+ "ON CONFLICT ON CONSTRAINT user_rates_song_review_pkey DO UPDATE SET rated = EXCLUDED.rated";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, ownerNickname);
		statment.setString(2, name);
		statment.setInt(3, albumId);
		statment.setString(4, raterNickname);
		statment.setBoolean(5, rating);
		statment.execute();
		statment.close();
		connection.close();
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
	public ArrayList<Song> getBestSongs() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select album.albumid, song.name as songname, album.name as albumname, song.users, song.rating from song inner join album ON album.albumid = song.album Order by song.rating DESC limit 4";
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

		String query = "select album.albumid, song.name as songname, album.name as albumname, song.users, song.rating from song inner join album ON album.albumid = song.album Order by song.postdate, album.postdate, album.albumid DESC limit 4";
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
	public ArrayList<Song> getRandomSongsByConditions(int limit, boolean mostRated) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "select album.albumid, song.name as songname, album.name as albumname, song.users, song.rating"
				+ " from song" + " inner join album" + " on song.album = album.albumid ";

		if (mostRated) {
			query += "where song.rating = (select max(song.rating) from song) ";
		}

		query += "order by random() limit ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, limit);

		ResultSet result = statement.executeQuery();
		ArrayList<Song> songs = new ArrayList<>();
		while (result.next()) {
			songs.add(buildSimplifiedSong(result));
		}

		try {
			return songs;
		} finally {
			connection.close();
			result.close();
			statement.close();
		}
	}

	@Override
	public ArrayList<Song> getBestSongsByGenre(String genre) throws SQLException {

		Connection connection = this.dataSource.getConnection();
		String query = "select album.albumid, song.name as songname, album.name as albumname, song.users, song.rating from song inner join album on song.album = album.albumid where exists (select * from musical_genre_album where album = albumid and musical_genre = ?) Order by song.rating DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
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
	public ArrayList<Song> getLatestSongsByGenre(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "select album.albumid, song.name as songname, album.name as albumname, song.users, song.rating from song inner join album on song.album = album.albumid where exists (select * from musical_genre_album where album = albumid and musical_genre = ?) Order by song.postdate, album.postdate, album.albumid DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
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
	public ArrayList<Song> getRandomSongs(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select album.albumid, song.name as songname, song.album as albumname, song.users, song.rating from song inner join album on song.album = album.albumid where exists (select * from musical_genre_album where album = albumid and musical_genre = ?) Order by random() DESC limit 4";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, genre);
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
	public Integer getSongsNumberByGenre(String genre) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		String query = "SELECT SUM(numberofsongs) as count from musical_genre_album INNER JOIN album ON album.albumid = musical_genre_album.album where musical_genre = ?";

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
