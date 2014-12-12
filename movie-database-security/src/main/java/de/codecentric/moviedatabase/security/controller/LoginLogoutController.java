package de.codecentric.moviedatabase.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginLogoutController {

    /**
     * Open login page
     *
     * @return String
     */
    @RequestMapping(value= "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "login";
    }

    /**
     * Open login page
     *
     * @return String
     */
    @RequestMapping(value= "/logout", method = RequestMethod.GET)
    public String getLogoutPage() {
        return "redirect:/login";
    }
}
