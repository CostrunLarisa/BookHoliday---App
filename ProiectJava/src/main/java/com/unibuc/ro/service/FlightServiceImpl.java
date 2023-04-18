package com.unibuc.ro.service;

import com.unibuc.ro.model.Flight;
import com.unibuc.ro.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<Flight> findAllByPeriod(String startDate, String endDate) {
        return flightRepository.findByPeriod(LocalDate.from(LocalDateTime.parse(startDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                LocalDate.from(LocalDateTime.parse(endDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    @Override
    public List<Flight> findAllByDest(String destinationName) {
        return flightRepository.findByDest(destinationName);
    }
}
