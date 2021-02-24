package it.ingsw.revedia.controller;

import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.daoInterfaces.BookDao;
import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.*;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class SearchController {

	@GetMapping("/search")
	public ModelAndView getSearchResults(@RequestParam("query") String query, @RequestParam(name = "type", required = false) String type, HttpServletRequest request) {
		if(type == null)
			return loadResultsMainPage(query, request);

		return loadMainInfo(query, type, request);
	}

	private ModelAndView loadMainInfo(String query, String type, HttpServletRequest request) {
		ModelAndView model;

		if(type == null)
			model = new ModelAndView("searchRes");
		else {
			model = new ModelAndView("searchResByType");
			switch (type) {
				case "albums":
					model.addObject("type", "Album");
					break;
				case "songs":
					model.addObject("type", "Brani");
					break;
				case "movies":
					model.addObject("type", "Film");
					break;
				case "books":
					model.addObject("type", "Libri");
					break;
			}
		}

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

		model.addObject("queryString", query);

		return model;
	}

	private String[] getQueryList(String plainQuery) {
		plainQuery.replaceAll("'", " ");
		String[] tokens = plainQuery.split(" ");

		return tokens;
	}

	private ModelAndView loadResultsByType(String query, String type, Integer offset) {
		ModelAndView model = null;

		String[] queryList = getQueryList(query);

		switch (type) {
			case "albums":
				model = new ModelAndView("moreAlbumResults");
				AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();
				try {
					ArrayList<Album> albums = albumDao.searchByKeyWords(queryList, 15, offset);
					model.addObject("albumsList", albums);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				break;
			case "songs":
				model = new ModelAndView("moreSongResults");
				SongDao songDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();
				try {
					ArrayList<Song> songs = songDao.searchByKeyWords(queryList, 15, offset);
					model.addObject("songsList", songs);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				break;
			case "movies":
				model = new ModelAndView("moreMovieResults");
				MovieDao movieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();
				try {
					ArrayList<Movie> movies = movieDao.searchByKeyWords(queryList, 15, offset);
					model.addObject("moviesList", movies);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				break;
			case "books":
				model = new ModelAndView("moreBookResults");
				BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();
				try {
					ArrayList<Book> books = bookDao.searchByKeyWords(queryList, 15, offset);
					model.addObject("booksList", books);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				break;
		}

		return model;
	}

	private ModelAndView loadResultsMainPage(String query, HttpServletRequest request) {
		ModelAndView model = loadMainInfo(query, null, request);
		String[] queryList = getQueryList(query);

		loadSomeResults(queryList, model, 4);

		return model;
	}

	private void loadSomeResults(String[] queryList, ModelAndView model, Integer limit) {
		try {
			BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();
			MovieDao movieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();
			SongDao songDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();
			AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();

			ArrayList<Book> books = bookDao.searchByKeyWords(queryList, limit, 0);
			ArrayList<Movie> movies = movieDao.searchByKeyWords(queryList, limit, 0);
			ArrayList<Song> songs = songDao.searchByKeyWords(queryList, limit, 0);
			ArrayList<Album> albums = albumDao.searchByKeyWords(queryList, limit, 0);

			model.addObject("booksList", books);
			model.addObject("moviesList", movies);
			model.addObject("songsList", songs);
			model.addObject("albumsList", albums);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@PostMapping("/search")
	public ModelAndView getContents(@RequestParam("query") String query, @RequestParam("type") String type, @RequestParam("offset") Integer offset) {
		return loadResultsByType(query, type, offset);
	}

	@PostMapping("/search/autocomplete")
	public ModelAndView getAutocompleteResults(@RequestParam("query") String query) {
		ModelAndView model = new ModelAndView("searchAutocomplete");
		String[] queryList = getQueryList(query);

		loadSomeResults(queryList, model, 1);

		return model;
	}
/*
	@GetMapping("/books/explore/numberOfContents")
	@ResponseBody
	public Integer getNumberOfPages(@RequestParam("genre") String genre) {
		Integer numberOfContents = getNumberOfBooks(genre);

		if(numberOfContents == 0)
			return 1;

		Integer numberOfPages = numberOfContents / 20;
		if(numberOfContents % 20 != 0)
			numberOfPages++;

		return numberOfPages;
	}

	private Integer getNumberOfBooks(String genre) {
		Integer n = 0;
		try {
			BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();

			n = bookDao.getBooksNumberByGenre(genre);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return n;
	}

	private ModelAndView loadBooks(String genres, Integer page, Integer modality, Integer order) {
		ModelAndView model = new ModelAndView("allBooksArea");

		try {
			BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();

			ArrayList<Book> books = bookDao.findByGenre(genres, (page - 1) * 20, modality, order);
			model.addObject("booksList", books);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return model;
	}*/

}
