package com.unibuc.ro.service;

import com.unibuc.ro.exceptions.EntityNotFoundException;
import com.unibuc.ro.model.Accommodation;
import com.unibuc.ro.model.Destination;
import com.unibuc.ro.repository.AccommodationRepository;
import com.unibuc.ro.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationServiceImpl extends AbstractService<Accommodation> implements AccommodationService{
    private final AccommodationRepository accommodationRepository;
    private final DestinationRepository destinationRepository;
    @Autowired
    public AccommodationServiceImpl(AccommodationRepository repository, DestinationRepository destinationRepository) {
        super(repository);
        this.accommodationRepository = repository;
        this.destinationRepository = destinationRepository;
    }

    @Override
    public Accommodation findByName(String name) {
        Optional<Accommodation> accommodation = accommodationRepository.findByName(name);
        if (accommodation.isPresent()) {
            return accommodation.get();
        } else {
            throw new EntityNotFoundException("Accommodation with name " + name + " not found!");
        }
    }

    @Override
    public List<Accommodation> findAllByDestination(String destinationName) {
        Optional<Destination> destination = destinationRepository.findByDestinationName(destinationName);
        if (destination.isPresent()) {
            return accommodationRepository.findAllByDestination(destinationName);
        } else {
            throw new EntityNotFoundException("Destination with name " + destinationName + " does not exist!");
        }
    }

    @Override
    public List<Accommodation> findAllByDestinationSorted(String destinationName) {
        List<Accommodation> accommodations = findAllByDestination(destinationName);
        accommodations.sort(Comparator.comparing(Accommodation::getPricePerNight));
        return accommodations;
    }

}
