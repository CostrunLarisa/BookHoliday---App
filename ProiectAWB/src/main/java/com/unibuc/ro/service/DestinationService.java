package com.unibuc.ro.service;

import com.unibuc.ro.model.Destination;

public interface DestinationService extends CrudService<Destination> {
    Destination findByName(String destinationName);
}
