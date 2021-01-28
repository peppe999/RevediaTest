package it.ingsw.revedia.controller;

import it.ingsw.revedia.model.User;
import it.ingsw.revedia.utilities.Permissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AboutUsController {

    @GetMapping("/aboutus")
    public String getAboutUsPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if(session.getAttribute("nickname") != null)
        {
            User user = new User();
            user.setNickname(session.getAttribute("nickname").toString());
            user.setPermissions(Permissions.valueOf(session.getAttribute("permissions").toString()));
            model.addAttribute("user",user);
            model.addAttribute("hideuser","");
            model.addAttribute("signupbutton","display: none");
        }
        else
        {
            model.addAttribute("hideuser","display: none");
            model.addAttribute("signupbutton"," ");
        }

        return "aboutus";
    }
}
