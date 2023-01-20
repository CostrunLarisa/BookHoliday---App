package com.unibuc.ro.service;

import com.unibuc.ro.model.Airport;

public interface AirportService extends CrudService<Airport>{
    Airport findByName(String name);
}
