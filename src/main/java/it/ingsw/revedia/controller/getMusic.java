package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Movie;

public class getMusic {

	@GetMapping("/music")
	public ModelAndView returnMusic() {
		ModelAndView model = new ModelAndView();

		try {

			MovieDao movieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();

			ArrayList<Movie> movie = movieDao.getHighRateMovies();
			model.addObject("list", movie);

			ArrayList<Movie> latestMovie = movieDao.getLatestMovies();
			model.addObject("latestList", latestMovie);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
