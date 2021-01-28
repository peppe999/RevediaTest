package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;

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
public class GetMusic {

	@GetMapping("/music")
	public ModelAndView returnMusic(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("music");

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

			// GENERI
			ArrayList<String> genre = AlbumDao.getRandomGenres();
			model.addObject("genreList", genre);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
