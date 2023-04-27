package com.unibuc.ro.service;

import com.unibuc.ro.model.*;
import com.unibuc.ro.repository.DestinationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DestinationServiceImplTest {

    @InjectMocks
    private DestinationServiceImpl destinationService;

    @Mock
    private DestinationRepository destinationRepository;

    @Test
    void saveHappyFlow() {
        //prepare
        Destination destination = new Destination(5l, "Hawaii", new HashSet<>());
        when(destinationRepository.save(destination)).thenReturn(destination);
        //act
        Destination result = destinationService.save(destination);
        //assert
        assertEquals("Hawaii", result.getDestinationName());
        assertEquals(new HashSet<Client>(), result.getAccommodations());
    }

    @Test
    void saveHappyFlowWithAccomodations() {
        //prepare
        Destination destination = new Destination(5l, "Hawaii", new HashSet<>());
        Accommodation accommodation = new Accommodation(1l, AccommodationType.HOTEL, "La vie", 123l, "12:00", "10:00", 100, destination);
        destination.getAccommodations().add(accommodation);

        when(destinationRepository.save(destination)).thenReturn(destination);
        //act
        Destination result = destinationService.save(destination);
        //assert
        assertEquals("Hawaii", result.getDestinationName());
        assertEquals(result.getAccommodations(), destination.getAccommodations());
        Assertions.assertTrue(result.getAccommodations().contains(accommodation));
    }

    @Test
    void findAll() {
        //prepare
        List<Destination> list = new ArrayList<>();
        list.add(new Destination(5l, "Hawaii", new HashSet<>()));
        list.add(new Destination(6l, "Maldive", new HashSet<>()));
        list.add(new Destination(7l, "Positano", new HashSet<>()));
        when(destinationRepository.findAll()).thenReturn(list);

        //act
        List<Destination> result = destinationService.findAll();

        //assert
        assertEquals(result, list);
    }

    @Test
    void findByIdHappyFlow() {
        //prepare
        Destination destination = new Destination(5l, "Hawaii", new HashSet<>());
        when(destinationRepository.findById(5l)).thenReturn(Optional.of(destination));
        //act
        Destination result = destinationService.findById(5l);
        //assert
        assertEquals(result.getDestinationName(), destination.getDestinationName());
        assertEquals(result.getId(), destination.getId());
    }

    @Test
    void findByIdException() {
        //prepare
        when(destinationRepository.findById(5l)).thenReturn(Optional.empty());
        //act
        try {
            destinationService.findById(5l);
        } catch (Exception e) {
            //assert
            assertEquals("Destination with id 5 not found!", e.getMessage());
        }
    }

    @Test
    void deleteByIdHappyFlow() {
        //prepare
        Destination destination = new Destination(5l, "Hawaii", new HashSet<>());
        when(destinationRepository.findById(5l)).thenReturn(Optional.of(destination));
        //act
        destinationService.deleteById(5l);
        //assert
        verify(destinationRepository, times(1)).delete(destination);
        verify(destinationRepository,times(1)).findById(5l);
    }

    @Test
    void deleteByIdException() {
        //prepare
        when(destinationRepository.findById(5l)).thenReturn(Optional.empty());
        //act
        try {
            destinationService.deleteById(5l);
        } catch (Exception e) {
            //assert
            assertEquals("Destination with id 5 not found!", e.getMessage());
        }
    }

    @Test
    void update() {
        //prepare
        Destination destination = new Destination(5l, "Hawaii", new HashSet<>());
        Destination newDestination = new Destination(5l, "Thailand", new HashSet<>());
        when(destinationRepository.findById(5l)).thenReturn(Optional.of(destination));
        //act
        Destination result = destinationService.update(5l, newDestination);
        //assert
        assertEquals(result.getDestinationName(), newDestination.getDestinationName());
        assertEquals(result.getId(), destination.getId());
    }

    @Test
    void updateException() {
        //prepare
        when(destinationRepository.findById(5l)).thenReturn(Optional.empty());
        //act
        try {
            destinationService.update(5l, new Destination(5l, "Hawaii", new HashSet<>()));
        } catch (Exception e) {
            //assert
            assertEquals("Destination with id 5 not found!", e.getMessage());
        }
    }

    @Test
    void findByNameHappyFlow() {
        //prepare
        Destination destination = new Destination(5l, "Maldive", new HashSet<>());
        when(destinationRepository.findByDestinationName("Maldive")).thenReturn(Optional.of(destination));
        //act
        Destination destination1 = destinationService.findByName("Maldive");
        //assert
        assertEquals(destination.getDestinationName(), destination1.getDestinationName());
        assertEquals(destination.getId(), destination1.getId());
    }

    @Test
    void findByInexistentName() {
        //prepare
        when(destinationRepository.findByDestinationName("Bora-Bora")).thenReturn(Optional.empty());
        //act
        try {
            destinationService.findByName("Bora-Bora");
        } catch (Exception e) {
            assertEquals("Destination with name Bora-Bora does not exist!", e.getMessage());
        }
    }
}