package com.unibuc.ro.service;

import com.unibuc.ro.model.Flight;
import com.unibuc.ro.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class FlightServiceImpl extends AbstractService<Flight> implements FlightService {
    private final FlightRepository flightRepository;
    @Autowired
    public FlightServiceImpl(FlightRepository repository) {
        super(repository);
        this.flightRepository = repository;
    }

    @Override
    public List<Flight> findAllByPeriod(Date startDate, Date endDate) {
        return flightRepository.findByPeriod(startDate,endDate);
    }

    @Override
    public List<Flight> findAllByDest(String destinationName) {
        return flightRepository.findByDest(destinationName);
    }
}
