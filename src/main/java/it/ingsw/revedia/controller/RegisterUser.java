package it.ingsw.revedia.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.ingsw.revedia.jdbcModels.DAOFactory;
import it.ingsw.revedia.jdbcModels.UserJDBC;
import it.ingsw.revedia.model.User;

@Controller
public class RegisterUser
{

	@RequestMapping(value = "/Register", method = RequestMethod.GET)
	public String RegisterDispatcher(){
		return "register";
	}

	@RequestMapping(value = "/Register", method = RequestMethod.POST)
	public void UserRegister(@RequestParam("/nick") String nick, @RequestParam("/nome") String name,
			@RequestParam("/cognome") String sur, @RequestParam("/mail") String mail,
			@RequestParam("/tag pwd") String pwd) throws SQLException
	{

		User user = new User(nick, name, sur, mail);

		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
		UserJDBC userJDBC = factory.getUserJDBC();

		userJDBC.insertUser(user, pwd);
	}

}
