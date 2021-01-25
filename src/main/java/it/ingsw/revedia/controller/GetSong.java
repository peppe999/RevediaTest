package it.ingsw.revedia.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.SongJDBC;
import it.ingsw.revedia.model.Song;

@Controller
public class GetSong {

	@RequestMapping(value = "/Songs", method = RequestMethod.GET)
	public ModelAndView returnSong(@RequestParam("title") String title, @RequestParam("id") Integer id)
			throws IOException, SQLException {

		ModelAndView model = new ModelAndView();

		SongJDBC songJDBC = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();
		Song song = songJDBC.findByPrimaryKey(title, id);

		model.setViewName("index");
		model.addObject("albumjbadoadiba", song);

		return model;

	}

}
