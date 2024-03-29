package com.unibuc.ro.service;

import com.unibuc.ro.model.Flight;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface FlightService extends CrudService<Flight> {
    List<Flight> findAllByPeriod(String  startDate, String endDate);
    List<Flight> findAllByDest(String destinationName);
}
