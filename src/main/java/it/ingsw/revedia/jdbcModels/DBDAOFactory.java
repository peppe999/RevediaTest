package it.ingsw.revedia.jdbcModels;

public class DBDAOFactory extends DAOFactory
{
	private static DataSource dataSource;
	private final static String username = "revadmin";
	private final static String password = "cac5c1dc7a21171f.3573529a7096c94d";

	static
	{
		try
		{
			Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();

			dataSource = new DataSource("jdbc:postgresql://revediadb.c3igmyv3gigi.us-east-2.rds.amazonaws.com/revedia",username,password); // METTERE SERVER AWS

		}
		catch (Exception e)
		{
			System.err.println("PostgresDAOFactory.class: failed to load PostGress JDBC driver\n" + e);
			e.printStackTrace();
		}
	}

	public static void setDataSource(DataSource dataSource)
	{
		DBDAOFactory.dataSource = dataSource;
	}

	@Override
	public AlbumJDBC getAlbumJDBC()
	{
		return new AlbumJDBC(dataSource);
	}

	@Override
	public BookJDBC getBookJDBC()
	{
		return new BookJDBC(dataSource);
	}

	@Override
	public SongJDBC getSongJDBC()
	{
		return new SongJDBC(dataSource);
	}

	@Override
	public UserJDBC getUserJDBC()
	{
		return new UserJDBC(dataSource);
	}

	@Override
	public DataSource getDataSource()
	{
		return dataSource;
	}

	@Override
	public MovieJDBC getMovieJDBC()
	{
		return new MovieJDBC(dataSource);
	}

}
