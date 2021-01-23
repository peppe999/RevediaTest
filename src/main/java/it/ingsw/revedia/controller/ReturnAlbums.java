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
import it.ingsw.revedia.jdbcModels.AlbumJDBC;
import it.ingsw.revedia.model.Album;

@Controller
public class ReturnAlbums {

	@RequestMapping(value = "/Albums", method = RequestMethod.GET)
	public void returnAlbums(HttpServletResponse response) throws IOException, SQLException {

		AlbumJDBC albumJDBC = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();

		List<Album> albums = albumJDBC.findAll();
		if (albums.isEmpty()) {
			response.getWriter().println("No albums found!");
			return;
		}

		JsonArray arrayJ = new JsonArray();

		for (Album album : albums) {

			JsonObject gson = new JsonObject();
			gson.addProperty("title", album.getName());
			gson.addProperty("autor", album.getArtist());
			gson.addProperty("label", album.getLabel());

			// gson.addProperty("image", album.getImage());
			arrayJ.add(gson);
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(arrayJ.toString());
		response.getWriter().println();

	}

}
