package it.ingsw.revedia.controller;


import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Album;
import it.ingsw.revedia.model.AlbumReview;
import it.ingsw.revedia.model.Song;
import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class AlbumController {

    @GetMapping("/music/album")
    public ModelAndView getAlbum(@RequestParam("id") Integer albumid, HttpServletRequest request) throws SQLException {

        ModelAndView modelAndView = new ModelAndView("albumPage");
        AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();
        Album album = albumDao.findByPrimaryKey(albumid);
        ArrayList<Song> songs = albumDao.getSongs(albumid);

        float durata = 0;
        for(int i = 0; i < songs.size(); i++){
            durata += songs.get(i).getLength();
        }

        durata = (durata/1000)/60;

        modelAndView.addObject("durata", (int)durata);
        modelAndView.addObject("album", album);
        modelAndView.addObject("songs", songs);

        HttpSession session = request.getSession();
        session.setAttribute("albumid", albumid);

        boolean logged = false;

        if(session.getAttribute("nickname") != null)
        {
            logged = true;
            String nickname = session.getAttribute("nickname").toString();
            AlbumReview review = albumDao.getUserReview(albumid, nickname);
            User user = new User();
            user.setNickname(nickname);
            user.setPermissions(Permissions.valueOf(session.getAttribute("permissions").toString()));
            modelAndView.addObject("user",user);
            modelAndView.addObject("hideuser","");
            modelAndView.addObject("signupbutton","display: none");
            modelAndView.addObject("myreview", review);
        }
        else
        {
            modelAndView.addObject("hideuser","display: none");
            modelAndView.addObject("signupbutton"," ");
        }

        modelAndView.addObject("logged", logged);



        return modelAndView;
    }

    @PostMapping("/music/album")
    public ModelAndView getReviews(@RequestParam("id") Integer albumid, @RequestParam("offset") Integer offset, HttpServletRequest request) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("albumReviews");
        AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();
        ArrayList<AlbumReview> reviews;

        HttpSession session = request.getSession();
        boolean logged = false;

        if(session.getAttribute("nickname") != null) {
            logged = true;
            String nickname = session.getAttribute("nickname").toString();
            reviews = albumDao.getReviewsByUserRater(albumid, nickname, offset);
        }
        else
            reviews = albumDao.getReviews(albumid, offset);

        modelAndView.addObject("reviews", reviews);

        modelAndView.addObject("logged", logged);

        return modelAndView;
    }

    @PostMapping("/music/album/rateReview")
    @ResponseBody
    public Boolean rateReview(@RequestParam("id") Integer albumid, @RequestParam("user") String user, @RequestParam("rating") Boolean rating, HttpServletRequest request) {
        HttpSession session = request.getSession();

        if(session.getAttribute("nickname") != null) {
            String loggedUser = session.getAttribute("nickname").toString();

            try {
                DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().upsertAlbumReview(user, albumid, loggedUser, rating);
                return true;
            } catch (SQLException throwables) {
                return false;
            }
        }

        return false;
    }




    // STO METODO QUI E' DA FARE!!!

    @PostMapping("/music/album/sendreview")
    public ModelAndView sendAlbumReview(@RequestParam("id") Integer albumid, @RequestParam("text") String text, @RequestParam("rating") Integer rating, HttpServletRequest request)  {

        ModelAndView modelAndView = new ModelAndView("myReview");

        HttpSession session = request.getSession();
        if(session.getAttribute("nickname") != null)
        {
            AlbumReview albumReview = new AlbumReview();
            albumReview.setUser(session.getAttribute("nickname").toString());
            albumReview.setAlbumId(albumid);
            albumReview.setDescription(text);
            albumReview.setNumberOfStars(rating.shortValue());
            try {
                DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().addReview(albumReview);
                modelAndView.addObject("myreview", albumReview);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return modelAndView;
    }

}
