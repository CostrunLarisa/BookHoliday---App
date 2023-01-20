package com.unibuc.ro.service;

import com.unibuc.ro.model.Address;
import com.unibuc.ro.model.AirlineType;
import com.unibuc.ro.model.Destination;
import com.unibuc.ro.model.Flight;
import com.unibuc.ro.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {
    @InjectMocks
    private FlightServiceImpl flightService;
    @Mock
    private FlightRepository flightRepository;

    @Test
    void findAllByPeriod() {
        //prepare
        Destination destination = new Destination("Spain");
        List<Flight> flights = List.of(new Flight(AirlineType.QATAR_AIRLINE,
                        destination,
                        "08:00", "12:00",LocalDate.parse("2022-01-01"), 200l),
                new Flight(AirlineType.QATAR_AIRLINE,
                        destination,
                        "08:00", "12:00",  LocalDate.parse("2023-02-02"), 200l));
        when(flightRepository.findByPeriod(LocalDate.parse("2022-01-01"), LocalDate.parse("2023-02-02"))).thenReturn(flights);
        //act
        List<Flight> flights1 = flightService.findAllByPeriod("2022-01-01", "2023-02-02");
        //assert
        assertEquals(flights, flights1);
    }

    @Test
    void findAllByDest() {
        //prepare
        Destination destination = new Destination("Spain");
        List<Flight> flights = List.of(new Flight(AirlineType.QATAR_AIRLINE,
                        destination,
                        "08:00", "12:00",LocalDate.parse("2022-01-01"), 200l),
                new Flight(AirlineType.QATAR_AIRLINE,
                        destination,
                        "08:00", "12:00",  LocalDate.parse("2023-02-02"), 200l));
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

        when(flightRepository.findByPeriod(LocalDate.parse("2022-01-01"), LocalDate.parse("2023-02-02"))).thenReturn(new ArrayList<>());
        //act
        List<Flight> flights1 = flightService.findAllByPeriod("2022-01-01", "2023-02-02");
        //assert
        assertEquals(new ArrayList<>(), flights1);
    }
    @Test
    void update() {
        when(flightRepository.findById(1l)).thenReturn(Optional.of(new Flight()));
        flightService.update(1l,new Flight());
        verify(flightRepository,times(1)).save(any());
    }
    @Test
    void save() {
        flightService.save(new Flight());
        verify(flightRepository,times(1)).save(any());
    }
    @Test
    void findAll() {
        flightService.findAll();
        verify(flightRepository,times(1)).findAll();
    }
    @Test
    void deleteById() {
        when(flightRepository.findById(1l)).thenReturn(Optional.of(new Flight()));
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