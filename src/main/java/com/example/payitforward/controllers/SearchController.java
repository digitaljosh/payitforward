package com.example.payitforward.controllers;

import com.example.payitforward.models.data.OpportunityDao;
import com.example.payitforward.models.data.UserDao;
import com.example.payitforward.models.Opportunity;
import com.example.payitforward.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    UserDao userDao;

    @Autowired
    OpportunityDao opportunityDao;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String searchResults(@RequestParam String search, Model model){

        //Searches database for user entries containing the search query
        List<User> users = userDao.findByUsernameContains(search);
        //Searches database for opportunity entries containing the search query
        List<Opportunity> opportunities = opportunityDao.findByNameContains(search);

        model.addAttribute("opportunities", opportunities);
        model.addAttribute("profiles", users);
        model.addAttribute("title", "Search Results");

        return "search";
    }
}
