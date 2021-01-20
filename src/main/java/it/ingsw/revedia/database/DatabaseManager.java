package it.ingsw.revedia.database;

import it.ingsw.revedia.jdbcModels.DAOFactory;

public class DatabaseManager
{
	private static DatabaseManager instance = null;
	private DAOFactory daoFactory;

	public static DatabaseManager getIstance()
	{
		if (instance == null)
		{
			instance = new DatabaseManager();
		}
		return instance;
	}

	private DatabaseManager()
	{
		daoFactory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
	}

	public DAOFactory getDaoFactory()
	{
		return daoFactory;
	}
}
