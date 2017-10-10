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
@RequestMapping("opportunity")
public class OpportunityController {

    @Autowired
    private UserDao userDao;

    @Autowired
    OpportunityDao opportunityDao;
//

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("opportunities", opportunityDao.findAll());
        model.addAttribute("title", "Opportunities");

        return "opportunity/index";
    }


    //Things below this comment reportedly not functional
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddForm(Model model, @PathVariable int userId) {

        //User user = opportunityDao.findOne(userId);
       // AddOpportunityForm form = new AddOpportunityForm(userDao.findAll(), user);

        model.addAttribute("title", "Add Opportunity");
        model.addAttribute(new Opportunity());
        //model.addAttribute("opportunities",form);


        return "opportunity/edit";
    }

//
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Opportunity opportunity,
                                  Errors errors, @RequestParam int userId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Opportunity");
          //  model.addAttribute("user", userDao.findAll());
            return "opportunity/add";
        }


        opportunityDao.save(opportunity);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveOpportunityForm(Model model) {
        model.addAttribute("opportunities", opportunityDao.findAll());
        model.addAttribute("title", "Remove Opportunity");
        return "opportunity/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveOpportunityForm(@RequestParam int[] OpportunityIds) {

        // When removing a cheese from list,
        // find all menus that the cheese is in and
        // remove the cheese in each
        // Them, remove the cheese from the list


        for (int  opportunityId :  OpportunityIds) {
            opportunityDao.delete(opportunityId);
        }

        return "redirect:/opportunity";
    }

    @RequestMapping(value = "edit/{opportunityId}", method=RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int opportunityId) {

        Opportunity opportunityToEdit = opportunityDao.findOne(opportunityId);

        model.addAttribute("opportunity", opportunityToEdit);
        return "opportunity/edit";

    }

    @RequestMapping(value = "edit", method=RequestMethod.POST)
    public String processEditOpportunityForm(@RequestParam String name, @RequestParam String description, @RequestParam String location,
                                        @RequestParam int userId) {
        Opportunity opportunityToEdit = opportunityDao.findOne(userId);


        opportunityToEdit.setName(name);
        opportunityToEdit.setDescription(description);
        opportunityToEdit.setLocation(location);

        opportunityDao.save(opportunityToEdit);

        return "redirect:/opportunity";

    }


}
