package it.ingsw.revedia.controller;


import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Book;
import it.ingsw.revedia.utilities.PasswordManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

@Controller
public class PopulateBooksController {

    @GetMapping("/loaderBooks")
    public String getPage() {
        return "loaderBooks";
    }

   /* @GetMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println(PasswordManager.getMD5("test123"));
        return "<img src='images/music/lalala'></img>";
    }*/

  /*  @GetMapping("/loader/genres")
    @ResponseBody
    public List<String> getGenres(String title) {
        try {
            List<String> genres = DatabaseManager.getIstance().getDaoFactory().getBookJDBC().getGenres(title);
            return genres;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }*/

    /*@PostMapping("/loader/upalbum")
    @ResponseBody
    public Integer uploadBook(@RequestBody Book book) {
        int id = -1;
        System.out.println(book.getGenres().get(0));
        System.out.println(book.getPostDate());
        try {
            id = DatabaseManager.getIstance().getDaoFactory().getBookJDBC().insertBook(book,);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return id;
    }*/

    @PostMapping("/loader/upbook/img")
    @ResponseBody
    public String uploadImage(@RequestParam("id")String id, @RequestParam("url")String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            BufferedImage image = ImageIO.read(url);
            ImageIO.write(image, "jpg",new File("src/main/webapp/images/books/" + id + ".jpg"));
            return "ok";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/loader/upbook")
    @ResponseBody
    public int uploadBook(@RequestBody Book s) {
        System.out.println(s.getTitle());
        s.setUser("revediaTest");
        int id = -1;
        try {
            id = DatabaseManager.getIstance().getDaoFactory().getBookJDBC().insertBook(s);
        } catch ( SQLException throwables) {
            throwables.printStackTrace();
        }

        return id;
    }

    @GetMapping("/loader/fakesession")
    public  String getGenres(ModelMap model) {
        model.addAttribute("nickname", "Filomena");
        return "/book";
    }

}


