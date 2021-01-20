package it.ingsw.revedia.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.MovieJDBC;
import it.ingsw.revedia.model.Movie;

@Controller
public class ReturnFilms
{
//	@RequestMapping(value = "/Movies", method = RequestMethod.GET)
//	public void returnSongs(HttpServletResponse response) throws IOException, SQLException
//	{
//
//		MovieJDBC movieJDBC = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();
//
//		List<Movie> movies = movieJDBC.findAll();
//		if (movies.isEmpty())
//		{
//			response.getWriter().println("No films found!");
//			return;
//		}
//
//		/* JSON */
//
//	}

}
