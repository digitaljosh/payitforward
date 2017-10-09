package com.example.payitforward.Controllers;

import com.example.payitforward.Models.Data.OpportunityDao;
import com.example.payitforward.Models.Data.UserDao;
import com.example.payitforward.Models.Opportunity;
import com.example.payitforward.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("profile")
public class OpportunityController {

    @Autowired
    private UserDao userDao;

    @Autowired
    OpportunityDao opportunityDao;


    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("opportunities", opportunityDao.findAll());
        model.addAttribute("title", "Opportunities");

        return "profile/index";
    }


    //Things below this comment reportedly not functional
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddForm(Model model, @PathVariable int userId) {

        //User user = opportunityDao.findOne(userId);
        AddOpportunityForm form = new AddOpportunityForm(userDao.findAll(), user);

        model.addAttribute("title", "Add Opportunity");
        model.addAttribute(new Opportunity());
        model.addAttribute("opportunities",form);


        return "profile/edit";
    }

//
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEditForm(@ModelAttribute @Valid User newUser,
                                  Errors errors, @RequestParam int userId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Profile");
          //  model.addAttribute("user", userDao.findAll());
            return "profile/edit";
        }

        User user = userDao.findOne(userId);
        newProfile.setUser(user);
        userDao.save(newProfile);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveOpportunityForm(Model model) {
        model.addAttribute("opportunities", opportunityDao.findAll());
        model.addAttribute("title", "Opportunity");
        return "profile/remove";
    }

    @RequestMapping(value = "edit/{opportunityId}", method=RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int opportunityId) {

        Opportunity opportunityToEdit = OpportunityDao.findOne(opportunityId);

        model.addAttribute("opportunity", opportunityToEdit);
        return "cheese/edit";

    }

    @RequestMapping(value = "edit", method=RequestMethod.POST)
    public String processEditOpportunityForm(@RequestParam String name, @RequestParam String description,
                                        @RequestParam int userId) {
        Opportunity opportunityToEdit = opportunityDao.findOne(userid);


        opportunityToEdit.setName(name);
        opportunityToEdit.setDescription(description);

        opportunityDao.save(opportunityToEdit);

        return "redirect:/opportunity";

    }


}
