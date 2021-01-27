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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class Home
{
    @GetMapping("/")
    public ModelAndView getHome(HttpServletRequest request)
    {
        ModelAndView model = new ModelAndView("index");

        try
        {
            AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();
            Album albumCarousel = albumDao.getRandomAlbumsByConditions(1,true).get(0);
            model.addObject("albumCarousel", albumCarousel);

            SongDao songDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();
            Song songCarousel = songDao.getRandomSongsByConditions(1,true).get(0);
            model.addObject("songCarousel", songCarousel);

            MovieDao movieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();
            Movie movieCarousel = movieDao.getRandomMoviesByConditions(1,true).get(0);
            model.addObject("movieCarousel", movieCarousel);

            BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();
            Book bookCarousel = new Book();
            bookCarousel.setTitle("Banana 33");
            bookCarousel.setUser("Rocco");
            model.addObject("bookCarousel", bookCarousel);


            ArrayList<Song> songs = songDao.getRandomSongsByConditions(8,false);
            model.addObject("songs",songs);

            ArrayList<Album> albums = albumDao.getRandomAlbumsByConditions(6,false);
            model.addObject("albums",albums);

            ArrayList<Movie> movies = movieDao.getRandomMoviesByConditions(4,false);
            model.addObject("movies",movies);

            ArrayList<Book> books = bookDao.getRandomBooksByConditions(6,false);
            model.addObject("books",books);

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
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return model;
    }

    @GetMapping("/logout")
    public String logOut(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);

        if(session != null)
            session.invalidate();

        return "redirect:/";
    }
}
