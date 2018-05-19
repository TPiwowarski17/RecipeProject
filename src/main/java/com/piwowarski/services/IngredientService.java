package com.piwowarski.services;

import com.piwowarski.DTO.IngredientDto;

public interface IngredientService {

    IngredientDto findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientDto saveIngredientDto(IngredientDto dto);

    void deleteByRecipeIdAndIngredientId(Long recpieId, Long ingredientId);

}
