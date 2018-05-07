package com.piwowarski.controllers;

import com.piwowarski.models.Recipe;
import com.piwowarski.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShowController {

    RecipeRepository recipeRepository;

    @Autowired
    public ShowController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping("/recipe/show/{id}")
    public String showRecipe(@PathVariable(name = "id") Long id, Model model) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(NullPointerException::new);
        model.addAttribute("recipe", recipe);
        return "recipe/show";
    }
}
