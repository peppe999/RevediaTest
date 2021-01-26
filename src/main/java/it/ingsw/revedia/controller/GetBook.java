package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.daoInterfaces.BookDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Book;

@Controller
public class GetBook {

	@GetMapping("/books")
	public ModelAndView getBooks() {
		ModelAndView model = new ModelAndView();

		try {

			BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();

			ArrayList<Book> book = bookDao.getHighRateBooks();
			model.addObject("list", book);

			ArrayList<Book> latestBooks = bookDao.getLatestBooks();
			model.addObject("latestList", latestBooks);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
