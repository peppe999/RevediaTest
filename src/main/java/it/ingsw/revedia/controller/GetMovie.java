package it.ingsw.revedia.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.MovieJDBC;
import it.ingsw.revedia.model.Movie;

@Controller
public class GetMovie {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public void returnMovie(HttpServletResponse response, HttpServletRequest request) throws IOException, SQLException {

		String title = request.getParameter("title");
		System.out.println(title);

		MovieJDBC movieJDBC = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();
		Movie movie = movieJDBC.findByPrimaryKey(title);

		JsonObject gson = new JsonObject();

		gson.addProperty("title", movie.getTitle());
		// gson.addProperty("image", song.getImage_source());
		gson.addProperty("description", movie.getDescription());
		gson.addProperty("duration", movie.getLength());
		// gson.addProperty("genres", movie.getGenres());

		response.setContentType("application/json");
		response.getWriter().println(gson.toString());
		response.getWriter().flush();

	}

}
