package it.ingsw.revedia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetMovie {

	@GetMapping("/movies")
	public String movie() {
		return "movies";
	}

	/*
	 * @GetMapping("/") public ModelAndView highrateMovies() { ModelAndView model =
	 * new ModelAndView();
	 * 
	 * try {
	 * 
	 * MovieDao movieDao =
	 * DatabaseManager.getIstance().getDaoFactory().getMovieJDBC(); Movie movie =
	 * movieDao.getHighRateMovies(1, true).get(0); model.addObject("movieTitle",
	 * movie.getTitle()); model.addObject("movieUser", movie.getUser()); } catch
	 * (SQLException throwables) { throwables.printStackTrace(); }
	 * 
	 * return model; }
	 */

}
