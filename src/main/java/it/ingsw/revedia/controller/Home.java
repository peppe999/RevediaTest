package it.ingsw.revedia.controller;

import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.daoInterfaces.BookDao;
import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.Book;
import it.ingsw.revedia.model.Movie;
import it.ingsw.revedia.model.Song;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.sql.SQLException;

@Controller
public class Home
{
    @GetMapping("/")
    public ModelAndView getHome()
    {
        ModelAndView model = new ModelAndView("index");

        try
        {
            AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();
            Album albumCarousel = albumDao.getRandomAlbumsByConditions(1,true).get(0);
            model.addObject("albumName", albumCarousel.getName());
            model.addObject("albumUser",albumCarousel.getUser());

            SongDao songDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();
            Song song = songDao.getRandomSongsByConditions(1,true).get(0);
            model.addObject("songName",song.getName());
            model.addObject("songUser",song.getUser());

            MovieDao movieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();
            Movie movie = movieDao.getRandomMoviesByConditions(1,true).get(0);
            model.addObject("movieTitle",movie.getTitle());
            model.addObject("movieUser",movie.getUser());

            BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();
            Book book = new Book(); //bookDao.getRandomBooksByConditions(1,true).get(0);
            //non ci sono ancora libri nel db
            book.setTitle("Banana 33");
            book.setUser("Rocco");
            model.addObject("bookTitle",book.getTitle());
            model.addObject("bookUser",book.getUser());
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return model;
    }
}
