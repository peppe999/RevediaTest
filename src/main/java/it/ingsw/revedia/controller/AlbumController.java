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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class AlbumController {


    @GetMapping("/more")
    public ModelAndView getAlbum(@RequestParam("albumid") int albumid, HttpServletRequest request) throws SQLException {

        ModelAndView modelAndView = new ModelAndView("albumPage");
        AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();
        Album album = albumDao.findByPrimaryKey(albumid);
        ArrayList<Song> songs = albumDao.getSongs(albumid);
        ArrayList<AlbumReview>  reviews = albumDao.getReviews(albumid);
        ArrayList<String>  songsName = new ArrayList<>();

        float durata = 0;
        for(int i = 0; i < songs.size(); i++){
            songsName.add(songs.get(i).getName());
            durata += songs.get(i).getLength();
        }

        durata = (durata/1000)/60;

        modelAndView.addObject("durata", (int)durata);
        modelAndView.addObject("album", album);
        modelAndView.addObject("songs", songsName);
        modelAndView.addObject("reviews", reviews);

        HttpSession session = request.getSession();
        session.setAttribute("albumid", albumid);
        if(session.getAttribute("nickname") != null)
        {
            User user = new User();
            user.setNickname(session.getAttribute("nickname").toString());
            user.setPermissions(Permissions.valueOf(session.getAttribute("permissions").toString()));
            modelAndView.addObject("user",user);
            modelAndView.addObject("hideuser","");
            modelAndView.addObject("signupbutton","display: none");
        }
        else
        {
            modelAndView.addObject("hideuser","display: none");
            modelAndView.addObject("signupbutton"," ");
        }

        return modelAndView;


    }


    @PostMapping("/sendalbumreview")
    public ModelAndView sendAlbumReview(HttpServletRequest request, @RequestParam( "reviewinput") String review)  {

        ModelAndView modelAndView = new ModelAndView();

        HttpSession session = request.getSession();
        if(session.getAttribute("nickname") != null)
        {
            User user = new User();
            user.setNickname(session.getAttribute("nickname").toString());
            user.setPermissions(Permissions.valueOf(session.getAttribute("permissions").toString()));
            AlbumReview albumReview = new AlbumReview();
            albumReview.setUser(user.getNickname());
            int idAlbum = Integer.parseInt(session.getAttribute("albumid").toString());
            albumReview.setAlbumId(idAlbum);
            albumReview.setDescription(review);
            albumReview.setNumberOfStars((short) 3);
            try {
                DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().addReview(albumReview);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

           modelAndView.setViewName("redirect:/more?albumid="+ idAlbum );
        }
        else
        {

            modelAndView.setViewName("redirect:/Login");
        }

        return  modelAndView;
    }

}
