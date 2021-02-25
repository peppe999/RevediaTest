package it.ingsw.revedia.controller;


import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.daoInterfaces.BookDao;
import it.ingsw.revedia.daoInterfaces.MovieDao;
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
public class BookController {

    @GetMapping("books/book")
    public ModelAndView getBook(@RequestParam("title") String booktitle, HttpServletRequest request) throws SQLException {

        ModelAndView modelAndView = new ModelAndView("bookPage");
        BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();
        Book book = bookDao.findByPrimaryKey(booktitle);
        ArrayList<BookReview> reviews = bookDao.getReviews(booktitle, 0);

        modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("book", book);

        HttpSession session = request.getSession();
        session.setAttribute("booktitle", booktitle);

        boolean logged = false;

        if (session.getAttribute("nickname") != null) {
            logged = true;
            String nickname = session.getAttribute("nickname").toString();
            BookReview review = bookDao.getUserReview(booktitle , nickname);
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


    @PostMapping("/books/book")
    public ModelAndView getReviews(@RequestParam("title") String booktitle, @RequestParam("offset") Integer offset, HttpServletRequest request) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("bookReviews");
        BookDao bookDao = DatabaseManager.getIstance().getDaoFactory().getBookJDBC();
        ArrayList<BookReview> reviews;

        HttpSession session = request.getSession();
        boolean logged = false;

        if(session.getAttribute("nickname") != null) {
            logged = true;
            String nickname = session.getAttribute("nickname").toString();
            reviews = bookDao.getReviewsByUserRater(booktitle, nickname, offset);
        }
        else
            reviews = bookDao.getReviews(booktitle, offset);

        modelAndView.addObject("reviews", reviews);

        modelAndView.addObject("logged", logged);

        return modelAndView;
    }

    @PostMapping("/books/book/rateReview")
    @ResponseBody
    public Boolean rateReview(@RequestParam("title") String booktitle, @RequestParam("user") String user, @RequestParam("rating") Boolean rating, HttpServletRequest request) {
        HttpSession session = request.getSession();

        if(session.getAttribute("nickname") != null) {
            String loggedUser = session.getAttribute("nickname").toString();

            try {
                DatabaseManager.getIstance().getDaoFactory().getBookJDBC().upsertBookReview(user, booktitle, loggedUser, rating);
                return true;
            } catch (SQLException throwables) {
                return false;
            }
        }

        return false;
    }

    @PostMapping("/books/book/sendreview")
    public ModelAndView sendBookReview(@RequestParam("title") String booktitle, @RequestParam("text") String text, @RequestParam("rating") Integer rating, HttpServletRequest request)  {

        ModelAndView modelAndView = new ModelAndView("myReview");

        HttpSession session = request.getSession();
        if(session.getAttribute("nickname") != null)
        {
            BookReview bookReview = new BookReview();
            bookReview.setUser(session.getAttribute("nickname").toString());
            bookReview.setBook(booktitle);
            bookReview.setDescription(text);
            bookReview.setNumberOfStars(rating.shortValue());
            try {
                DatabaseManager.getIstance().getDaoFactory().getBookJDBC().addReview(bookReview);
                modelAndView.addObject("myreview", bookReview);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return modelAndView;
    }
    /*public  ModelAndView sendMovieReview(HttpServletRequest request, @RequestParam("reviewinput") String review){
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
    }*/

}
