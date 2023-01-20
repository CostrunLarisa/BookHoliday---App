package com.unibuc.ro.service;

import com.unibuc.ro.model.Accommodation;

import java.util.List;

public interface AccommodationService extends CrudService<Accommodation> {
    Accommodation findByName(String name);

    List<Accommodation> findAllByDestination(String destinationName);
}
