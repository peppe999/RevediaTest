package it.ingsw.revedia.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.BookJDBC;
import it.ingsw.revedia.model.Book;

@Controller
public class GetBook {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public void returnBook(HttpServletResponse response, HttpServletRequest request) throws IOException, SQLException {

		String title = request.getParameter("title");
		System.out.println(title);

		BookJDBC bookJDBC = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();
		Book book = bookJDBC.getBook(title);

		JsonObject gson = new JsonObject();

		gson.addProperty("title", book.getTitle());
		// gson.addProperty("image", song.getImage_source());
		gson.addProperty("description", book.getDescription());
		gson.addProperty("numberOfPages", book.getNumberOfPages());
		gson.addProperty("autor", book.getArtist());
		gson.addProperty("PH", book.getPublishingHouse());
		// gson.addProperty("genres", movie.getGenres());

		response.setContentType("application/json");
		response.getWriter().println(gson.toString());
		response.getWriter().flush();

	}

}
