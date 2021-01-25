package it.ingsw.revedia.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.BookJDBC;
import it.ingsw.revedia.jdbcModels.MovieJDBC;
import it.ingsw.revedia.model.Book;
import it.ingsw.revedia.model.Movie;
import it.ingsw.revedia.model.Song;

@Controller
public class SearchBar
{

	// RICERCA CANZONE PER NOME

//	@RequestMapping(value = "/tag della barra di ricerca", method = RequestMethod.GET)
//	public Song SongByName(ModelMap model, @RequestParam("/nome barra di ricerca") String name)
//	{
//		/*
//		 * SongJDBC songJDBC =
//		 * DatabaseManager.getIstance().getDaoFactory().getSongJDBC(); Song song =
//		 * songJDBC.findByPrimaryKey(name);
//		 *
//		 * return song;
//		 */
//
//		return null;
//
//	}
//
//	// RICERCA Film per Titolo
//
//	@RequestMapping(value = "/tag della barra di ricerca", method = RequestMethod.GET)
//	public Movie MovieByTitle(ModelMap model, @RequestParam("/nome barra di ricerca") String title) throws SQLException
//	{
//
//		MovieJDBC movieJDBC = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();
//		Movie movie = movieJDBC.findByPrimaryKey(title);
//
//		return movie;
//
//	}
//
//	// RICERCA Libro per titolo
//
//	@RequestMapping(value = "/tag della barra di ricerca", method = RequestMethod.GET)
//	public Book BookByTitle(ModelMap model, @RequestParam("/nome barra di ricerca") String title) throws SQLException
//	{
//
//		BookJDBC bookJDBC = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();
//		Book book = bookJDBC.findByPrimaryKey(title);
//
//		return book;
//	}*/

}