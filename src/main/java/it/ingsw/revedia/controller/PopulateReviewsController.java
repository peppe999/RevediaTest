package it.ingsw.revedia.controller;

import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
public class PopulateReviewsController {

    public List<AlbumReview> generateAlbumReviews() {
        List<AlbumReview> reviews = new ArrayList<>();

        AlbumReview r = new AlbumReview();
        r.setDescription("Questo album descrive perfettamente tutto ciò che amo ascoltare. Se potessi farlo, metterei anche più di 5 stelle");
        r.setNumberOfStars((short)5);
        reviews.add(r);

        r = new AlbumReview();
        r.setDescription("Ha un ottimo potenziale ed è molto orecchiabile, ma mi aspettavo un po' di più");
        r.setNumberOfStars((short)3);
        reviews.add(r);

        r = new AlbumReview();
        r.setDescription("Un album che mi è piaciuto davvero molto. Peccato solo per qualche dettaglio. Ad ogni modo è sicuramente da ascoltare");
        r.setNumberOfStars((short)4);
        reviews.add(r);

        r = new AlbumReview();
        r.setDescription("Carino in alcuni punti, ma nel complesso non mi è piaciuto un granchè");
        r.setNumberOfStars((short)2);
        reviews.add(r);

        r = new AlbumReview();
        r.setDescription("Ascoltarlo è stata una perdita di tempo. Gusti eh, ma personalmente è da scartare");
        r.setNumberOfStars((short)1);
        reviews.add(r);

        return reviews;
    }

    private List<SongReview> generateSongReviews() {
        List<SongReview> reviews = new ArrayList<>();

        SongReview r = new SongReview();
        r.setDescription("Questo brano è a dir poco perfetto, lo ascolto di continuo");
        r.setNumberOfStars((short)5);
        reviews.add(r);

        r = new SongReview();
        r.setDescription("Un bel brano, ben strutturato e orecchiabile. Però non mi convince del tutto");
        r.setNumberOfStars((short)3);
        reviews.add(r);

        r = new SongReview();
        r.setDescription("Qualcosa di cui lamentarmi? La durata. Ma wow, per il resto è davvero un brano bellissimo");
        r.setNumberOfStars((short)4);
        reviews.add(r);

        r = new SongReview();
        r.setDescription("Non ci siamo, è quasi difficile ascoltarlo. Si salva solo in alcuni passaggi, ed è per questo che non valuto con il minimo");
        r.setNumberOfStars((short)2);
        reviews.add(r);

        r = new SongReview();
        r.setDescription("Personalmente è uno dei brani peggiori che mi sia capitato di ascoltare, non lo consiglio affatto. Preservate il vostro udito");
        r.setNumberOfStars((short)1);
        reviews.add(r);

        return reviews;
    }

    private List<MovieReview> generateMovieReviews() {
        List<MovieReview> reviews = new ArrayList<>();

        MovieReview r = new MovieReview();
        r.setDescription("Questo film è un capolavoro. Da guardare e riguardare ancora");
        r.setNumberOfStars((short)5);
        reviews.add(r);

        r = new MovieReview();
        r.setDescription("Un film guardabile, alcune scene sono belle, ma nel complesso è mediocre");
        r.setNumberOfStars((short)3);
        reviews.add(r);

        r = new MovieReview();
        r.setDescription("Un ottimo film, il tempo vola. Peccato però per la qualità delle riprese, si poteva fare meglio");
        r.setNumberOfStars((short)4);
        reviews.add(r);

        r = new MovieReview();
        r.setDescription("La trama è interessante, ma il casting scelto ha reso tutto una vera noia.");
        r.setNumberOfStars((short)2);
        reviews.add(r);

        r = new MovieReview();
        r.setDescription("Un vero disastro, gli attori hanno recitato malissimo e il doppiaggio è pessimo.");
        r.setNumberOfStars((short)1);
        reviews.add(r);

        return reviews;
    }

    private List<BookReview> generateBookReviews() {
        List<BookReview> reviews = new ArrayList<>();

        BookReview r = new BookReview();
        r.setDescription("Questo libro è spettacolare, l'ho letto tutto di fila");
        r.setNumberOfStars((short)5);
        reviews.add(r);

        r = new BookReview();
        r.setDescription("Un libro carino, mi è piaciuto. Però la trama ha diversi buchi");
        r.setNumberOfStars((short)3);
        reviews.add(r);

        r = new BookReview();
        r.setDescription("Un libro davvero bello. Peccato per il finale");
        r.setNumberOfStars((short)4);
        reviews.add(r);

        r = new BookReview();
        r.setDescription("Una delle letture più noiose di sempre, si salvano alcuni passaggi, ma non lo consiglio");
        r.setNumberOfStars((short)2);
        reviews.add(r);

        r = new BookReview();
        r.setDescription("Un libro pessimo, sconsiglio assolutamente di leggerlo");
        r.setNumberOfStars((short)1);
        reviews.add(r);

        return reviews;
    }

    @GetMapping("/loaderReviews/albums")
    @ResponseBody
    public String loadAlbumsReviews() {
        /*List<String> users = Arrays.asList("revediaTest");
        List<AlbumReview> reviews = generateAlbumReviews();
        Random random = new Random();
        try {
            List<Album> albums = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().findAll();
            for(String user : users) {
                for(Album album : albums) {
                    boolean upload = random.nextBoolean();
                    if(upload) {
                        int index = random.nextInt(reviews.size());
                        AlbumReview review = reviews.get(index);
                        review.setUser(user);
                        review.setAlbumId(album.getId());

                        DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().addReview(review);
                        System.out.println(album.getId());
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

        try {
            DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().upsertAlbumReview("revediaTest", 6, "revediaTest", true);
            DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().upsertAlbumReview("revediaTest", 6, "revediaTest", false);
            List<AlbumReview> albumReviews = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC().getReviewsByUserRater(6, "revediaTest", 0);
            for(AlbumReview rev : albumReviews) {
                if(rev.getActualUserRate() == null)
                    System.out.println("nullo");
                else
                    System.out.println("non nullo");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "finito";
    }

    @GetMapping("/loaderReviews/songs")
    @ResponseBody
    public String loadSongsReviews() {
        /*List<String> users = Arrays.asList("revediaTest");
        List<SongReview> reviews = generateSongReviews();
        Random random = new Random();
        try {
            List<Song> songs = DatabaseManager.getIstance().getDaoFactory().getSongJDBC().findAll();
            for(String user : users) {
                for(Song song : songs) {
                    int upload = random.nextInt(5);
                    if(upload == 1) {
                        int index = random.nextInt(reviews.size());
                        SongReview review = reviews.get(index);
                        review.setUser(user);
                        review.setAlbumId(song.getAlbumID());
                        review.setSongName(song.getName());

                        DatabaseManager.getIstance().getDaoFactory().getSongJDBC().addReview(review);
                        System.out.println(song.getName());
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

        try {
            //DatabaseManager.getIstance().getDaoFactory().getSongJDBC().upsertSongReview("revediaTest", "120", 547, "revediaTest", true);
            //DatabaseManager.getIstance().getDaoFactory().getSongJDBC().upsertSongReview("revediaTest", "120", 547, "revediaTest", false);
            List<SongReview> songReviews = DatabaseManager.getIstance().getDaoFactory().getSongJDBC().getReviewsByUserRater("120", 547,"revediaTest", 0);
            for(SongReview rev : songReviews) {
                if(rev.getActualUserRate() == null)
                    System.out.println("nullo");
                else
                    System.out.println("non nullo");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "finito";
    }

    @GetMapping("/loaderReviews/movies")
    @ResponseBody
    public String loadMoviesReviews() {
        /*List<String> users = Arrays.asList("revediaTest");
        List<MovieReview> reviews = generateMovieReviews();
        Random random = new Random();
        try {
            List<Movie> movies = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().findAll();
            for(String user : users) {
                for(Movie movie : movies) {
                    int upload = random.nextInt(3);
                    if(upload == 1) {
                        int index = random.nextInt(reviews.size());
                        MovieReview review = reviews.get(index);
                        review.setUser(user);
                        review.setMovie(movie.getTitle());

                        DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().addReview(review);
                        System.out.println(movie.getTitle());
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

        try {
            DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().upsertMovieReview("revediaTest", "Tenet", "revediaTest", true);
            DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().upsertMovieReview("revediaTest", "Tenet", "revediaTest", false);
            List<MovieReview> movieReviews = DatabaseManager.getIstance().getDaoFactory().getMovieJDBC().getReviewsByUserRater("Tenet", "revediaTest", 0);
            for(MovieReview rev : movieReviews) {
                if(rev.getActualUserRate() == null)
                    System.out.println("nullo");
                else
                    System.out.println("non nullo");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "finito";
    }

    @GetMapping("/loaderReviews/books")
    @ResponseBody
    public String loadBooksReviews() {
       /* List<String> users = Arrays.asList("revediaTest");
        List<BookReview> reviews = generateBookReviews();
        Random random = new Random();
        try {
            List<Book> books = DatabaseManager.getIstance().getDaoFactory().getBookJDBC().findAll();
            for(String user : users) {
                for(Book book : books) {
                    int upload = random.nextInt(4);
                    if(upload == 1) {
                        int index = random.nextInt(reviews.size());
                        BookReview review = reviews.get(index);
                        review.setUser(user);
                        review.setBook(book.getTitle());

                        DatabaseManager.getIstance().getDaoFactory().getBookJDBC().addReview(review);
                        System.out.println(book.getTitle());
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

        try {
            DatabaseManager.getIstance().getDaoFactory().getBookJDBC().upsertBookReview("revediaTest", "Full Tilt", "revediaTest", true);
            DatabaseManager.getIstance().getDaoFactory().getBookJDBC().upsertBookReview("revediaTest", "Full Tilt", "revediaTest", false);
            List<BookReview> bookReviews = DatabaseManager.getIstance().getDaoFactory().getBookJDBC().getReviewsByUserRater("Full Tilt", "revediaTest", 0);
            for(BookReview rev : bookReviews) {
                if(rev.getActualUserRate() == null)
                    System.out.println("nullo");
                else
                    System.out.println("non nullo");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "finito";
    }
}
