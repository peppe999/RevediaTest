package it.ingsw.revedia.controller;

import it.ingsw.revedia.daoInterfaces.UserDao;
import it.ingsw.revedia.database.DatabaseManager;
import it.ingsw.revedia.jdbcModels.UserJDBC;
import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.EmailManager;
import it.ingsw.revedia.utilities.GoogleManager;
import it.ingsw.revedia.utilities.PasswordManager;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class Signup
{
    @GetMapping("/signup")
    public String signup(HttpServletRequest request, Model m)
    {
        if(request.getSession().getAttribute("nickname") != null)
            return "redirect:/";

        m.addAttribute("usernotavailable", "none");
        m.addAttribute("errorMsg", "");

        return "signup";
    }

    @PostMapping("/register")
    public ModelAndView register(HttpServletRequest request, @RequestParam("username") String username,
                                 @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,
                                 @RequestParam("password") String password, @RequestParam("mail") String mail)
    {
        ModelAndView model = new ModelAndView();

        if(username.startsWith("gUser")) {
            model.setViewName("signup");
            model.addObject("usernotavailable", "block");
            model.addObject("errorMsg", "Nome utente riservato, riprova");
            return model;
        }

        User user = new User();
        user.setNickname(username);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setMail(mail);
        user.setPermissions(Permissions.STANDARD);


        String MD5Password = PasswordManager.getMD5(password);
        try
        {
            DatabaseManager.getIstance().getDaoFactory().getUserJDBC().insertUser(user, MD5Password);
            EmailManager.registrationConfirm(user.getMail(), user.getNickname());
            HttpSession session = request.getSession();
            session = request.getSession(true);
            session.setAttribute("nickname", user.getNickname());
            session.setAttribute("mail",user.getMail());
            session.setAttribute("firstname",user.getFirstName());
            session.setAttribute("lastname",user.getLastName());
            session.setAttribute("permissions",user.getPermissions().toString());

            model.setViewName("redirect:/");
        }
        catch (SQLException throwables)
        {
            //throwables.printStackTrace();
            model.setViewName("signup");
            model.addObject("usernotavailable", "block");
            model.addObject("errorMsg", "Nome utente non disponibile o email già utilizzata, riprova");
        }

        return model;
    }

    @PostMapping("/googleLogin")
    public ModelAndView getGoogleData(HttpServletRequest request, @RequestParam("idtoken") String idTokenString) {

        User user = GoogleManager.validateToken(idTokenString);
        if(user != null) {

            UserDao userDao = DatabaseManager.getIstance().getDaoFactory().getUserJDBC();
            try {
                ModelAndView model = new ModelAndView("redirect:/");
                User dbUser = userDao.getUserByNicknameOrMail("", user.getMail());

                if(dbUser == null) {
                    String psw = PasswordManager.generatePassword();
                    Integer googleId = userDao.getNextGoogleIdValue();

                    user.setNickname("gUser" + googleId);
                    user.setPermissions(Permissions.STANDARD);
                    DatabaseManager.getIstance().getDaoFactory().getUserJDBC().insertUser(user, PasswordManager.getMD5(psw));

                    EmailManager.googleRegistrationConfirm(user.getMail(), user.getNickname(), psw);
                }
                else {
                    user = dbUser;
                }

                HttpSession session = request.getSession();
                session = request.getSession(true);
                session.setAttribute("nickname", user.getNickname());
                session.setAttribute("mail",user.getMail());
                session.setAttribute("firstname",user.getFirstName());
                session.setAttribute("lastname",user.getLastName());
                session.setAttribute("permissions",user.getPermissions().toString());

                return model;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                ModelAndView model = new ModelAndView("redirect:/Login");
                return model;
            }
        }
        else {
            ModelAndView model = new ModelAndView("redirect:/Login");
            return model;
        }
    }

}
