package com.unibuc.ro.service;

import com.unibuc.ro.model.Accommodation;
import com.unibuc.ro.model.AccommodationType;
import com.unibuc.ro.model.Destination;
import com.unibuc.ro.model.Holiday;
import com.unibuc.ro.repository.AccommodationRepository;
import com.unibuc.ro.repository.DestinationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {
    @InjectMocks
    private AccommodationServiceImpl accommodationService;
    @Mock
    private AccommodationRepository accommodationRepository;
    @Mock
    private DestinationRepository destinationRepository;

    @Test
    void findByNameHappyFlow() {
        //prepare
        Destination destination = new Destination("Maldive");
        Accommodation accommodation = new Accommodation(AccommodationType.HOTEL, "Alegria",
                180l, "14:00", "10:00", 300,
                destination);
        when(accommodationRepository.findByName("Alegria")).thenReturn(Optional.of(accommodation));
        //act
        Accommodation accommodation1 = accommodationService.findByName("Alegria");
        //assert
        assertEquals(accommodation1.getAccomodationType(), accommodation.getAccomodationType());
        assertEquals(accommodation1.getName(), accommodation.getName());
    }

    @Test
    void findByInexistentName() {
        //prepare
        when(accommodationRepository.findByName("Bonitos")).thenReturn(Optional.empty());
        //act
        try {
            accommodationService.findByName("Bonitos");
        } catch (Exception e) {
            //assert
            assertEquals("Accommodation with name Bonitos not found!", e.getMessage());
        }
    }

    @Test
    void findAllByDestinationHappyFlow() {
        //prepare
        Destination destination = new Destination("Maldive");
        Accommodation accommodation = new Accommodation(AccommodationType.HOTEL, "Alegria",
                180l, "14:00", "10:00", 300,
                destination);
        Accommodation accommodation1 = accommodationService.save(new Accommodation(AccommodationType.HOTEL, "Meeru",
                160l, "14:00", "10:00", 150,
                destination));
        List<Accommodation> accommodations = List.of(accommodation1, accommodation);
        when(destinationRepository.findByDestinationName("Maldive")).thenReturn(Optional.of(destination));
        when(accommodationRepository.findAllByDestination("Maldive")).thenReturn(accommodations);
        //act
        List<Accommodation> accommodationList = accommodationService.findAllByDestination("Maldive");
        //assert
        assertEquals(accommodations, accommodationList);
    }

    @Test
    void findAllByInexistentDestination() {
        //prepare
        when(destinationRepository.findByDestinationName("Bora-Bora")).thenReturn(Optional.empty());
        //act
        try {
            accommodationService.findAllByDestination("Bora-Bora");
        } catch (Exception e) {
            //assert
            assertEquals("Destination with name Bora-Bora does not exist!", e.getMessage());
        }
    }
    @Test
    void update() {
        when(accommodationRepository.findById(1l)).thenReturn(Optional.of(new Accommodation()));
        accommodationService.update(1l,new Accommodation());
        verify(accommodationRepository,times(1)).save(any());
    }
    @Test
    void save() {
        accommodationService.save(new Accommodation());
        verify(accommodationRepository,times(1)).save(any());
    }
    @Test
    void findAll() {
        accommodationService.findAll();
        verify(accommodationRepository,times(1)).findAll();
    }
    @Test
    void deleteById() {
        when(accommodationRepository.findById(1l)).thenReturn(Optional.of(new Accommodation()));
        accommodationService.deleteById(1l);
        verify(accommodationRepository, times(1)).delete(any());
    }
    @Test
    void deleteByInexistentId() {
        when(accommodationRepository.findById(1l)).thenReturn(Optional.empty());
        try {
            accommodationService.deleteById(1l);
        }catch(Exception e) {
            assertEquals("Accommodation with id 1 not found!",e.getMessage());
        }
    }
}