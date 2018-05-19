package com.piwowarski.services;

import com.piwowarski.DTO.UnitOfMeasureDto;
import com.piwowarski.converters.UniTOfMeasureToUnitOfMeasureDto;
import com.piwowarski.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UniTOfMeasureToUnitOfMeasureDto uniTOfMeasureToUnitOfMeasureDto;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UniTOfMeasureToUnitOfMeasureDto uniTOfMeasureToUnitOfMeasureDto) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.uniTOfMeasureToUnitOfMeasureDto = uniTOfMeasureToUnitOfMeasureDto;
    }

    @Override
    public Set<UnitOfMeasureDto> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false).map(uniTOfMeasureToUnitOfMeasureDto::convert).collect(Collectors.toSet());
    }
}
