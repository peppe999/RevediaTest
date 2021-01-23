package it.ingsw.revedia.controller;

import java.sql.SQLException;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.utilities.EmailManager;
import it.ingsw.revedia.utilities.PasswordManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import it.ingsw.revedia.model.User;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RegisterUser
{
	@GetMapping("/Register")
	public String RegisterDispatcher(){
		return "register";
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
			model.setViewName("second");
			model.addObject("nickname", nickname);
			//EmailManager.registrationConfirm(mail, nickname);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			model.setViewName("register");
			model.addObject("nonRegistrato", "Utente già registrato");
		}

		return model;
	}

}
