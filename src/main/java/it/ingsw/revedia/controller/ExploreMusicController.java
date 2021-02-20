package it.ingsw.revedia.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.Song;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ExploreMusicController {

	@GetMapping("/music/explore")
	public ModelAndView returnMusicGenre(@RequestParam("genre") String genres, @RequestParam(name = "page", required = false) Integer page, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("exploreMusic");

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

		model.addObject("genre", genres);

		try {
			SongDao songDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();

			ArrayList<Song> song = songDao.getBestSongsByGenre(genres);
			model.addObject("bestSongsList", song);
			ArrayList<Song> latestSong = songDao.getLatestSongsByGenre(genres);
			model.addObject("latestSongsList", latestSong);

			AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();

			ArrayList<Album> album = albumDao.getBestAlbumsByGenre(genres);
			model.addObject("bestAlbumsList", album);
			ArrayList<Album> latestAlbum = albumDao.getLatestAlbumsByGenre(genres);
			model.addObject("latestAlbumsList", latestAlbum);

			Integer countAlbums = albumDao.getAlbumsNumberByGenre(genres, false);
			model.addObject("countAlbums", countAlbums);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

	@PostMapping("/music/explore")
	public ModelAndView getContents(@RequestParam("genre") String genres, @RequestParam("page") Integer page, @RequestParam("modality") Integer modality,
									@RequestParam("order") Integer order, @RequestParam("type") Integer type) {

		if(type == 1)	// SOLO BRANI
			return loadSongs(genres, page, modality, order);

		boolean excludeSingles = (type == 0) ? false : true;

		return loadAlbums(genres, page, modality, order, excludeSingles);
	}

	@GetMapping("/music/explore/numberOfContents")
	@ResponseBody
	public Integer getNumberOfPages(@RequestParam("genre") String genre, @RequestParam("type") Integer type) {
		Integer numberOfContents;
		if(type == 1)	// SOLO BRANI
			numberOfContents =  getNumberOfSongs(genre);
		else {
			boolean excludeSingles = (type == 0) ? false : true;

			numberOfContents = getNumberOfAlbums(genre, excludeSingles);
		}

		if(numberOfContents == 0)
			return 1;

		Integer numberOfPages = numberOfContents / 20;
		if(numberOfContents % 20 != 0)
			numberOfPages++;

		return numberOfPages;
	}

	private Integer getNumberOfAlbums(String genre, Boolean excludeSingles) {
		Integer n = 0;
		try {
			AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();

			n = albumDao.getAlbumsNumberByGenre(genre, excludeSingles);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return n;
	}

	private Integer getNumberOfSongs(String genre) {
		Integer n = 0;
		try {
			SongDao songDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();

			n = songDao.getSongsNumberByGenre(genre);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return n;
	}

	private ModelAndView loadAlbums(String genres, Integer page, Integer modality, Integer order, Boolean excludeSingles) {
		ModelAndView model = new ModelAndView("allAlbumsArea");

		try {
			AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();

			ArrayList<Album> albums = albumDao.findByGenre(genres, (page - 1) * 20, modality, order, excludeSingles);
			model.addObject("albumsList", albums);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

	private ModelAndView loadSongs(String genres, Integer page, Integer modality, Integer order) {
		ModelAndView model = new ModelAndView("allSongsArea");

		try {
			SongDao songDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();

			ArrayList<Song> songs = songDao.findByGenre(genres, (page - 1) * 20, modality, order);
			model.addObject("songsList", songs);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}

}
