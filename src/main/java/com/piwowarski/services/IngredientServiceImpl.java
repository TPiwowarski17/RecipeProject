package com.piwowarski.services;

import com.piwowarski.DTO.IngredientDto;
import com.piwowarski.converters.IngredientToIngredientDto;
import com.piwowarski.models.Recipe;
import com.piwowarski.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientDto ingredientToIngredientDto;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientDto ingredientToIngredientDto, RecipeRepository recipeRepository) {
        this.ingredientToIngredientDto = ingredientToIngredientDto;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientDto findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error("recipe is not found! Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientDto> ingredientDtoOptional = recipe.getIngredients().stream()
                .filter(i -> i.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientDto.convert(ingredient)).findFirst();

        if (!ingredientDtoOptional.isPresent()) {
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientDtoOptional.get();
    }
}
