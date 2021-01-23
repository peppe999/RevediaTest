package it.ingsw.revedia.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.MovieJDBC;
import it.ingsw.revedia.model.Movie;

@Controller
public class ReturnFilms {
	@RequestMapping(value = "/Movies", method = RequestMethod.GET)
	public void returnMovies(HttpServletResponse response) throws IOException, SQLException {

		MovieJDBC movieJDBC = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();

		List<Movie> movies = movieJDBC.findAll();
		if (movies.isEmpty()) {
			response.getWriter().println("No films found!");
			return;
		}

		JsonArray arrayJ = new JsonArray();

		for (Movie movie : movies) {

			JsonObject gson = new JsonObject();
			gson.addProperty("titolo", movie.getTitle());
			// gson.addProperty("image", movie.get());
			arrayJ.add(gson);
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(arrayJ.toString());
		response.getWriter().println();

	}
}
