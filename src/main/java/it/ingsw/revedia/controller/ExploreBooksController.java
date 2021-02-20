package it.ingsw.revedia.controller;

import it.ingsw.revedia.daoInterfaces.BookDao;
import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Book;
import it.ingsw.revedia.model.Movie;
import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class ExploreBooksController {

	@GetMapping("/books/explore")
	public ModelAndView returnBooksGenre(@RequestParam("genre") String genres, @RequestParam(name = "page", required = false) Integer page, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("exploreBooks");

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

		model.addObject("genre", genres);

		try {
			BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();

			ArrayList<Book> bestBooks = bookDao.getBestBooksByGenre(genres);
			model.addObject("bestBooksList", bestBooks);
			ArrayList<Book> latestBooks = bookDao.getLatestBooksByGenre(genres);
			model.addObject("latestBooksList", latestBooks);

			Integer countBooks = bookDao.getBooksNumberByGenre(genres);
			model.addObject("countBooks", countBooks);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

	@PostMapping("/books/explore")
	public ModelAndView getContents(@RequestParam("genre") String genres, @RequestParam("page") Integer page, @RequestParam("modality") Integer modality, @RequestParam("order") Integer order) {
		return loadBooks(genres, page, modality, order);
	}

	@GetMapping("/books/explore/numberOfContents")
	@ResponseBody
	public Integer getNumberOfPages(@RequestParam("genre") String genre) {
		Integer numberOfContents = getNumberOfBooks(genre);

		if(numberOfContents == 0)
			return 1;

		Integer numberOfPages = numberOfContents / 20;
		if(numberOfContents % 20 != 0)
			numberOfPages++;

		return numberOfPages;
	}

	private Integer getNumberOfBooks(String genre) {
		Integer n = 0;
		try {
			BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();

			n = bookDao.getBooksNumberByGenre(genre);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return n;
	}

	private ModelAndView loadBooks(String genres, Integer page, Integer modality, Integer order) {
		ModelAndView model = new ModelAndView("allBooksArea");

		try {
			BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();

			ArrayList<Book> books = bookDao.findByGenre(genres, (page - 1) * 20, modality, order);
			model.addObject("booksList", books);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
