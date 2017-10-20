package com.example.payitforward.controllers;

import com.example.payitforward.models.data.OpportunityDao;
import com.example.payitforward.models.data.UserDao;
import com.example.payitforward.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    UserDao userDao;

    @Autowired
    OpportunityDao opportunityDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("profiles", userDao.findAll());
        model.addAttribute("title", "Users");

        return "profile/index";
    }

    @RequestMapping(value="view/{userId}", method = RequestMethod.GET)
    public String viewProfile(Model model, @PathVariable int userId){

        //create a user object based on the user ID
        User user = userDao.findOne(userId);

        model.addAttribute("user", user);

        return "profile/view";
    }

    //TODO: figure out how to use session or similar to make sure user can only edit own profile
    @RequestMapping(value="edit/{userId}", method = RequestMethod.GET)
    public String viewEditProfile(Model model, @PathVariable int userId){

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
        return "redirect:/profile/view/" + updatedUser.getId();
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