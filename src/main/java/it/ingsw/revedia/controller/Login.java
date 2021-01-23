package it.ingsw.revedia.controller;

import java.sql.SQLException;

import it.ingsw.revedia.utilities.EmailManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.PasswordManager;

@Controller
public class Login
{	
	@PostMapping("/loginUser")
	public ModelAndView loginUser(@RequestParam("nickname") String nickname, @RequestParam("password") String password)
	{
		ModelAndView model = new ModelAndView();
		try
		{
			String MD5Password = PasswordManager.getMD5(password);
			if(DatabaseManager.getIstance().getDaoFactory().getUserJDBC().validateLogin(MD5Password, nickname))
			{
				model.setViewName("second.jsp");
				model.addObject("nickname", nickname);
			}
			else
			{
				model.setViewName("register.jsp");
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return model;
	}
}
