package com.piwowarski.services;

import com.piwowarski.DTO.RecipeDto;
import com.piwowarski.converters.RecipeDtoToRecipe;
import com.piwowarski.converters.RecipeToRecipeDto;
import com.piwowarski.models.Recipe;
import com.piwowarski.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDtoToRecipe recipeDtoToRecipe;
    private final RecipeToRecipeDto recipeToRecipeDto;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeDtoToRecipe recipeDtoToRecipe, RecipeToRecipeDto recipeToRecipeDto) {
        this.recipeRepository = recipeRepository;
        this.recipeDtoToRecipe = recipeDtoToRecipe;
        this.recipeToRecipeDto = recipeToRecipeDto;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long l) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);
        return recipeOptional.orElseThrow(RuntimeException::new);
    }


    @Override
    @Transactional
    public RecipeDto saveRecipeDto(RecipeDto recipeDto) {
        Recipe detachedRecipe = recipeDtoToRecipe.convert(recipeDto);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        return recipeToRecipeDto.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeDto findDtoById(Long id) {
        return recipeToRecipeDto.convert(findById(id));
    }

    @Override
    public void deleteById(Long idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }
}
