package com.piwowarski.converters;

import com.piwowarski.DTO.IngredientDto;
import com.piwowarski.models.Ingredient;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientDtoToIngredient implements Converter<IngredientDto, Ingredient> {

    private UnitOfMeasureDtoToUnitOfMeasure uomConverter;

    @Autowired
    public IngredientDtoToIngredient(UnitOfMeasureDtoToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientDto ingredientDto) {
        if (ingredientDto == null) {
            return null;
        }
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientDto.getId());
        ingredient.setAmount(ingredientDto.getAmount());
        ingredient.setDescription(ingredientDto.getDescription());
        ingredient.setUom(uomConverter.convert(ingredientDto.getUnitOfMeasure()));
        return ingredient;
    }
}
