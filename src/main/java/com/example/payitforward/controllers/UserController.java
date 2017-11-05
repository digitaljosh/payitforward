package com.example.payitforward.controllers;

import com.example.payitforward.models.User;
import com.example.payitforward.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.MessageDigest;


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

    //Allows user to sign up, saves their credentials to the database, and initiate a session
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String processSignupForm(@ModelAttribute @Valid User newUser, Errors errors, Model model,
                                    HttpServletRequest request, HttpServletResponse response) {

//        if (errors.hasErrors()){
//            model.addAttribute("title", "Create an Account");
//            return "signup";
//        }

        Iterable<User> users = userDao.findAll();

        for (User user : users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                model.addAttribute("title", "Create an Account");
                model.addAttribute("message", "That username is already in use. Please choose another or go to the login page.");
                return "signup";
            }
        }

        if (newUser.getPassword().isEmpty()) {
            model.addAttribute("password", "Password field cannot be empty.");
            return "signup";
        } else if (newUser.getPassword().length() < 3) {
            model.addAttribute("password", "Password must be between 3 and 15 characters.");
            return "signup";
        }

        //hash password, save user, and add user to session

        String userPassword = newUser.getPassword();
        newUser.setPwHash(hashPassword(userPassword));


        userDao.save(newUser);
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", newUser);
        response.encodeRedirectURL("profile/myprofile");

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
                                   Model model, HttpServletRequest request, String password,
                                   HttpServletResponse response){

        Iterable<User> users = userDao.findAll();

        for (User user : users) {
            if (user.getUsername().equals(returningUser.getUsername())) {
                if (user.getPwHash().equals(hashPassword(returningUser.getPassword()))) {
                    HttpSession session = request.getSession();
                    session.setAttribute("loggedInUser", user);
                    response.encodeRedirectURL("profile/myprofile");
                    return "redirect:profile/myprofile";

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
    public String logout(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("loggedInUser");
        model.addAttribute("title", "Log Out");
        return "logout";
    }

    public static String hashPassword(@ModelAttribute String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}