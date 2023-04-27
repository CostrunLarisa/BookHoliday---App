package com.unibuc.ro.service;

import com.unibuc.ro.exceptions.EntityNotFoundException;
import com.unibuc.ro.model.Address;
import com.unibuc.ro.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl extends AbstractService<Address> implements AddressService{
    private final AddressRepository addressRepository;
    public AddressServiceImpl(AddressRepository repository) {
        super(repository);
        this.addressRepository = repository;
    }

    @Override
    public Address findByAccommodationName(String name) {
        Optional<Address> address = addressRepository.findAddressByAccommodation_Name(name);
        if (address.isPresent()) {
            return address.get();
        } else {
            throw new EntityNotFoundException("Accommodation with name " + name + " not found!");
        }
    }
}
