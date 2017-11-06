package com.example.payitforward.controllers;

import com.example.payitforward.models.Category;
import com.example.payitforward.models.User;
import com.example.payitforward.models.data.CategoryDao;
import com.example.payitforward.models.data.OpportunityDao;
import com.example.payitforward.models.data.UserDao;
import com.example.payitforward.models.Opportunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("opportunity")
public class OpportunityController {

    @Autowired
    private UserDao userDao;

    @Autowired
    OpportunityDao opportunityDao;

    @Autowired
    CategoryDao categoryDao;


    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("opportunities", opportunityDao.findAll());
        model.addAttribute("title", "Opportunities");

        return "opportunity/index";
    }

    @RequestMapping(value = "{opportunityId}",method=RequestMethod.GET)
    public String displayOpportunity(Model model, @PathVariable int opportunityId, HttpSession session) {

        User currentUser = (User) session.getAttribute("loggedInUser");

        if (currentUser == null){
            return "redirect:/login";
        }

        Opportunity opportunityToSee = opportunityDao.findOne(opportunityId);

        List<User> currentClaimedUsers = opportunityToSee.getClaimingUsers();

        List<User> currentCompletedUsers = opportunityToSee.getCompletingUsers();

        Boolean userClaimed = false;
        Boolean userCompleted = false;
        Boolean isCreator = false;

        for (int i =0; i<currentClaimedUsers.size(); i++){
            if (currentClaimedUsers.get(i).getId() == currentUser.getId()){
                userClaimed = true;
            }
        }

        for (int i =0; i<currentCompletedUsers.size(); i++){
            if (currentCompletedUsers.get(i).getId() == currentUser.getId()){
                userCompleted = true;
            }
        }

        if (currentUser.getUsername().equals(opportunityToSee.getOpportunityCreator().getUsername())) {
            isCreator = true;
        }

        model.addAttribute("opportunity", opportunityToSee);
        model.addAttribute("userClaimed", userClaimed);
        model.addAttribute("userCompleted", userCompleted);
        model.addAttribute("isCreator", isCreator);

        return "opportunity/opportunityPage";
    }

    @RequestMapping(value = "{opportunityId}",method=RequestMethod.POST)
    public String processClaimAndCompletion(Model model, HttpSession session,
                                            @PathVariable int opportunityId){

        User currentUser = (User) session.getAttribute("loggedInUser");

        Opportunity opportunityToEdit = opportunityDao.findOne(opportunityId);

        User creator = opportunityToEdit.getOpportunityCreator();

        List<User> currentClaimedUsers = opportunityToEdit.getClaimingUsers();

        List<User> currentCompletedUsers = opportunityToEdit.getCompletingUsers();

        Boolean userClaimed = false;
        Boolean userCompleted = false;

        for (int i =0; i<currentClaimedUsers.size(); i++){
            if (currentClaimedUsers.get(i).getId() == currentUser.getId()){
                userClaimed = true;
            }
        }

        for (int i =0; i<currentCompletedUsers.size(); i++){
            if (currentCompletedUsers.get(i).getId() == currentUser.getId()){
                userCompleted = true;
            }
        }

        if (opportunityToEdit.getClaimed() > 0 && currentUser.getId() != creator.getId() && userClaimed==false && userCompleted==false) {
            opportunityToEdit.setClaimed(opportunityToEdit.getClaimed() - 1);

            currentClaimedUsers.add(currentUser);
            opportunityToEdit.setClaimingUsers(currentClaimedUsers);
            opportunityDao.save(opportunityToEdit);
        }

//        else if (currentUser.getId() != creator.getId() && userClaimed==true && userCompleted==false) {
//
//            currentCompletedUsers.add(currentUser);
//            opportunityToEdit.setCompletingUsers(currentCompletedUsers);
//            opportunityDao.save(opportunityToEdit);
//        }

        else if (currentUser.getId() == creator.getId() || userCompleted==true) {

            return "redirect:/opportunity/{opportunityId}";
        }

        return "redirect:/opportunity/{opportunityId}";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddForm(Model model, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/opportunity";
        }

        model.addAttribute("title", "Add Opportunity");
        model.addAttribute(new Opportunity());
        model.addAttribute("categories", categoryDao.findAll());


        return "opportunity/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Opportunity opportunity,
                             Errors errors, Model model, @RequestParam int claimed, @RequestParam int categoryId, HttpSession session) {

    if (errors.hasErrors()) {
        model.addAttribute("title", "Add Opportunity");
        return "opportunity/add";
    }

    if(claimed < 1){
        model.addAttribute("opportunity", opportunity);
        model.addAttribute("claimedError", "Please request at least 1 volunteer");
        return "opportunity/edit";
    }

    User currentUser = (User) session.getAttribute("loggedInUser");


    Category category = categoryDao.findOne(categoryId);
    opportunity.setCategory(category);
    opportunity.setOpportunityCreator(currentUser);
    opportunityDao.save(opportunity);

    return "redirect:/opportunity";
}

    @RequestMapping(value = "remove/{opportunityId}", method = RequestMethod.GET)
    public String displayRemoveOpportunityForm(Model model, @PathVariable int opportunityId, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/opportunity";
        }

        User currentUser = (User) session.getAttribute("loggedInUser");

        Opportunity opportunityToEdit = opportunityDao.findOne(opportunityId);

        User creator = opportunityToEdit.getOpportunityCreator();

        if (currentUser.getId() != creator.getId()){
            return "redirect:/opportunity";
        }

        model.addAttribute("opportunity", opportunityDao.findOne(opportunityId));
        model.addAttribute("title", "Remove Opportunity");
        return "opportunity/remove";
    }

    @RequestMapping(value = "remove/{opportunityId}", method = RequestMethod.POST)
    public String processRemoveOpportunityForm(@PathVariable int opportunityId) {

        Opportunity opportunityToRemove = opportunityDao.findOne(opportunityId);

        opportunityDao.delete(opportunityToRemove);

        return "redirect:/opportunity/";
    }

    @RequestMapping(value = "manage/{opportunityId}", method = RequestMethod.GET)
    public String displayManageOpportunityForm(Model model, HttpSession session, @PathVariable int opportunityId) {

        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/opportunity";
        }

        User currentUser = (User) session.getAttribute("loggedInUser");

        Opportunity opportunityToManage = opportunityDao.findOne(opportunityId);

        User creator = opportunityToManage.getOpportunityCreator();

        if (currentUser.getId() != creator.getId()){
            return "redirect:/opportunity";
        }

        model.addAttribute("opportunity", opportunityToManage);
        model.addAttribute("title", "Manage Claiming Users");

        return "opportunity/manage";
    }

    @RequestMapping(value = "manage/{opportunityId}", method = RequestMethod.POST)
    public String processManage(@RequestParam int userIds[], @PathVariable int opportunityId){

        Opportunity opportunityToManage = opportunityDao.findOne(opportunityId);

        List<User> currentCompletedUsers = opportunityToManage.getCompletingUsers();

        for (int  user :  userIds) {
            currentCompletedUsers.add(userDao.findOne(user));

        }
        opportunityToManage.setCompletingUsers(currentCompletedUsers);
        opportunityDao.save(opportunityToManage);
        return "redirect:/opportunity";
    }

    @RequestMapping(value = "edit/{opportunityId}", method=RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int opportunityId, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/opportunity";
        }

        User currentUser = (User) session.getAttribute("loggedInUser");

        Opportunity opportunityToEdit = opportunityDao.findOne(opportunityId);

        User creator = opportunityToEdit.getOpportunityCreator();

        if (currentUser.getId() != creator.getId()){
            return "redirect:/opportunity";
        }

        model.addAttribute("opportunity", opportunityToEdit);
        model.addAttribute("categories", categoryDao.findAll());

        return "opportunity/edit";

    }

    @RequestMapping(value = "edit/{opportunityId}", method=RequestMethod.POST)
    public String processEditOpportunityForm(@ModelAttribute @Valid Opportunity opportunity, Errors errors, Model model,
                                  @RequestParam String name, String description, String location, int claimed,
                                  @PathVariable int opportunityId, @RequestParam String date, @RequestParam int categoryId) {

        if(errors.hasErrors()){
            model.addAttribute("opportunity", opportunity);
            return "opportunity/edit";
        }

        if(claimed < 1){
            model.addAttribute("opportunity", opportunity);
            model.addAttribute("claimedError", "Please request at least 1 volunteer");
            return "opportunity/edit";
        }

        Opportunity opportunityToEdit = opportunityDao.findOne(opportunityId);
        Category cat = categoryDao.findOne(categoryId);

        opportunityToEdit.setName(name);
        opportunityToEdit.setDescription(description);
        opportunityToEdit.setLocation(location);
        opportunityToEdit.setClaimed(claimed);
        opportunityToEdit.setDate(date);
        opportunityToEdit.setCategory(cat);

        opportunityDao.save(opportunityToEdit);

        return "redirect:/opportunity";

    }

}