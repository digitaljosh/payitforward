package com.example.payitforward.Controllers;

import com.example.payitforward.models.Data.OpportunityDao;
import com.example.payitforward.models.Data.UserDao;
import com.example.payitforward.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String submitEditProfile(@ModelAttribute @Valid User user,
                                    Errors errors,
                                    @PathVariable int userId,
                                    Model model){

        if (errors.hasErrors()) {
            model.addAttribute("user", user);
            return "profile/edit";
        }

        //update the user object in the DB -- Hibernate checks ID to see if user should be updated or inserted
        userDao.save(user);

        //redirect to the view of the user's profile so they can see changes
        return "redirect:/profile/view/" + user.getId();
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String displayDeleteUserAccount(Model model){
        //
        model.addAttribute("title", "Delete account");

        return "profile/delete";
    }

    @RequestMapping(value="delete", method=RequestMethod.POST)
    public String processDeleteUserAccount(@RequestParam int userId){

        //delete the user from the userDao
        userDao.delete(userId);

        //TODO: add some kind of confirmation message
        return "redirect:";
    }
}