package com.piwowarski.controllers;

import com.piwowarski.models.Category;
import com.piwowarski.models.UnitOfMeasure;
import com.piwowarski.repositories.CategoryRepository;
import com.piwowarski.repositories.UnitOfMeasureRepository;
import com.piwowarski.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model model){
        model.addAttribute("recipes",recipeService.getRecipes());
        return "index";
    }
}
