package it.ingsw.revedia.controller;


import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.*;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        ArrayList<SongReview> reviews = songDao.getReviews(songtitle, songid, 0);


        modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("song", song);

        HttpSession session = request.getSession();
        session.setAttribute("songtitle", songtitle);
       // session.setAttribute("songid", songid);
        if (session.getAttribute("nickname") != null) {
            User user = new User();
            user.setNickname(session.getAttribute("nickname").toString());
            user.setPermissions(Permissions.valueOf(session.getAttribute("permissions").toString()));
            modelAndView.addObject("user", user);
            modelAndView.addObject("hideuser", "");
            modelAndView.addObject("signupbutton", "display: none");
        } else {
            modelAndView.addObject("hideuser", "display: none");
            modelAndView.addObject("signupbutton", " ");
        }

        return modelAndView;

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
