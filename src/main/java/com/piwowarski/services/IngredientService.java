package com.piwowarski.services;

import com.piwowarski.DTO.IngredientDto;

public interface IngredientService {

    IngredientDto findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

}
