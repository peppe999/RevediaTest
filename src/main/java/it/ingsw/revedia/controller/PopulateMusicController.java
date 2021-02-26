package it.ingsw.revedia.controller;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.Song;
import it.ingsw.revedia.utilities.PasswordManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PopulateMusicController {

    @GetMapping("/loader")
    public String getPage() {
        return "loader";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println(PasswordManager.getMD5("test123"));
        return "<img src='images/music/lalala'></img>";
    }

    @GetMapping("/loader/genres")
    @ResponseBody
    public List<String> getGenres() {
        try {
            List<String> genres = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().getAllGenres();
            return genres;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @PostMapping("/loader/upalbum")
    @ResponseBody
    public Integer uploadAlbums(@RequestBody Album al) {
        int id = -1;
        System.out.println(al.getGenre().get(0));
        System.out.println(al.getReleaseDate());
        al.setUser("revediaTest");
        try {
            id = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().insertAlbum(al);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return id;
    }

    @PostMapping("/loader/upalbum/img")
    @ResponseBody
    public String uploadImage(@RequestParam("id")String id, @RequestParam("url")String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            BufferedImage image = ImageIO.read(url);
            ImageIO.write(image, "jpg",new File("src/main/webapp/images/music/" + id + ".jpg"));
            return "ok";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/loader/upsong")
    @ResponseBody
    public String uploadSong(@RequestBody Song s) {
        System.out.println(s.getName());
        try {
            DatabaseManager.getIstance().getDaoFactory().getSongJDBC().insertSong(s, "revediaTest");
            return "ok";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
