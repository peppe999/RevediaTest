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
import it.ingsw.revedia.jdbcModels.AlbumJDBC;
import it.ingsw.revedia.model.Album;

@Controller
public class GetAlbum {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public void returnAlbum(HttpServletResponse response, HttpServletRequest request) throws IOException, SQLException {

		Integer id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);

		AlbumJDBC albumJDBC = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();
		Album album = albumJDBC.getAlbum(id);

		JsonObject gson = new JsonObject();

		gson.addProperty("name", album.getName());
		// gson.addProperty("image", song.getImage_source());
		gson.addProperty("numberOfSong", album.getNumberOfSongs());
		gson.addProperty("autor", album.getArtist());
		gson.addProperty("label", album.getLabel());
		// gson.addProperty("genres", movie.getGenres());

		response.setContentType("application/json");
		response.getWriter().println(gson.toString());
		response.getWriter().flush();

	}

}
