package com.example.payitforward.controllers;

import com.example.payitforward.models.Opportunity;
import com.example.payitforward.models.data.OpportunityDao;
import com.example.payitforward.models.data.UserDao;
import com.example.payitforward.models.User;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    UserDao userDao;

    @Autowired
    OpportunityDao opportunityDao;

    EmailValidator validator = new EmailValidator();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("profiles", userDao.findAll());
        model.addAttribute("title", "Users");

        return "profile/index";
    }

    @RequestMapping(value="view/{userId}", method = RequestMethod.GET)
    public String viewProfile(Model model, @PathVariable int userId){

        User userProfile = userDao.findOne(userId);

        List<Opportunity> claimedOpportunities = new ArrayList();
        List<Opportunity> completedOpportunities = new ArrayList();
        Iterable<Opportunity> opportunities = opportunityDao.findAll();

        for (Opportunity opp : opportunities) {
            boolean completed = false;
            boolean claimed = false;
            List<User> currentCompletedUsers = opp.getCompletingUsers();
            for (int i =0; i<currentCompletedUsers.size(); i++){
                if (currentCompletedUsers.get(i).getId() == userId){
                    completed = true;
                }
            }

            List<User> currentClaimedUsers = opp.getClaimingUsers();
            for (int i =0; i<currentClaimedUsers.size(); i++){
                if (currentClaimedUsers.get(i).getId() == userId){
                    claimed = true;
                }
            }

            if (completed) {
                completedOpportunities.add(opp);
            } else if (claimed) {
                claimedOpportunities.add(opp);
            }
        }

        String userPicture = userProfile.getImageName();

        if(userPicture != null){
            model.addAttribute("photoName", userPicture);
        }

        model.addAttribute("user", userProfile);
        model.addAttribute("claimedOpportunities", claimedOpportunities);
        model.addAttribute("completedOpportunities", completedOpportunities);

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
        User user = userDao.findOne(currentUser.getId());

        List<Opportunity> claimedOpportunities = new ArrayList();
        List<Opportunity> completedOpportunities = new ArrayList();
        Iterable<Opportunity> opportunities = opportunityDao.findAll();

        for (Opportunity opp : opportunities) {
            boolean completed = false;
            boolean claimed = false;
            List<User> currentCompletedUsers = opp.getCompletingUsers();
            for (int i =0; i<currentCompletedUsers.size(); i++){
                if (currentCompletedUsers.get(i).getId() == user.getId()){
                    completed = true;
                }
            }

            List<User> currentClaimedUsers = opp.getClaimingUsers();
            for (int i =0; i<currentClaimedUsers.size(); i++){
                if (currentClaimedUsers.get(i).getId() == user.getId()){
                    claimed = true;
                }
            }

            if (completed) {
                completedOpportunities.add(opp);
            } else if (claimed) {
                claimedOpportunities.add(opp);
            }
        }

        String userPicture = user.getImageName();

        if(userPicture != null){
            model.addAttribute("photoName", userPicture);
        }

        //add user object to model
        model.addAttribute("user", user);
        model.addAttribute("claimedOpportunities", claimedOpportunities);
        model.addAttribute("completedOpportunities", completedOpportunities);

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
        model.addAttribute("title", "Edit Profile");

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
        if(!validator.isValid(email, null)){
            model.addAttribute("error2", "Please enter a valid email address");
            model.addAttribute("user", currentUser);
            return "profile/edit";
        }

        //validate bio
        if(bio.length() > 250){
            model.addAttribute("error3", "Bio must be shorter than 500 characters");
            model.addAttribute("user", currentUser);
            return "profile/edit";
        }

        currentUser.setDisplayname(displayname);
        currentUser.setEmail(email);
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
    public String processDeleteUserAccount(@RequestParam int userId, HttpServletRequest request){

        HttpSession session = request.getSession();
        session.removeAttribute("loggedInUser");

        userDao.delete(userId);

        return "redirect:";
    }
}