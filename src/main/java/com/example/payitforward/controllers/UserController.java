package com.example.payitforward.controllers;

import com.example.payitforward.models.User;
import com.example.payitforward.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    //renders signup form
    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String displaySignupForm(Model model){

        model.addAttribute(new User());
        model.addAttribute("title", "Create an Account");

        return "signup";

    }

    //Allows user to sign up and saves their credentials to the database
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String processSignupForm(@ModelAttribute @Valid User newUser, Errors errors,
                                    Model model, HttpServletRequest request){

        if (errors.hasErrors()){
            model.addAttribute("title", "Create an Account");
            return "signup";
        }

        Iterable<User> users = userDao.findAll();

        for (User user : users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                model.addAttribute("title", "Create an Account");
                model.addAttribute("message", "That username is already in use. Please choose another or go to the login page.");
                return "signup";
            }
        }

        //save user and add user to session
        userDao.save(newUser);
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", newUser);

        return "redirect:/profile/myprofile";
    }

    //renders login form
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLoginForm(Model model){

        model.addAttribute(new User());
        model.addAttribute("title", "Log In");

        return "login";

    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLoginForm(@ModelAttribute @Valid User returningUser, Errors errors,
                                   Model model, HttpServletRequest request){

//        if (errors.hasErrors()) {
//            model.addAttribute("title", "Log In");
//            return "login";
//        }

        Iterable<User> users = userDao.findAll();

        for (User user : users) {
            if (user.getUsername().equals(returningUser.getUsername())) {
                if (user.getPassword().equals(returningUser.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("loggedInUser", user);
                    return "redirect:";
                    //TODO: return some kind of welcome message
                } else {
                    //return login page with password error
                    model.addAttribute("title", "Log In");
                    model.addAttribute("passwordMessage", "Password is incorrect. Please try again.");
                    return "login";
                }
            }
        }
        //return login page with username error
        model.addAttribute("title", "Log In");
        model.addAttribute("usernameMessage", "There is no account with that username. Please try again or sign up for an account.");


        return "login";

    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "login";
    }

}
