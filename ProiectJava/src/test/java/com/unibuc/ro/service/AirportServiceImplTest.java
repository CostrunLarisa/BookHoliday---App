package com.unibuc.ro.service;

import com.unibuc.ro.model.*;
import com.unibuc.ro.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirportServiceImplTest {
    @InjectMocks
    private AirportServiceImpl airportService;
    @Mock
    private AirportRepository airportRepository;

    @Test
    void findByName() throws ParseException {
        //prepare
        Destination destination = new Destination("Maldive");
        Flight flight1 = new Flight(AirlineType.QATAR_AIRLINE,
                destination,
                "08:00", "12:00", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(10))), 200l);
        Flight flight2 = new Flight(AirlineType.QATAR_AIRLINE,
                destination,
                "12:00", "16:00", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(15))), 180l);
        Flight flight3 = new Flight(AirlineType.RYANNAIR,
                destination,
                "13:40", "23:40", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(20))), 80l);
        Airport airport = new Airport("Henri-Coanda", Set.of(flight1, flight2, flight3));

        when(airportRepository.findAirportByAirportName("Henri-Coanda")).thenReturn(Optional.of(airport));

        //act
        Airport airport1 = airportService.findByName("Henri-Coanda");
        //assert
        assertEquals(airport.getFlight(), airport1.getFlight());
    }

    @Test
    void findByInexistentName() {
        //prepare
        when(airportRepository.findAirportByAirportName("Henri-Coanda")).thenReturn(Optional.empty());

        //act
        try {
            airportService.findByName("Henri-Coanda");
        } catch (Exception e) {
            //assert
            assertEquals("Airport with name Henri-Coanda not found!", e.getMessage());
        }
    }
    @Test
    void update() {
        when(airportRepository.findById(1l)).thenReturn(Optional.of(new Airport()));
        airportService.update(1l,new Airport());
        verify(airportRepository,times(1)).save(any());
    }
    @Test
    void save() {
        airportService.save(new Airport());
        verify(airportRepository,times(1)).save(any());
    }
    @Test
    void findAll() {
        airportService.findAll();
        verify(airportRepository,times(1)).findAll();
    }
    @Test
    void deleteById() {
        when(airportRepository.findById(1l)).thenReturn(Optional.of(new Airport()));
        airportService.deleteById(1l);
        verify(airportRepository, times(1)).delete(any());
    }
    @Test
    void deleteByInexistentId() {
        when(airportRepository.findById(1l)).thenReturn(Optional.empty());
        try {
            airportService.deleteById(1l);
        }catch(Exception e) {
            assertEquals("Airport with id 1 not found!",e.getMessage());
        }
    }
}