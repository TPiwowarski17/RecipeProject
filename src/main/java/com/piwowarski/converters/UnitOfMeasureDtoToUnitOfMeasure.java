package com.piwowarski.converters;

import com.piwowarski.DTO.UnitOfMeasureDto;
import com.piwowarski.models.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureDtoToUnitOfMeasure implements Converter<UnitOfMeasureDto, UnitOfMeasure> {
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureDto unitOfMeasureDto) {
        if (unitOfMeasureDto == null) {
            return null;
        }
        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(unitOfMeasureDto.getId());
        uom.setDescription(unitOfMeasureDto.getDescription());
        return uom;
    }
}
