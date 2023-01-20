package com.unibuc.ro.service;

import com.unibuc.ro.exceptions.EntityNotFoundException;
import com.unibuc.ro.model.Destination;
import com.unibuc.ro.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DestinationServiceImpl extends AbstractService<Destination> implements DestinationService {
    private final DestinationRepository destinationRepository;
    @Autowired
    public DestinationServiceImpl(DestinationRepository repository) {
        super(repository);
        this.destinationRepository = repository;
    }

    @Override
    public Destination findByName(String destinationName) {
        Optional<Destination> destination = destinationRepository.findByDestinationName(destinationName);
        if(destination.isPresent()) {
            return  destination.get();
        } else {
            throw new EntityNotFoundException("Destination with name " + destinationName + " does not exist!");
        }
    }
}
