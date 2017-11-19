package com.example.payitforward.controllers;

import com.example.payitforward.models.Category;
import com.example.payitforward.models.Opportunity;
import com.example.payitforward.models.data.CategoryDao;
import com.example.payitforward.models.data.OpportunityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private OpportunityDao opportunityDao;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(defaultValue = "0") int id) {
        model.addAttribute("title", "Categories");
        model.addAttribute("categories", categoryDao.findAll());
        return "category/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/category";
        }
        model.addAttribute(new Category());
        model.addAttribute("title", "Add Category");
        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Category category, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Category");
            return "redirect:/category";
        }


        categoryDao.save(category);
        return "redirect:/category/";
    }

    @RequestMapping(value = "{categoryId}", method = RequestMethod.GET)
    public String searchByCategory(Model model, @PathVariable int categoryId){

        Category correctCategory = categoryDao.findOne(categoryId);

        model.addAttribute("title", "Opportunities");
        model.addAttribute("opportunities", opportunityDao.findAll());

        return "category/searchByCategory";
    }


}
