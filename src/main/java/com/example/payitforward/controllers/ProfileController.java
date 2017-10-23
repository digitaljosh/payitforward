package com.example.payitforward.controllers;

import com.example.payitforward.models.data.OpportunityDao;
import com.example.payitforward.models.data.UserDao;
import com.example.payitforward.models.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @RequestMapping(value="myprofile", method = RequestMethod.GET)
    public String viewMyProfile(Model model, HttpSession session){

        //if there's no user in the session, redirect to login page
        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/login";
        }
        //get the user from session
        User currentUser = (User) session.getAttribute("loggedInUser");

        //add user object to model
        model.addAttribute("user", currentUser);

        return "profile/myprofile";
    }


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

    @RequestMapping(value="edit/{userId}", method = RequestMethod.POST)
    public String submitEditProfile(String username, String displayname, String bio,
                                    @PathVariable int userId){

        //if (errors.hasErrors()) {
            //model.addAttribute("user", user);
            //return "profile/edit";
        //}

        //create a new user object corresponding to the user ID
        User updatedUser = userDao.findOne(userId);

        updatedUser.setUsername(username);
        updatedUser.setDisplayname(displayname);
        updatedUser.setBio(bio);

        //update the user object in the DB -- Hibernate checks ID to see if user should be updated or inserted
        userDao.save(updatedUser);

        //redirect to the view of the user's profile so they can see changes
        return "redirect:/profile/myprofile";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String displayDeleteUserAccount(Model model){

        model.addAttribute("title", "Delete account");

        return "profile/delete";
    }

    @RequestMapping(value="delete", method=RequestMethod.POST)
    public String processDeleteUserAccount(@RequestParam int userId){

        userDao.delete(userId);

        return "redirect:";
    }
}