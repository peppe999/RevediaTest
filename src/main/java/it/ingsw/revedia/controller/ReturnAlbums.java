package it.ingsw.revedia.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.AlbumJDBC;

@Controller
public class ReturnAlbums
{

//	@RequestMapping(value = "/Albums", method = RequestMethod.GET)
//	public void returnAlbums(HttpServletResponse response) throws IOException
//	{
//
//		AlbumJDBC albumJDBC = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();
//
//		/*
//		 * List<Album> albums = albumJDBC.findAll(); if (albums.isEmpty()) {
//		 * response.getWriter().println("No albums found!"); return; }
//		 */
//
//		/* JSON */
//
//	}

}
