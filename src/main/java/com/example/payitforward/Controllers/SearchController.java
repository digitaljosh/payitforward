package com.example.payitforward.Controllers;

import com.example.payitforward.models.Data.OpportunityDao;
import com.example.payitforward.models.Data.UserDao;
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

        List<User> users = userDao.findByUsernameLike(search);


        List<Opportunity> opportunities = opportunityDao.findByNameLike(search);

        model.addAttribute("opportunities", opportunities);
        model.addAttribute("profiles", users);
        model.addAttribute("title", "Search Results");

        return "search";
    }
}

