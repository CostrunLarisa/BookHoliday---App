package com.unibuc.ro.service;

import com.unibuc.ro.model.AirlineType;
import com.unibuc.ro.model.Destination;
import com.unibuc.ro.model.Flight;
import com.unibuc.ro.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {
    @InjectMocks
    private FlightServiceImpl flightService;
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private DestinationService destinationService;

    @Test
    void findAllByPeriod() throws ParseException {
        //prepare
        Destination destination = new Destination("Spain");
        List<Flight> flights = List.of(new Flight(AirlineType.QATAR_AIRLINE,
                        destination,
                        "08:00", "12:00",LocalDate.of(2022,1,1), 200l),
                new Flight(AirlineType.QATAR_AIRLINE,
                        destination,
                        "08:00", "12:00", LocalDate.of(2023,2,2), 200l));
        when(flightRepository.findByPeriod(LocalDate.of(2022,1,1), LocalDate.of(2023,2,2))).thenReturn(flights);
        //act
        List<Flight> flights1 = flightService.findAllByPeriod("2022-01-01","2023-02-02");
        //assert
        assertEquals(flights, flights1);
    }

    @Test
    void findAllByDest() throws ParseException {
        //prepare
        Destination destination = new Destination("Spain");
        List<Flight> flights = List.of(new Flight(AirlineType.QATAR_AIRLINE,
                        destination,
                        "08:00", "12:00", LocalDate.of(2022,1,1), 200l),
                new Flight(AirlineType.QATAR_AIRLINE,
                        destination,
                        "08:00", "12:00",  LocalDate.of(2023,2,2), 200l));
        when(flightRepository.findByDest("Spain")).thenReturn(flights);
        //act
        List<Flight> flights1 = flightService.findAllByDest("Spain");
        //assert
        assertEquals(flights, flights1);
    }
    @Test
    void findAllByInexistentDest() {
        when(flightRepository.findByDest("Spain")).thenReturn(new ArrayList<>());
        //act
        List<Flight> flights1 = flightService.findAllByDest("Spain");
        //assert
        assertEquals(new ArrayList<>(), flights1);
    }
    @Test
    void findAllByInexistentPeriod() {
        //prepare

        when(flightRepository.findByPeriod(LocalDate.of(2022,1,1), LocalDate.of(2023,2,2))).thenReturn(new ArrayList<>());
        //act
        List<Flight> flights1 = flightService.findAllByPeriod("2022-01-01", "2023-02-02");
        //assert
        assertEquals(new ArrayList<>(), flights1);
    }
    @Test
    void update() {
        Flight flight = new Flight(AirlineType.QATAR_AIRLINE, destinationService.findByName("Palma de Mallorca"), "08:00", "12:00", LocalDate.now().plusDays(10), 200l);
        when(flightRepository.findById(1l)).thenReturn(Optional.of(flight));
        flightService.update(1l,new Flight(AirlineType.QATAR_AIRLINE, destinationService.findByName("Palma de Mallorca"), "08:00", "12:00", LocalDate.now().plusDays(10), 200l));
        verify(flightRepository,times(1)).save(any());
    }
    @Test
    void save() {
        flightService.save(new Flight(AirlineType.QATAR_AIRLINE, destinationService.findByName("Palma de Mallorca"), "08:00", "12:00", LocalDate.now().plusDays(10), 200l));
        verify(flightRepository,times(1)).save(any());
    }
    @Test
    void findAll() {
        flightService.findAll();
        verify(flightRepository,times(1)).findAll();
    }
    @Test
    void deleteById() {
        Flight flight = new Flight(AirlineType.QATAR_AIRLINE, destinationService.findByName("Palma de Mallorca"), "08:00", "12:00", LocalDate.now().plusDays(10), 200l);
        when(flightRepository.findById(1l)).thenReturn(Optional.of(flight));
        flightService.deleteById(1l);
        verify(flightRepository, times(1)).delete(any());
    }
    @Test
    void deleteByInexistentId() {
        when(flightRepository.findById(1l)).thenReturn(Optional.empty());
        try {
            flightService.deleteById(1l);
        }catch(Exception e) {
            assertEquals("Flight with id 1 not found!",e.getMessage());
        }
    }
}