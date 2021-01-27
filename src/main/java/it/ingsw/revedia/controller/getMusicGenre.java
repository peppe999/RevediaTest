package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.Song;

@Controller
public class getMusicGenre {

	/*
	 * @GetMapping("/allgenreofmusic") public String musics() { return
	 * "allgenreofmusic"; }
	 */

	@GetMapping("/allgenreofmusic")
	public ModelAndView returnMusicGenre(@RequestParam("genre") String genres) {
		ModelAndView model = new ModelAndView();

		try {

			SongDao SongDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();

			ArrayList<Song> song = SongDao.getHighRateSongByGenre(genres);
			model.addObject("list", song);
			ArrayList<Song> latestSong = SongDao.getLatestSongByGenre(genres);
			model.addObject("latestList", latestSong);

			AlbumDao AlbumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();

			ArrayList<Album> album = AlbumDao.getHighRateAlbumByGenre(genres);
			model.addObject("listAlbums", album);
			ArrayList<Album> latestAlbum = AlbumDao.getLatestAlbumByGenre(genres);
			model.addObject("latestListAlbums", latestAlbum);

			Integer countAlbums = AlbumDao.getNumerAlbumByGenre(genres);
			model.addObject("countAlbums", countAlbums);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
