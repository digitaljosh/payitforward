package com.example.payitforward.controllers;

import com.example.payitforward.models.User;
import com.example.payitforward.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserDao userDao;


    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String displaySignupForm(Model model){

        model.addAttribute(new User());
        model.addAttribute("title", "Create an Account");

        return "signup";

    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String processSignupForm(Model model, @ModelAttribute @Valid User newUser, Errors errors){

        if (errors.hasErrors()){
            model.addAttribute("title", "Create an Account");
            return "signup";
        }

        userDao.save(newUser);
        return "redirect:";
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLoginForm(Model model){

        model.addAttribute(new User());
        model.addAttribute("title", "Log In");

        return "login";

    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLoginForm(Model model, @ModelAttribute @Valid User returningUser, Errors errors){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "login";
        }

        return "redirect:";

    }

}
