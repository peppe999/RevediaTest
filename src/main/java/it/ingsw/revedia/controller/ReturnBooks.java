package it.ingsw.revedia.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.BookJDBC;
import it.ingsw.revedia.model.Book;

@Controller
public class ReturnBooks {

	@RequestMapping(value = "/Boooks", method = RequestMethod.GET)
	public void returnBooks(HttpServletResponse response) throws IOException, SQLException {

		BookJDBC bookJDBC = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();

		List<Book> books = bookJDBC.findAll();
		if (books.isEmpty()) {
			response.getWriter().println("No books found!");
			return;
		}

		JsonArray arrayJ = new JsonArray();

		for (Book book : books) {

			JsonObject gson = new JsonObject();
			gson.addProperty("title", book.getTitle());
			gson.addProperty("autor", book.getArtist());
			gson.addProperty("PH", book.getPublishingHouse());

			// gson.addProperty("image", book.getImage());
			arrayJ.add(gson);
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(arrayJ.toString());
		response.getWriter().println();

	}

}
