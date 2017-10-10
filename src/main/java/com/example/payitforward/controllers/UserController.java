package com.example.payitforward.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String displaySignupForm(Model model){

        return "/signup";

    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String processSignupForm(Model model){


    }



    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLoginForm(Model model){

        return "/login";

    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLoginForm(Model model){

    }



}
