package com.piwowarski.controllers;

import com.piwowarski.DTO.RecipeDto;
import com.piwowarski.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeFormControler {
    private final RecipeService recipeService;

    public RecipeFormControler(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeDto());
        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findDtoById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeDto recipeDto) {
        RecipeDto savedDto = recipeService.saveRecipeDto(recipeDto);

        return "redirect:/recipe/" + savedDto.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
