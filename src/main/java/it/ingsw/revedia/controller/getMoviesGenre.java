package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Movie;

@Controller
public class getMoviesGenre {

	@GetMapping("/allgenreofmovies")
	public ModelAndView returnMoviesGenre(@RequestParam("genre") String genres) {
		ModelAndView model = new ModelAndView();

		try {

			MovieDao MovieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();

			ArrayList<Movie> movie = MovieDao.getHighRateMovieByGenre(genres);
			model.addObject("list", movie);
			ArrayList<Movie> latestMovie = MovieDao.getLatestMovieByGenre(genres);
			model.addObject("latestList", latestMovie);
			Integer countMovies = MovieDao.getNumerMovieByGenre(genres);
			model.addObject("countMovies", countMovies);

			ArrayList<Movie> movie1 = MovieDao.getRandomMovies(genres);
			model.addObject("list1", movie1);

			ArrayList<Movie> movie2 = MovieDao.getRandomMovies(genres);
			model.addObject("list2", movie2);

			ArrayList<Movie> movie3 = MovieDao.getRandomMovies(genres);
			model.addObject("list3", movie3);

			ArrayList<Movie> movie4 = MovieDao.getRandomMovies(genres);
			model.addObject("list4", movie4);

			ArrayList<Movie> movie5 = MovieDao.getRandomMovies(genres);
			model.addObject("list5", movie5);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}