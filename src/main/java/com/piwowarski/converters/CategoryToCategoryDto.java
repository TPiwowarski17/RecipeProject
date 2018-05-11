package com.piwowarski.converters;

import com.piwowarski.DTO.CategoryDto;
import com.piwowarski.models.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryDto implements Converter<Category, CategoryDto> {
    @Synchronized
    @Nullable
    @Override
    public CategoryDto convert(Category category) {

        if (category == null) {
            return null;
        }
        final CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setDescription(category.getDescription());

        return categoryDto;
    }
}
