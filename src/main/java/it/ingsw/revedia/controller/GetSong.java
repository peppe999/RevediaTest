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
import it.ingsw.revedia.jdbcModels.SongJDBC;
import it.ingsw.revedia.model.Song;

@Controller
public class GetSong {

	@RequestMapping(value = "/Songs", method = RequestMethod.GET)
	public void returnSong(HttpServletResponse response, HttpServletRequest request) throws IOException, SQLException {

		String titolo = request.getParameter("name");
		Integer id = Integer.parseInt(request.getParameter("id"));
		System.out.println(titolo);
		System.out.println(id);

		SongJDBC songJDBC = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();
		Song song = songJDBC.findByPrimaryKey(titolo, id);

		JsonObject gson = new JsonObject();

		gson.addProperty("name", song.getName());
		// gson.addProperty("image", song.getImage_source());
		gson.addProperty("album", song.getAlbumName());
		gson.addProperty("id", song.getAlbumID());
		gson.addProperty("description", song.getDescription());
		gson.addProperty("genre", song.getGenre());

		response.setContentType("application/json");
		response.getWriter().println(gson.toString());
		response.getWriter().flush();

	}

}
