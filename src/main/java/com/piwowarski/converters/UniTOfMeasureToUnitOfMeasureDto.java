package com.piwowarski.converters;

import com.piwowarski.DTO.UnitOfMeasureDto;
import com.piwowarski.models.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UniTOfMeasureToUnitOfMeasureDto implements Converter<UnitOfMeasure, UnitOfMeasureDto> {
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureDto convert(UnitOfMeasure unitOfMeasure) {

        if (unitOfMeasure == null) {
            return null;
        }
        final UnitOfMeasureDto uomd = new UnitOfMeasureDto();
        uomd.setId(unitOfMeasure.getId());
        uomd.setDescription(unitOfMeasure.getUom());
        return uomd;
    }
}
