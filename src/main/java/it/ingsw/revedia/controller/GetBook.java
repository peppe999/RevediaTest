package it.ingsw.revedia.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.BookJDBC;
import it.ingsw.revedia.model.Book;

@Controller
public class GetBook {

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public ModelAndView returnBook(@RequestParam("title") String title) throws IOException, SQLException {

		ModelAndView model = new ModelAndView();

		BookJDBC bookJDBC = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();
		Book book = bookJDBC.getBook(title);

		model.setViewName("index");
		model.addObject("albumjbadoadiba", book);

		return model;

	}

}
