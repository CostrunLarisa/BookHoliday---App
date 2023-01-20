package com.unibuc.ro.model;

import com.unibuc.ro.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ClientIdConverter implements AttributeConverter<Client,Long> {

    @Override
    public Long convertToDatabaseColumn(Client attribute) {
        return attribute.getId();
    }

    @Override
    public Client convertToEntityAttribute(Long dbData) {
        return new Client();
    }
}
