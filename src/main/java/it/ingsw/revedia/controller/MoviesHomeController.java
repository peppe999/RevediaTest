package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Movie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MoviesHomeController {

	@GetMapping("/movies")
	public ModelAndView highrateMovies(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("moviesHome");

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

			MovieDao movieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();

			ArrayList<Movie> movie = movieDao.getBestMovies();
			model.addObject("bestMoviesList", movie);

			ArrayList<Movie> latestMovie = movieDao.getLatestMovies();
			model.addObject("latestMoviesList", latestMovie);

			List<String> genre = movieDao.getAllGenres();
			model.addObject("genreList", genre);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
