package it.ingsw.revedia.controller;

import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.AlbumReview;
import it.ingsw.revedia.model.Movie;
import it.ingsw.revedia.model.MovieReview;
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
public class MovieController {

    @GetMapping("movies/movie")
    public ModelAndView getMovie(@RequestParam("title") String movietitle, HttpServletRequest request) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("moviePage");
        MovieDao movieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();
        Movie movie = movieDao.findByPrimaryKey(movietitle);
        ArrayList<MovieReview> reviews = movieDao.getReviews(movietitle, 0);

        modelAndView.addObject("reviews",reviews);
        modelAndView.addObject("movie", movie);

        HttpSession session = request.getSession();
        session.setAttribute("movietitle", movietitle);
        if(session.getAttribute("nickname") != null){
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

    @PostMapping("/sendmoviereview")
    public  ModelAndView sendMovieReview(HttpServletRequest request, @RequestParam("reviewinput") String review){
        ModelAndView modelAndView = new ModelAndView();

        HttpSession session = request.getSession();
        if(session.getAttribute("nickname") != null)
        {
            User user = new User();
            user.setNickname(session.getAttribute("nickname").toString());
            user.setPermissions(Permissions.valueOf(session.getAttribute("permissions").toString()));
            MovieReview movieReview = new MovieReview();
            movieReview.setUser(user.getNickname());
            String idMovie = session.getAttribute("movietitle").toString();
            movieReview.setMovie(idMovie);
            movieReview.setDescription(review);
            movieReview.setNumberOfStars((short) 3);
            try {
                DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().addReview(movieReview);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            modelAndView.setViewName("redirect:/more?movietitle="+ idMovie );
        }
        else
        {

            modelAndView.setViewName("redirect:/Login");
        }

        return  modelAndView;
    }


}
