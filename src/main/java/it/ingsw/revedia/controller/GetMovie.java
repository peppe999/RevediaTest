package it.ingsw.revedia.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.MovieJDBC;
import it.ingsw.revedia.model.Movie;

@Controller
public class GetMovie {

	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public ModelAndView returnMovie(@RequestParam("title") String title) throws IOException, SQLException {

		ModelAndView model = new ModelAndView();

		MovieJDBC movieJDBC = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();
		Movie movie = movieJDBC.findByPrimaryKey(title);

		model.setViewName("index");
		model.addObject("albumjbadoadiba", movie);

		return model;
	}

}
