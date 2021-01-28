package it.ingsw.revedia.controller;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.PasswordManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
public class UserPageController {

    @GetMapping("/user")
    public String getUserPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if(session.getAttribute("nickname") == null)
            return "redirect:/";

        try {
            User user = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getUser((String) session.getAttribute("nickname"));
            model.addAttribute("user", user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "userpage";
    }

    @PostMapping("/user/avgquality")
    @ResponseBody
    public Float getUserAvgQuality(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        Float value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getAvgQuality((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    @PostMapping("/user/avgrating")
    @ResponseBody
    public Float getUserAvgRating(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        Float value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getAvgRating((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    @PostMapping("/user/numratedreviews")
    @ResponseBody
    public Integer getUserNumRatedReviews(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        Integer value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getNumRatedReviews((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    @PostMapping("/user/bestreview")
    @ResponseBody
    public String getBestReview(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        String value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getBestReview((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    @PostMapping("/user/favcat")
    @ResponseBody
    public String getFavCat(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        String value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getFavouriteCat((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    @PostMapping("/user/numreviews")
    @ResponseBody
    public Map<String, Object> getUserNumReviews(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        Map<String, Object> value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getNumReviews((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    @PostMapping("/user/favgenrecat")
    @ResponseBody
    public Map<String, String> getFavGenreCat(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        Map<String, String> value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getFavouriteGenreForCat((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    @PostMapping("/user/contributeDay")
    @ResponseBody
    public Map<String, List<Object>> getContributeDay(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        Map<String, List<Object>> value = null;
        try {
            value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getContributeForDay((String)session.getAttribute("nickname"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    @PostMapping("/user/editData")
    @ResponseBody
    public String editData(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                           @RequestParam("mail") String mail, @RequestParam("psw") String psw, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        try {
            if(!DatabaseManager.getIstance().getDaoFactory().getUserJDBC().validateLogin(PasswordManager.getMD5(psw), (String)session.getAttribute("nickname")))
                return "La password non è corretta";

            User user = new User((String)session.getAttribute("nickname"), firstName, lastName, mail);
            DatabaseManager.getIstance().getDaoFactory().getUserJDBC().updateUser(user);
            return "ok";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }
}