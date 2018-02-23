package ru.mail.controller;


import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author Denis Monich
 * this class implements view of login form
 */
@Controller
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);

    /**
     *
     * @return name of jsp page where view data
     */
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String showWelcomePage() {
        if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
            return "redirect:/explorer";
        }
        logger.error("coming to show welcome page GET");
        return "login";
    }
}

