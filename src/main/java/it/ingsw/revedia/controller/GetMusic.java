package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.Song;

@Controller
public class GetMusic {

	@GetMapping("/music")
	public ModelAndView returnMusic() {
		ModelAndView model = new ModelAndView();

		try {

			SongDao SongDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();

			ArrayList<Song> song = SongDao.getHighRateSongs();
			model.addObject("list", song);
			ArrayList<Song> latestSong = SongDao.getLatestSongs();
			model.addObject("latestList", latestSong);

			// ALBUM

			AlbumDao AlbumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();

			ArrayList<Album> album = AlbumDao.getHighRateAlbums();
			model.addObject("listAlbums", album);
			ArrayList<Album> latestAlbum = AlbumDao.getLatestAlbums();
			model.addObject("latestListAlbums", latestAlbum);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
