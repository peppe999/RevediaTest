package it.ingsw.revedia.jdbcModels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		if(result.next())
		{
			user = buildUser(result);
			statment.close();
			result.close();
			connection.close();
			return user;
		}
		else
			throw new TupleNotFoundException("No result set avaible with this condition");
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
}
