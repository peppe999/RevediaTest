package it.ingsw.revedia.controller;

import java.sql.SQLException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
		ModelAndView model =  new ModelAndView();
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
	
	@PostMapping("/register")
	public ModelAndView register(@RequestParam("nickname") String nickname, @RequestParam("nome") String firstName,
								 @RequestParam("cognome") String lastName, @RequestParam("mail") String mail,
								 @RequestParam("password") String password)
	{
		ModelAndView model = new ModelAndView();
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setNickname(nickname);
		user.setMail(mail);
		String MD5Password = PasswordManager.getMD5(password);
		try
		{
			DatabaseManager.getIstance().getDaoFactory().getUserJDBC().insertUser(user, MD5Password);
			model.setViewName("second.jsp");
			model.addObject("nickname", nickname);
			System.out.println("utente registrato");
			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			model.setViewName("register.jsp");
			model.addObject("nonRegistrato", "Utente già registrato");
		}
		
		return model;
	}
}
