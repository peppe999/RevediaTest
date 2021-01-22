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

public class AlbumJDBC implements AlbumDao
{
	private DataSource dataSource;

	public AlbumJDBC()
	{
		super();
	}

	public AlbumJDBC(DataSource dataSource)
	{
		super();
		this.dataSource = dataSource;
	}

	@Override
	public Album getAlbum(Integer id) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select album.albumid, name, numberofsongs, releasedate, label, users, artist "
					 + "from album "
					 + "where album.albumid = ?";

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

	private static Album buildAlbum(ResultSet result) throws SQLException
	{
		int albumId = result.getInt("albumid");
		String name = result.getString("name");
		short numberOfSongs = result.getShort("numberofsongs");
		Date releaseDate = result.getDate("releasedate");
		String label = result.getString("label");
		String user = result.getString("users");
		String artist = result.getString("artist");

		Album album = new Album();
		album.setId(albumId);
		album.setName(name);
		album.setNumberOfSongs(numberOfSongs);
		album.setReleaseDate(releaseDate);
		album.setLabel(label);
		album.setUser(user);
		album.setArtist(artist);

		return album;
	}

	@Override
	public ArrayList<Album> getAlbums(String name) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();
		String query = "select album.albumid, name, numberofsongs, releasedate, label, users, artist "
				+ "from album "
				+ "where name = ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, name);
		ResultSet result = statment.executeQuery();

		ArrayList<Album> albums = new ArrayList<Album>();
		while (result.next())
		{
			albums.add(buildAlbum(result));
		}

		result.close();
		statment.close();
		connection.close();
		return albums;
	}

	@Override
	public int insertAlbum(Album album, String userNickname) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "insert into album(name,releaseDate, label, users, artist) values (?,?,?,?,?) returning albumid";
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, album.getName());
		statment.setDate(2, album.getReleaseDate());
		statment.setString(3, album.getLabel());
		statment.setString(4, userNickname);
		statment.setString(5, album.getArtist());
		ResultSet result = statment.executeQuery();
		result.next();
		int id = result.getInt(1);
		//statment.execute();
		statment.close();
		connection.close();

		insertAlbumGenres(id, album.getGenre());

		return id;
	}

	@Override
	public List<Album> findAll() throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		List<Album> albums = new ArrayList<>();

		Album album = null;

		String query = "select albumid from album";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		while (result.next())
		{
			album = getAlbum(result.getInt("albumid"));
			albums.add(album);
		}

		result.close();
		statement.close();
		connection.close();

		return albums;
	}

	@Override
	public ArrayList<AlbumReview> getReviews(Album album) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select users, album, numberofStars, description, postdate " 
					 + "from album_review "
					 + "where album = ?";
		
		PreparedStatement statment = connection.prepareStatement(query);
		statment.setInt(1, album.getId());
		ResultSet result = statment.executeQuery();

		ArrayList<AlbumReview> reviews = new ArrayList<AlbumReview>();
		while (result.next())
		{
			reviews.add(buildReview(result));
		}

		result.close();
		statment.close();
		connection.close();
		return reviews;
	}

	private AlbumReview buildReview(ResultSet result) throws SQLException
	{
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

		return review;
	}

	@Override
	public ArrayList<Album> searchByKeyWords(String keyWords, int limit, int offset) throws SQLException
	{

		Connection connection = this.dataSource.getConnection();

		String query = "select albumid, name, numberofsongs, releasedate, label, users, rating, postdate, artist "
					 + "from album "
					 + "where name similar to ? " 
					 + "limit ? offset ?";

		PreparedStatement statment = connection.prepareStatement(query);
		statment.setString(1, keyWords);
		statment.setInt(2, limit);
		statment.setInt(3, offset);

		ResultSet result = statment.executeQuery();
		ArrayList<Album> albums = new ArrayList<Album>();

		while (result.next())
		{
			albums.add(buildAlbum(result));
		}

		result.close();
		statment.close();
		connection.close();

		return albums;
	}

	@Override
	public void insertAlbumGenres(int albumId, List<String> genres) throws SQLException{
		for(String genre : genres) {
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
	public List<String> getGenres() throws SQLException {
		Connection connection = this.dataSource.getConnection();

		String query = "select name from musical_genre";

		PreparedStatement statment = connection.prepareStatement(query);

		ResultSet result = statment.executeQuery();
		List<String> genres = new ArrayList<>();

		while (result.next())
		{
			genres.add(result.getString("name"));
		}

		result.close();
		statment.close();
		connection.close();

		return genres;
	}

	@Override
	public void addReview(AlbumReview review) throws SQLException
	{
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
	public void deleteReview(String nickname, int albumId) throws SQLException
	{
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
	public void updateReview(AlbumReview review) throws SQLException
	{
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
	public void updateAlbum(Album album) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "update album set name  = ?, releasedate = ?, label = ? , artist = ? "
					 + "where albumid = ?";
		
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
	public void deleteAlbum(int id) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "delete from album where albumid = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		statement.execute();
		statement.close();
		connection.close();
	}

	@Override
	public ArrayList<Song> getSongs(int id) throws SQLException
	{
		Connection connection = this.dataSource.getConnection();

		String query = "select album.albumid, song.name as songname, album.name as albumname,"
				+ " song.length, song.rating" 
				+ " from song " 
				+ " inner join album on song.album = album.albumid"
				+ " where albumid = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		ArrayList<Song> songs = new ArrayList<Song>();

		while (result.next())
		{
			String name = result.getString("songnname");
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
}
