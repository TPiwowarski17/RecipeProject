package com.piwowarski.services;

import com.piwowarski.DTO.IngredientDto;
import com.piwowarski.converters.IngredientDtoToIngredient;
import com.piwowarski.converters.IngredientToIngredientDto;
import com.piwowarski.models.Ingredient;
import com.piwowarski.models.Recipe;
import com.piwowarski.repositories.RecipeRepository;
import com.piwowarski.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientDto ingredientToIngredientDto;
    private final IngredientDtoToIngredient ingredientDtoToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientDto ingredientToIngredientDto, IngredientDtoToIngredient ingredientDtoToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientDto = ingredientToIngredientDto;
        this.ingredientDtoToIngredient = ingredientDtoToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    @Transactional
    public IngredientDto saveIngredientDto(IngredientDto dto) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(dto.getRecipeId());

        if (!recipeOptional.isPresent()) {
            return new IngredientDto();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(i -> i.getId().equals(dto.getId())).findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(dto.getDescription());
                ingredientFound.setAmount(dto.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository.findById(dto.getUom().getId()).orElseThrow(RuntimeException::new));
            } else {
                Ingredient ingredient = ingredientDtoToIngredient.convert(dto);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }
            Recipe saveRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = saveRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(dto.getId()))
                    .findFirst();
            if (!savedIngredientOptional.isPresent()) {
                savedIngredientOptional = saveRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(dto.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(dto.getAmount()))
                        .filter(ingredient -> ingredient.getUom().getId().equals(dto.getUom().getId()))
                        .findFirst();
            }
            return ingredientToIngredientDto.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteByRecipeIdAndIngredientId(Long recpieId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recpieId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Recipe Id Not Found. Id: " + recpieId);
        }
    }
}
