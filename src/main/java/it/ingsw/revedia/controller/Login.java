package it.ingsw.revedia.controller;

import java.sql.SQLException;

import it.ingsw.revedia.jdbcModels.TupleNotFoundException;
import it.ingsw.revedia.jdbcModels.UserJDBC;
import it.ingsw.revedia.model.Book;
import it.ingsw.revedia.utilities.EmailManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.PasswordManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class Login
{
	@GetMapping("/Login")
	public String login(HttpServletRequest request)
	{
		if(request.getSession().getAttribute("nickname") != null)
			return "redirect:/";

		return "login";
	}

	@PostMapping("/loginUser")
	public ModelAndView loginUser(HttpServletRequest request, @RequestParam("username") String nickname,
								  @RequestParam("password") String password)
	{
		ModelAndView model =  new ModelAndView();
		try
		{
			String MD5Password = PasswordManager.getMD5(password);
			UserJDBC userJDBC = DatabaseManager.getIstance().getDaoFactory().getUserJDBC();
			if(userJDBC.validateLoginByNicknameOrMail(nickname, nickname, MD5Password))
			{
				User user = userJDBC.getUserByNicknameOrMail(nickname,nickname);
				HttpSession session = request.getSession(true);
				session.setAttribute("nickname", user.getNickname());
				session.setAttribute("mail",user.getMail());
				session.setAttribute("firstname",user.getFirstName());
				session.setAttribute("lastname",user.getLastName());
				session.setAttribute("permissions",user.getPermissions().toString());

				model.setViewName("redirect:/");
			}
		} 
		catch (SQLException | TupleNotFoundException e)
		{
			e.printStackTrace();
			model.setViewName("login");
			model.addObject("invalidparameters", "Nome utente, mail o password errati");
		}
		
		return model;
	}

	@PostMapping("/checkLogin")
	@ResponseBody
	public Boolean checkLogin(HttpServletRequest request) {
		if(request.getSession().getAttribute("nickname") != null)
			return true;
		return false;
	}
}
