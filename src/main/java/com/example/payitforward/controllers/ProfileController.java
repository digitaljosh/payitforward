package com.example.payitforward.controllers;

import com.example.payitforward.models.data.OpportunityDao;
import com.example.payitforward.models.data.UserDao;
import com.example.payitforward.models.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    UserDao userDao;

    @Autowired
    OpportunityDao opportunityDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("profiles", userDao.findAll());
        model.addAttribute("title", "Users");

        return "profile/index";
    }

    @RequestMapping(value="view/{userId}", method = RequestMethod.GET)
    public String viewProfile(Model model, @PathVariable int userId){

        User user = userDao.findOne(userId);
        model.addAttribute("user", user);

        return "profile/view";
    }

    //Allows you to view your own profile
    @RequestMapping(value="myprofile", method = RequestMethod.GET)
    public String viewMyProfile(Model model, HttpSession session){

        //if there's no user in the session, redirect to login page
        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/login";
        }
        //get the user from session
        User currentUser = (User) session.getAttribute("loggedInUser");

        // get updated info from db
        User user = userDao.findOne(currentUser.getId());

        //add user object to model
        model.addAttribute("user", user);

        return "profile/myprofile";
    }

    //Renders the edit page for logged in user
    @RequestMapping(value="edit/{userId}", method = RequestMethod.GET)
    public String viewEditProfile(Model model, @PathVariable int userId, HttpSession session){

        //if user is not in session, redirect to login
        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/login";
        }

        //if the user in the session does not match the user ID in the route, redirect to login
        User currentUser = (User) session.getAttribute("loggedInUser");
        int currentId = currentUser.getId();

        if (currentId != userId){
            return "redirect:/login";
        }

        User user = userDao.findOne(userId);
        model.addAttribute("user", user);

        return "profile/edit";
    }

    //Posts the edits from edit page
    @RequestMapping(value="edit/{userId}", method = RequestMethod.POST)
    public String submitEditProfile(@RequestParam String displayname, String bio, String email,
                                    @PathVariable int userId, Model model, HttpSession session){

        User currentUser = (User) session.getAttribute("loggedInUser");

        //validate displayname
        if(displayname.length() > 25){
            model.addAttribute("error1", "Display name must be less than 25 characters");
            model.addAttribute("user", currentUser);
            return "profile/edit";
        }

        //validate email


        //validate bio
        if(bio.length() > 500){
            model.addAttribute("error3", "Bio must be shorter than 500 characters");
            model.addAttribute("user", currentUser);
            return "profile/edit";
        }

        currentUser.setDisplayname(displayname);
        currentUser.setBio(bio);

        //update the user object in the DB -- Hibernate checks ID to see if user should be updated or inserted
        userDao.save(currentUser);

        //redirect to the view of the user's profile so they can see changes
        return "redirect:/profile/myprofile";
    }

    //Renders delete account page
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String displayDeleteUserAccount(Model model, HttpSession session){

        //if user is not in session, redirect to login
        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/login";
        }

        //get the current user's ID
        User currentUser = (User) session.getAttribute("loggedInUser");
        int currentId = currentUser.getId();

        User user = userDao.findOne(currentId);

        model.addAttribute("user", user);
        model.addAttribute("title", "Delete account");

        return "profile/delete";
    }

    //Posts delete account page and redirects to home page
    @RequestMapping(value="delete", method=RequestMethod.POST)
    public String processDeleteUserAccount(@RequestParam int userId, HttpSession session){

        userDao.delete(userId);

        return "redirect:";
    }
}