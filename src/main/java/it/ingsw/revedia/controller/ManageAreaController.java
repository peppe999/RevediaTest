package it.ingsw.revedia.controller;

import it.ingsw.revedia.daoInterfaces.MovieDao;
import it.ingsw.revedia.daoInterfaces.SongDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

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

    @PostMapping("/manage/explore/numberOfContents")
    @ResponseBody
    public Integer getNumberOfContents(@RequestParam("query") String query, @RequestParam("type") Integer type, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        Integer value = null;

        switch (type) {
            case 0:
                value = getNumAlbums(query, session.getAttribute("nickname").toString());
                break;
            case 1:
                value = getNumSongs(query, session.getAttribute("nickname").toString());
                break;
            case 2:
                value = getNumMovies(query, session.getAttribute("nickname").toString());
                break;
            case 3:
                value = getNumBooks(query, session.getAttribute("nickname").toString());
                break;
        }

        if(value == 0)
            return 1;

        Integer numberOfPages = value / 20;
        if(value % 20 != 0)
            numberOfPages++;

        return numberOfPages;
    }

    @PostMapping("/manage/book/explore")
    @ResponseBody
    public List<Book> getUserBooks(@RequestParam("query") String query, @RequestParam("page") Integer page, @RequestParam("modality") Integer modality,
                                          @RequestParam("order") Integer order, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        List<Book> books = null;

        if(query.equals("")) {
            try {
                books = DatabaseManager.getIstance().getDaoFactory().getBookJDBC().searchByUser(session.getAttribute("nickname").toString(), (page - 1) * 20, modality, order);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            try {
                books = DatabaseManager.getIstance().getDaoFactory().getBookJDBC().searchByUserWithKeyWords(session.getAttribute("nickname").toString(), getQueryList(query), (page - 1) * 20, modality, order);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return books;
    }

    @PostMapping("/manage/movie/explore")
    @ResponseBody
    public List<Movie> getUserMovies(@RequestParam("query") String query, @RequestParam("page") Integer page, @RequestParam("modality") Integer modality,
                                    @RequestParam("order") Integer order, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        List<Movie> movies = null;

        if(query.equals("")) {
            try {
                movies = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().searchByUser(session.getAttribute("nickname").toString(), (page - 1) * 20, modality, order);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            try {
                movies = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().searchByUserWithKeyWords(session.getAttribute("nickname").toString(), getQueryList(query), (page - 1) * 20, modality, order);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return movies;
    }

    @PostMapping("/manage/song/explore")
    @ResponseBody
    public List<Song> getUserSongs(@RequestParam("query") String query, @RequestParam("page") Integer page, @RequestParam("modality") Integer modality,
                                    @RequestParam("order") Integer order, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        List<Song> songs = null;

        if(query.equals("")) {
            try {
                songs = DatabaseManager.getIstance().getDaoFactory().getSongJDBC().searchByUser(session.getAttribute("nickname").toString(), (page - 1) * 20, modality, order);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            try {
                songs = DatabaseManager.getIstance().getDaoFactory().getSongJDBC().searchByUserWithKeyWords(session.getAttribute("nickname").toString(), getQueryList(query), (page - 1) * 20, modality, order);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return songs;
    }

    @PostMapping("/manage/album/explore")
    @ResponseBody
    public List<Album> getUserAlbums(@RequestParam("query") String query, @RequestParam("page") Integer page, @RequestParam("modality") Integer modality,
                                    @RequestParam("order") Integer order, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        List<Album> albums = null;

        if(query.equals("")) {
            try {
                albums = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().searchByUser(session.getAttribute("nickname").toString(), (page - 1) * 20, modality, order);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            try {
                albums = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().searchByUserWithKeyWords(session.getAttribute("nickname").toString(), getQueryList(query), (page - 1) * 20, modality, order);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return albums;
    }

    @PostMapping("/manage/getgenres")
    @ResponseBody
    public List<String> getGenres(@RequestParam("type") String type, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        List<String> genres = null;

        try {
            if(type.equals("music"))
                genres = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().getAllGenres();
            else if(type.equals("movie"))
                genres = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().getAllGenres();
            else if(type.equals("book"))
                genres = DatabaseManager.getIstance().getDaoFactory().getBookJDBC().getAllGenres();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return genres;
    }

    @PostMapping("/manage/upload/album")
    @ResponseBody
    public Integer uploadAlbum(@RequestBody Album album, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        System.out.println(album.getName());

        return -1;
    }

    @PostMapping("/manage/upload/songs")
    @ResponseBody
    public Integer uploadSongs(@RequestBody List<Song> songs, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        try {
            for(Song s : songs) {
                SongDao songDao = DatabaseManager.getIstance().getDaoFactory().getSongJDBC();
                Song dbSong = songDao.findByPrimaryKey(s.getName(), s.getAlbumID());
                if(dbSong == null) {
                    songDao.insertSong(s, session.getAttribute("nickname").toString());
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 1;
    }

    @PostMapping("/manage/upload/movie")
    @ResponseBody
    public Integer uploadMovie(@RequestBody Movie movie, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;

        try {
            MovieDao movieDao = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC();
            Movie dbMovie = movieDao.findByPrimaryKey(movie.getTitle());

            if(dbMovie == null) {
                Integer id = movieDao.insertMovie(movie);
                return id;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1;
    }

    @PostMapping("/manage/upload/book")
    @ResponseBody
    public Integer uploadBook(@RequestBody Book book, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;

        if(!session.getAttribute("permissions").toString().equals("EDITOR"))
            return null;



        return 1;
    }

    @PostMapping("/manage/upload/spotify/img")
    @ResponseBody
    public String uploadMusicImage(@RequestParam("id")String id, @RequestParam("url")String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            BufferedImage image = ImageIO.read(url);
            ImageIO.write(image, "jpg",new File("src/main/webapp/images/music/" + id + ".jpg"));
            return "ok";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/manage/upload/tmdb/img")
    @ResponseBody
    public String uploadMovieImage(@RequestParam("id")String id, @RequestParam("url")String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            BufferedImage image = ImageIO.read(url);
            ImageIO.write(image, "jpg",new File("src/main/webapp/images/movies/" + id + ".jpg"));
            return "ok";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/manage/upload/gbooks/img")
    @ResponseBody
    public String uploadBookImage(@RequestParam("id")String id, @RequestParam("url")String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            BufferedImage image = ImageIO.read(url);
            ImageIO.write(image, "jpg",new File("src/main/webapp/images/books/" + id + ".jpg"));
            return "ok";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String[] getQueryList(String plainQuery) {
        plainQuery.replaceAll("'", " ");
        String[] tokens = plainQuery.split(" ");

        return tokens;
    }

    private Integer getNumBooks(String query, String nickname) {
        Integer value = 0;
        if(query.equals("")) {
            try {
                value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getNumLoadedBooks(nickname);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            try {
                value = DatabaseManager.getIstance().getDaoFactory().getBookJDBC().getUserCountWithKeyWords(nickname, getQueryList(query));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return value;
    }

    private Integer getNumMovies(String query, String nickname) {
        Integer value = 0;
        if(query.equals("")) {
            try {
                value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getNumLoadedMovies(nickname);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            try {
                value = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().getUserCountWithKeyWords(nickname, getQueryList(query));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return value;
    }

    private Integer getNumSongs(String query, String nickname) {
        Integer value = 0;
        if(query.equals("")) {
            try {
                value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getNumLoadedSongs(nickname);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            try {
                value = DatabaseManager.getIstance().getDaoFactory().getSongJDBC().getUserCountWithKeyWords(nickname, getQueryList(query));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return value;
    }

    private Integer getNumAlbums(String query, String nickname) {
        Integer value = 0;
        if(query.equals("")) {
            try {
                value = DatabaseManager.getIstance().getDaoFactory().getUserJDBC().getNumLoadedAlbums(nickname);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            try {
                value = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().getUserCountWithKeyWords(nickname, getQueryList(query));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return value;
    }
}
