package it.ingsw.revedia.controller;


import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.*;
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
public class SongController {

    @GetMapping("/music/song")
    public ModelAndView getSong(@RequestParam("name") String songtitle, @RequestParam("album") int songid, HttpServletRequest request) throws SQLException {

        ModelAndView modelAndView = new ModelAndView("songPage");
        SongDao songDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();
        Song song = songDao.findByPrimaryKey(songtitle, songid);
        //ArrayList<SongReview> reviews = songDao.getReviews(songtitle, songid, 0);


        //modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("song", song);

        HttpSession session = request.getSession();
        session.setAttribute("songtitle", songtitle);
       // session.setAttribute("songid", songid);

        boolean logged = false;

        if (session.getAttribute("nickname") != null) {
            logged = true;
            String nickname = session.getAttribute("nickname").toString();
            SongReview review = songDao.getUserReview(songtitle, songid, nickname);
            User user = new User();
            user.setNickname(session.getAttribute("nickname").toString());
            user.setPermissions(Permissions.valueOf(session.getAttribute("permissions").toString()));
            modelAndView.addObject("user", user);
            modelAndView.addObject("hideuser", "");
            modelAndView.addObject("signupbutton", "display: none");
            modelAndView.addObject("myreview", review);
        } else {
            modelAndView.addObject("hideuser", "display: none");
            modelAndView.addObject("signupbutton", " ");
        }

        modelAndView.addObject("logged", logged);

        return modelAndView;

    }


    @PostMapping("/music/song")
    public ModelAndView getReviews(@RequestParam("name") String songtitle, @RequestParam("album") int songid, @RequestParam("offset") Integer offset, HttpServletRequest request) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("songReviews");
        SongDao songDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();
        ArrayList<SongReview> reviews;

        HttpSession session = request.getSession();
        boolean logged = false;

        if(session.getAttribute("nickname") != null) {
            logged = true;
            String nickname = session.getAttribute("nickname").toString();
            reviews = songDao.getReviewsByUserRater(songtitle, songid, nickname, offset);
        }
        else
            reviews = songDao.getReviews(songtitle, songid, offset);

        modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("logged", logged);

        return modelAndView;
    }



    @PostMapping("/music/song/rateReview")
    @ResponseBody
    public Boolean rateReview( @RequestParam("name") String name, @RequestParam("album") int songid, @RequestParam("user") String user, @RequestParam("rating") Boolean rating, HttpServletRequest request) {
        HttpSession session = request.getSession();

        if(session.getAttribute("nickname") != null) {
            String loggedUser = session.getAttribute("nickname").toString();

            try {
                DatabaseManager.getIstance().getDaoFactory().getSongJDBC().upsertSongReview(user, name, songid, loggedUser, rating);
                return true;
            } catch (SQLException throwables) {
                return false;
            }
        }

        return false;
    }




    @PostMapping("/sendsongreview")
    public  ModelAndView sendMovieReview(HttpServletRequest request, @RequestParam("reviewinput") String review){
        ModelAndView modelAndView = new ModelAndView();

        HttpSession session = request.getSession();
        if(session.getAttribute("nickname") != null)
        {
            User user = new User();
            user.setNickname(session.getAttribute("nickname").toString());
            user.setPermissions(Permissions.valueOf(session.getAttribute("permissions").toString()));
            SongReview songReview = new SongReview();
            songReview.setUser(user.getNickname());
            int idSong = Integer.parseInt(session.getAttribute("albumid").toString());
            String songtitle = session.getAttribute("songtitle").toString();
            songReview.setSongName(songtitle);
            songReview.setAlbumId(idSong);
            songReview.setDescription(review);
            songReview.setNumberOfStars((short) 3);
            try {
                DatabaseManager.getIstance().getDaoFactory().getSongJDBC().addReview(songReview);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            modelAndView.setViewName("redirect:/more?songtitle="+ songtitle+"albumid="+idSong );
        }
        else
        {

            modelAndView.setViewName("redirect:/Login");
        }

        return  modelAndView;
    }

}
