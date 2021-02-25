package it.ingsw.revedia.controller;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class ManageAreaController {

    @GetMapping("/manage")
    public String getManagePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        if(session.getAttribute("nickname") == null)
            return "redirect:/";

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return "redirect:/";

        try {
            User user = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getUser((String) session.getAttribute("nickname"));
            model.addAttribute("user", user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "managePage";
    }

    @PostMapping("/manage/albumsnum")
    @ResponseBody
    public Integer getLoadedAlbumsNum(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        Integer value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getNumLoadedAlbums((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return value;
    }

    @PostMapping("/manage/songsnum")
    @ResponseBody
    public Integer getLoadedSongsNum(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        Integer value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getNumLoadedSongs((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return value;
    }

    @PostMapping("/manage/moviesnum")
    @ResponseBody
    public Integer getLoadedMoviesNum(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        Integer value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getNumLoadedMovies((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return value;
    }

    @PostMapping("/manage/booksnum")
    @ResponseBody
    public Integer getLoadedBooksNum(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        Integer value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getNumLoadedBooks((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return value;
    }


}
