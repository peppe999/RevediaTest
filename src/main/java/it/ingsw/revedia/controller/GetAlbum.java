package it.ingsw.revedia.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.AlbumJDBC;
import it.ingsw.revedia.model.Album;

@Controller
public class GetAlbum {

	@RequestMapping(value = "/music", method = RequestMethod.GET)
	public ModelAndView returnAlbum(@RequestParam("id") Integer id) throws IOException, SQLException {

		ModelAndView model = new ModelAndView();

		AlbumJDBC albumJDBC = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();
		Album album = albumJDBC.findByPrimaryKey(id);

		model.setViewName("index");
		model.addObject("albumjbadoadiba", album);

		return model;
	}

}
