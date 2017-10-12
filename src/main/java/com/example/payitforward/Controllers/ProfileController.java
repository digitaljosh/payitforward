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

        model.addAttribute("profile", userDao.findOne(1));
        model.addAttribute("title", "My Profile");

        return "profile/index";
    }

    @RequestMapping(value="view/{userId}", method = RequestMethod.GET)
    public String viewProfile(Model model, @PathVariable int userId){

        //create a user object based on the user ID
        User user = userDao.findOne(userId);

        model.addAttribute("user", user);

        //TODO: add user picture
        //TODO: add list of opportunities on offer
        //TODO: add list of claimed opportunities
        //TODO: add list of completed opportunities

        return "profile/view";
    }

    //TODO: figure out how to use session or similar to make sure user can only edit own profile
    @RequestMapping(value="edit/{userId}", method = RequestMethod.GET)
    public String viewEditProfile(Model model,
                                  @PathVariable int userId){

        User user = userDao.findOne(userId);

        model.addAttribute("user", user);

        return "profile/edit";
    }

    @RequestMapping(value="edit", method = RequestMethod.POST)
    public String submitEditProfile(Model model,
                              @ModelAttribute @Valid User user,
                              Errors errors){

        if (errors.hasErrors()) {
            model.addAttribute("user", user);
            return "profile/edit";
        }

        //update the user object in the DB -- Hibernate checks ID to see if user should be updated or inserted
        userDao.save(user);

        //redirect to the view of the user's profile so they can see changes
        return "redirect:/profile/view" + user.getId();
    }


    @RequestMapping(value = "create")
    public String create(Model model) {

        model.addAttribute("title", "Introduce Yourself");

        return "profile/create";
    }
}