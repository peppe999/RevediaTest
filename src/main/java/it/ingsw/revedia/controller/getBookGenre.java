package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.daoInterfaces.BookDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class getBookGenre {

	@GetMapping("/allgenreofbooks")
	public ModelAndView returnBooksGenre(@RequestParam("genre") String genres, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("allgenreofbooks");

		HttpSession session = request.getSession();
		if(session.getAttribute("nickname") != null)
		{
			User user = new User();
			user.setNickname(session.getAttribute("nickname").toString());
			user.setPermissions(Permissions.valueOf(session.getAttribute("permissions").toString()));
			model.addObject("user",user);
			model.addObject("hideuser","");
			model.addObject("signupbutton","display: none");
		}
		else
		{
			model.addObject("hideuser","display: none");
			model.addObject("signupbutton"," ");
		}

		try {

			BookDao BookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();

			ArrayList<Book> Book = BookDao.getBestBooksByGenre(genres);
			model.addObject("list", Book);
			ArrayList<Book> latestBook = BookDao.getLatestBooksByGenre(genres);
			model.addObject("latestList", latestBook);
			Integer countBooks = BookDao.getBooksNumberByGenre(genres);
			model.addObject("countBooks", countBooks);

			ArrayList<Book> Book1 = BookDao.getRandomBooks(genres);
			model.addObject("list1", Book1);

			ArrayList<Book> Book2 = BookDao.getRandomBooks(genres);
			model.addObject("list2", Book2);

			ArrayList<Book> Book3 = BookDao.getRandomBooks(genres);
			model.addObject("list3", Book3);

			ArrayList<Book> Book4 = BookDao.getRandomBooks(genres);
			model.addObject("list4", Book4);

			ArrayList<Book> Book5 = BookDao.getRandomBooks(genres);
			model.addObject("list5", Book5);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
