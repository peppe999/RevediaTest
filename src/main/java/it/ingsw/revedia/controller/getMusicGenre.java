package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.Song;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class getMusicGenre {

	@GetMapping("/allgenreofmusic")
	public ModelAndView returnMusicGenre(@RequestParam("genre") String genres, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("allgenreofmusic");

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

			ArrayList<Song> song1 = SongDao.getRandomSongs(genres);
			model.addObject("list1", song1);

			ArrayList<Song> song2 = SongDao.getRandomSongs(genres);
			model.addObject("list2", song2);

			ArrayList<Song> song3 = SongDao.getRandomSongs(genres);
			model.addObject("list3", song3);

			ArrayList<Song> song4 = SongDao.getRandomSongs(genres);
			model.addObject("list4", song4);

			ArrayList<Song> song5 = SongDao.getRandomSongs(genres);
			model.addObject("list5", song5);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
