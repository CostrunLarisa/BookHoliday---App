package com.unibuc.ro.service;

import com.unibuc.ro.model.Accommodation;
import com.unibuc.ro.model.AccommodationType;
import com.unibuc.ro.model.Address;
import com.unibuc.ro.model.Destination;
import com.unibuc.ro.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {
    @InjectMocks
    private AddressServiceImpl addressService;
    @Mock
    private AddressRepository addressRepository;

    @Test
    void findByAccommodationName() {
        //prepare
        Destination destination = new Destination("Maldive");
        Accommodation accommodation = new Accommodation(AccommodationType.HOSTEL, "Arena",
                285l, "12:30", "10:30", 120,
                destination);
        Address address = new Address(accommodation, "12km", "Str.Landia 53");
        when(addressRepository.findAddressByAccommodation_Name("Arena")).thenReturn(Optional.of(address));
        //act
        Address address1 = addressService.findByAccommodationName("Arena");
        //assert
        assertEquals(address.getKmFromCenter(), address1.getKmFromCenter());
        assertEquals(address.getStreetAndNumber(), address1.getStreetAndNumber());
    }

    @Test
    void findByInexistentAccommodationName() {
        when(addressRepository.findAddressByAccommodation_Name("Arena")).thenReturn(Optional.empty());
        //act
        try {
            addressService.findByAccommodationName("Arena");
        } catch (Exception e) {
            //assert
            assertEquals("Accommodation with name Arena not found!", e.getMessage());
        }
    }
    @Test
    void update() {
        when(addressRepository.findById(1l)).thenReturn(Optional.of(new Address()));
        addressService.update(1l,new Address());
        verify(addressRepository,times(1)).save(any());
    }
    @Test
    void save() {
        addressService.save(new Address());
        verify(addressRepository,times(1)).save(any());
    }
    @Test
    void findAll() {
        addressService.findAll();
        verify(addressRepository,times(1)).findAll();
    }
    @Test
    void deleteById() {
        when(addressRepository.findById(1l)).thenReturn(Optional.of(new Address()));
        addressService.deleteById(1l);
        verify(addressRepository, times(1)).delete(any());
    }
    @Test
    void deleteByInexistentId() {
        when(addressRepository.findById(1l)).thenReturn(Optional.empty());
        try {
            addressService.deleteById(1l);
        }catch(Exception e) {
            assertEquals("Address with id 1 not found!",e.getMessage());
        }
    }
}