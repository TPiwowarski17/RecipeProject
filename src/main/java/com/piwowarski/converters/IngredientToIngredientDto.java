package com.piwowarski.converters;

import com.piwowarski.DTO.IngredientDto;
import com.piwowarski.models.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientDto implements Converter<Ingredient, IngredientDto> {

    private UniTOfMeasureToUnitOfMeasureDto uomConverter;

    public IngredientToIngredientDto(UniTOfMeasureToUnitOfMeasureDto uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientDto convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ingredient.getId());
        if (ingredient.getRecipe() != null) {
            ingredientDto.setRecipeId(ingredient.getRecipe().getId());
        }
        ingredientDto.setAmount(ingredient.getAmount());
        ingredientDto.setDescription(ingredient.getDescription());
        ingredientDto.setUom(uomConverter.convert(ingredient.getUom()));
        return ingredientDto;
    }
}
