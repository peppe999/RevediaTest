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
import it.ingsw.revedia.jdbcModels.SongJDBC;
import it.ingsw.revedia.model.Song;

@Controller
public class ReturnSong {
	@RequestMapping(value = "/Songs", method = RequestMethod.GET)
	public void returnSongs(HttpServletResponse response) throws IOException, SQLException {

		SongJDBC songJDBC = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();

		List<Song> songs = songJDBC.findAll();
		if (songs.isEmpty()) {
			response.getWriter().println("No songs found!");
			return;
		}

		JsonArray arrayJ = new JsonArray();

		for (Song song : songs) {

			JsonObject gson = new JsonObject();
			gson.addProperty("name", song.getName());
			gson.addProperty("album", song.getAlbumName());

			// gson.addProperty("image", song.getImage());
			arrayJ.add(gson);
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(arrayJ.toString());
		response.getWriter().println();

	}

}
