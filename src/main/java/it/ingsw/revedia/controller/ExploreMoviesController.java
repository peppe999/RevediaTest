package it.ingsw.revedia.controller;

import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Movie;
import it.ingsw.revedia.model.Song;
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
public class ExploreMoviesController {

	@GetMapping("/movies/explore")
	public ModelAndView returnMoviesGenre(@RequestParam("genre") String genres, @RequestParam(name = "page", required = false) Integer page, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("exploreMovies");

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
			MovieDao movieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();

			ArrayList<Movie> bestMovies = movieDao.getBestMoviesByGenre(genres);
			model.addObject("bestMoviesList", bestMovies);
			ArrayList<Movie> latestMovies = movieDao.getLatestMoviesByGenre(genres);
			model.addObject("latestMoviesList", latestMovies);

			Integer countMovies = movieDao.getMoviesNumberByGenre(genres);
			model.addObject("countMovies", countMovies);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

	@PostMapping("/movies/explore")
	public ModelAndView getContents(@RequestParam("genre") String genres, @RequestParam("page") Integer page, @RequestParam("modality") Integer modality, @RequestParam("order") Integer order) {
		return loadMovies(genres, page, modality, order);
	}

	@GetMapping("/movies/explore/numberOfContents")
	@ResponseBody
	public Integer getNumberOfPages(@RequestParam("genre") String genre) {
		Integer numberOfContents = getNumberOfMovies(genre);

		if(numberOfContents == 0)
			return 1;

		Integer numberOfPages = numberOfContents / 20;
		if(numberOfContents % 20 != 0)
			numberOfPages++;

		return numberOfPages;
	}

	private Integer getNumberOfMovies(String genre) {
		Integer n = 0;
		try {
			MovieDao movieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();

			n = movieDao.getMoviesNumberByGenre(genre);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return n;
	}

	private ModelAndView loadMovies(String genres, Integer page, Integer modality, Integer order) {
		ModelAndView model = new ModelAndView("allMoviesArea");

		try {
			MovieDao movieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();

			ArrayList<Movie> movies = movieDao.findByGenre(genres, (page - 1) * 20, modality, order);
			model.addObject("moviesList", movies);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
