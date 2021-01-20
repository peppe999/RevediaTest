package it.ingsw.revedia.jdbcModels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource
{
	final private String dataBaseURL;
	final String admin;
	final String password;

	public DataSource(String dataBaseURL, String admin, String password)
	{
		super();
		this.dataBaseURL = dataBaseURL;
		this.admin = admin;
		this.password = password;
	}

	public Connection getConnection() throws PersistenceException
	{
		Connection connection = null;

		try
		{

			connection = DriverManager.getConnection(dataBaseURL, admin, password);

		} catch (SQLException e)
		{
			throw new PersistenceException(e.getMessage());
		}

		return connection;
	}
}
