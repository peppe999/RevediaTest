package it.ingsw.revedia.daoInterfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import it.ingsw.revedia.jdbcModels.TupleNotFoundException;
import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.Permissions;

public interface UserDao 
{
	public User getUser(String nickname) throws SQLException;
	public User getUserByNicknameOrMail(String nickname, String mail) throws SQLException, TupleNotFoundException;
	public void updateUser(User user) throws SQLException;
	public void insertUser(User user, String password) throws SQLException;
	public void deleteUser(String nickname) throws SQLException;
	public boolean changePassword(String oldPassword, String newPassword, String nickname, String mail) throws SQLException;
	public void changePermissions(Permissions permissions, String nickname) throws SQLException;
	public boolean validateLogin(String password, String nickname) throws SQLException;
	public boolean validateLoginByNicknameOrMail(String nickname, String mail, String password) throws SQLException, TupleNotFoundException;
	public Integer getNextGoogleIdValue() throws SQLException;

	public Float getAvgQuality(String nickname) throws SQLException;
	public Float getAvgRating(String nickname) throws SQLException;
	public Integer getNumRatedReviews(String nickname) throws SQLException;
	public String getBestReview(String nickname) throws SQLException;
	public String getFavouriteCat(String nickname) throws SQLException;
	public Map<String, Object> getNumReviews(String nickname) throws SQLException;
	public Map<String, String> getFavouriteGenreForCat(String nickname) throws SQLException;
	public Map<String, List<Object>> getContributeForDay(String nickname) throws SQLException;

	public Integer getNumLoadedAlbums(String nickname) throws SQLException;
	public Integer getNumLoadedSongs(String nickname) throws SQLException;
	public Integer getNumLoadedBooks(String nickname) throws SQLException;
	public Integer getNumLoadedMovies(String nickname) throws SQLException;
}