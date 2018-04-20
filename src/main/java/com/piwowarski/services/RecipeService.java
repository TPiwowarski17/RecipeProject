package com.piwowarski.services;

import com.piwowarski.models.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;


public interface RecipeService {
    Set<Recipe> getRecipes();
}
