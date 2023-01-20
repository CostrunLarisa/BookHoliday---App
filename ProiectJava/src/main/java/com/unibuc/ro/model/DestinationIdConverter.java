package com.unibuc.ro.model;

import com.unibuc.ro.repository.DestinationRepository;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DestinationIdConverter implements AttributeConverter<Destination, Long> {


    @Override
    public Long convertToDatabaseColumn(Destination attribute) {
        return attribute.getId();
    }

    @Override
    public Destination convertToEntityAttribute(Long dbData) {
        return new Destination();
    }
}
