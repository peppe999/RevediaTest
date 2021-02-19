package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.Song;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MusicHomeController {

	@GetMapping("/music")
	public ModelAndView returnMusic(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("musicHome");

		HttpSession session = request.getSession();
		if(session.getAttribute("nickname") != null)
		{
			User user = new User();
			user.setNickname(session.getAttribute("nickname").toString());
			user.setPermissions(Permissions.valueOf(session.getAttribute("permissions").toString()));
			model.addObject("user",user);
			model.addObject("hideuser","");
			model.addObject("signupbutton","display: none");
		}
		else
		{
			model.addObject("hideuser","display: none");
			model.addObject("signupbutton"," ");
		}

		try {

			SongDao songDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();

			ArrayList<Song> song = songDao.getHighRateSongs();
			model.addObject("bestSongsList", song);
			ArrayList<Song> latestSong = songDao.getLatestSongs();
			model.addObject("latestSongsList", latestSong);

			// ALBUM

			AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();

			ArrayList<Album> album = albumDao.getHighRateAlbums();
			model.addObject("bestAlbumsList", album);
			ArrayList<Album> latestAlbum = albumDao.getLatestAlbums();
			model.addObject("latestAlbumsList", latestAlbum);

			// GENERI
			List<String> genre = albumDao.getAllGenres();
			model.addObject("genreList", genre);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
