package com.unibuc.ro.service;

import com.unibuc.ro.model.Flight;

import java.util.Date;
import java.util.List;

public interface FlightService extends CrudService<Flight> {
    List<Flight> findAllByPeriod(Date startDate, Date endDate);
    List<Flight> findAllByDest(String destinationName);
}
