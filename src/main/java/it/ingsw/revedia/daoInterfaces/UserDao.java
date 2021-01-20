package it.ingsw.revedia.daoInterfaces;

import java.sql.SQLException;

import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.Permissions;

public interface UserDao 
{
	public User getUser(String nickname) throws SQLException;
	public void updateUser(User user) throws SQLException;
	public void insertUser(User user, String password) throws SQLException;
	public void deleteUser(String nickname) throws SQLException;
	public boolean changePassword(String oldPassword, String newPassword, String nickname, String mail) throws SQLException;
	public void changePermissions(Permissions permissions, String nickname) throws SQLException;
	public boolean validateLogin(String password, String nickname) throws SQLException;
}
