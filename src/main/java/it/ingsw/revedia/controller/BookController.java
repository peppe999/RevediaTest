package it.ingsw.revedia.controller;


import it.ingsw.revedia.daoInterfaces.BookDao;
import it.ingsw.revedia.daoInterfaces.MovieDao;
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
public class BookController {

    @GetMapping("/moreBook")
    public ModelAndView getBook(@RequestParam("title") String booktitle, HttpServletRequest request) throws SQLException {

        ModelAndView modelAndView = new ModelAndView("bookPage");
        BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();
        Book book = bookDao.findByPrimaryKey(booktitle);
        ArrayList<BookReview> reviews = bookDao.getReviews(booktitle);

        modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("book", book);

        HttpSession session = request.getSession();
        session.setAttribute("booktitle", booktitle);
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

    @PostMapping("/sendbookreview")
    public  ModelAndView sendMovieReview(HttpServletRequest request, @RequestParam("reviewinput") String review){
        ModelAndView modelAndView = new ModelAndView();

        HttpSession session = request.getSession();
        if(session.getAttribute("nickname") != null)
        {
            User user = new User();
            user.setNickname(session.getAttribute("nickname").toString());
            user.setPermissions(Permissions.valueOf(session.getAttribute("permissions").toString()));
            BookReview bookReview = new BookReview();
            bookReview.setUser(user.getNickname());
            String idBook = session.getAttribute("booktitle").toString();
            bookReview.setBook(idBook);
            bookReview.setDescription(review);
            bookReview.setNumberOfStars((short) 3);
            try {
                DatabaseManager.getIstance().getDaoFactory().getBookJDBC().addReview(bookReview);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            modelAndView.setViewName("redirect:/more?booktitle="+ idBook );
        }
        else
        {

            modelAndView.setViewName("redirect:/Login");
        }

        return  modelAndView;
    }

}
