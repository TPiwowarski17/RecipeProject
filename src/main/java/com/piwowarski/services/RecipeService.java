package com.piwowarski.services;

import com.piwowarski.DTO.RecipeDto;
import com.piwowarski.models.Recipe;

import java.util.Set;


public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeDto saveRecipeDto(RecipeDto recipeDto);

    RecipeDto findDtoById(Long id);

    void deleteById(Long idToDelete);
}
