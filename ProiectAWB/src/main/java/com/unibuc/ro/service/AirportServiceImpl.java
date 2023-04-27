package com.unibuc.ro.service;

import com.unibuc.ro.exceptions.EntityNotFoundException;
import com.unibuc.ro.model.Airport;
import com.unibuc.ro.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirportServiceImpl extends AbstractService<Airport> implements AirportService {
    private final AirportRepository airportRepository;

    @Autowired
    public AirportServiceImpl(AirportRepository repository) {
        super(repository);
        this.airportRepository = repository;
    }

    @Override
    public Airport findByName(String name) {
        Optional<Airport> airport = airportRepository.findAirportByAirportName(name);
        if (airport.isPresent()) {
            return airport.get();
        } else {
            throw new EntityNotFoundException("Airport with name " + name + " not found!");
        }
    }
}
