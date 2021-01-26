package it.ingsw.revedia.controller;

import it.ingsw.revedia.daoInterfaces.AlbumDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.model.Album;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

public class Home
{
    @GetMapping("/")
    public ModelAndView getHome()
    {
        ModelAndView model = new ModelAndView("index");

        AlbumDao albumDao = DatabaseManager.getIstance().getDaoFactory().getAlbumJDBC();
        try
        {
            Album albumCarousel = albumDao.getRandomAlbumsByConditions(1,true).get(0);
            model.addObject("albumCarousel", albumCarousel);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }



        return model;
    }
}
