package it.ingsw.revedia.controller;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.Movie;
import it.ingsw.revedia.model.Song;
import it.ingsw.revedia.utilities.PasswordManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PopulateFilmController {

    @GetMapping("/loaderFilm")
    public String getPage(Model m) {
        /*List<String> moviesNoCover = new ArrayList<>();
        try {
            List<Movie> movies = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().findAll();
            System.out.println("finito");
            for(Movie movie : movies) {
                File f = new File("src/main/webapp/images/movies/" + movie.getImageId() + ".jpg");
                if(!f.exists())
                    moviesNoCover.add(movie.getTitle());
            }
            for(String title : moviesNoCover) {
                DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().deleteMovie(title);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        /*try {
            List<Album> albums = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().findByGenre("Rock");
            System.out.println(albums.get(0).getName());
            Album al = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().findByPrimaryKey(albums.get(0).getId());
            System.out.println(al.getGenre().get(0) + " " + al.getReleaseDate());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        return "loaderFilm";
    }


    @PostMapping("/loaderFilm/upfilm")
    @ResponseBody
    public Integer uploadMovie(@RequestBody Movie mov) {
        System.out.println(mov.getGenres().get(0));
        System.out.println(mov.getTitle());
        mov.setUser("revediaTest");

        int id = -1;
        try {
            id = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().insertMovie(mov);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return id;
    }

    @PostMapping("/loaderFilm/upfilm/img")
    @ResponseBody
    public String uploadImage(@RequestParam("id")String id, @RequestParam("url")String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            BufferedImage image = ImageIO.read(url);
            ImageIO.write(image, "jpg",new File("src/main/webapp/images/movies/" + id + ".jpg"));
            return "ok";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/loaderFilm/upfilm/genres")
    @ResponseBody
    public String uploadGenres(@RequestBody List<String> genres) {
        for(String genre : genres) {
            try {
                DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().addGenre(genre);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println(genre);
        }

        return "oki";
    }

}
