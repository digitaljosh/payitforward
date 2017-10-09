package com.example.payitforward.Controllers;

import com.example.payitforward.models.Data.OpportunityDao;
import com.example.payitforward.models.Data.UserDao;
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
        model.addAttribute("title", "My Profile");

        return "profile/index";
    }
}