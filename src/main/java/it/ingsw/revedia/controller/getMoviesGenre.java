package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Movie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class getMoviesGenre {

	@GetMapping("/allgenreofmovies")
	public ModelAndView returnMoviesGenre(@RequestParam("genre") String genres, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("allgenreofmovies");

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

			MovieDao MovieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();

			ArrayList<Movie> movie = MovieDao.getBestMoviesByGenre(genres);
			model.addObject("list", movie);
			ArrayList<Movie> latestMovie = MovieDao.getLatestMoviesByGenre(genres);
			model.addObject("latestList", latestMovie);
			Integer countMovies = MovieDao.getMoviesNumberByGenre(genres);
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
