package com.unibuc.ro.service;

import com.unibuc.ro.model.*;
import com.unibuc.ro.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HolidayServiceImplTest {
    @InjectMocks
    private HolidayServiceImpl holidayService;
    @Mock
    private HolidayRepository holidayRepository;
    @Mock
    private AccommodationRepository accommodationRepository;
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private DestinationRepository destinationRepository;
// TODO:
//    @Test
//    void saveByInexistentClientAndDest() {
//        when(clientRepository.findById(1l)).thenReturn(Optional.empty());
//        try {
//            holidayService.saveByClientAndDest(2l, 1l, new HolidayRequest());
//        } catch (Exception e) {
//            assertEquals("The client with the provided username and password does not exist or is not registered.", e.getMessage());
//        }
//    }

//    @Test
//    void saveByClientAndInexistentDest() {
//        when(clientRepository.findById(1l)).thenReturn(Optional.of(new Client()));
//        when(destinationRepository.findById(2l)).thenReturn(Optional.empty());
//        try {
//            holidayService.saveByClientAndDest(2l, 1l, new HolidayRequest());
//        } catch (Exception e) {
//            assertEquals("Destination with id 2 does not exist!", e.getMessage());
//        }
//    }
// TODO:
//    @Test
//    void saveByClientAndDest() {
//        when(clientRepository.findById(1l)).thenReturn(Optional.of(new Client()));
//        when(destinationRepository.findById(2l)).thenReturn(Optional.of(new Destination()));
//
//        Holiday holiday = holidayService.saveByClientAndDest(2l, 1l, new HolidayRequest());
//        verify(holidayRepository, times(1)).save(any());
//    }

    // TODO:

//    @Test
//    void cancelHoliday() {
//        Holiday holiday = new Holiday();
//        Destination destination = new Destination("Maldive");
//        Accommodation accommodation = new Accommodation(1l, AccommodationType.HOTEL, "Alegria",
//                180l, "14:00", "10:00", 300,
//                destination);
//        holiday.setAccommodation(accommodation);
//        when(holidayRepository.findById(1l)).thenReturn(Optional.of(holiday));
//        when(accommodationRepository.findById(any())).thenReturn(Optional.of(accommodation));
//        holidayService.cancelHoliday(1l);
//        verify(holidayRepository, times(1)).save(holiday);
//
//        verify(accommodationRepository, times(1)).save(any());
//    }

    @Test
    void cancelInvalidHoliday() {
        Holiday holiday = new Holiday();
        holiday.setCanceled(true);
        when(holidayRepository.findById(1l)).thenReturn(Optional.of(holiday));
        try {
            holidayService.cancelHoliday(1l);
        } catch (Exception e) {
            assertEquals("Holiday is already cancelled!", e.getMessage());
        }
    }

    @Test
    void addFlight() throws ParseException {
        Destination destination = new Destination("Maldive");
        Holiday holiday = new Holiday();
        Flight flight = new Flight(AirlineType.QATAR_AIRLINE,
                destination,
                "08:00", "12:00", LocalDate.now().plusDays(10), 200l);
        holiday.setFlight(Set.of(flight));
        when(holidayRepository.findById(1L)).thenReturn(Optional.of(holiday));
        when(flightRepository.findById(2L)).thenReturn(Optional.of(flight));
        //act
        Holiday holiday1 = holidayService.addFlight(1l, 2l);
        //assert
        assertEquals(holiday.getFlight(), holiday1.getFlight());
    }

    @Test
    void addInexistentFlight() {
        when(holidayRepository.findById(1L)).thenReturn(Optional.of(new Holiday()));
        when(flightRepository.findById(2L)).thenReturn(Optional.empty());
        //act
        try {
            holidayService.addFlight(1l, 2l);
        } catch (Exception e) {
            //assert
            assertEquals("The selected flight does not exist!", e.getMessage());
        }
    }

    @Test
    void addFlightToInexistentHoliday() {
        when(holidayRepository.findById(1L)).thenReturn(Optional.empty());
        //act
        try {
            holidayService.addFlight(1l, 2l);
        } catch (Exception e) {
            //assert
            assertEquals("Holiday with id 1 not found!", e.getMessage());
        }
    }

    @Test
    void addAccommodation() {
        //prepare
        Holiday holiday = new Holiday();
        Destination destination = new Destination("Maldive");
        Accommodation accommodation = new Accommodation(1l, AccommodationType.HOTEL, "Alegria",
                180l, "14:00", "10:00", 300,
                destination);
        when(accommodationRepository.findById(1l)).thenReturn(Optional.of(accommodation));
        when(holidayRepository.findById(2l)).thenReturn(Optional.of(holiday));
        //act
        Holiday holiday1 = holidayService.addAccommodation(2l, 1l);
        //assert
        assertEquals(accommodation.getName(), holiday1.getAccommodation().getName());
        verify(accommodationRepository, times(1)).save(any());
        verify(accommodationRepository, times(1)).findById(any());

    }

    @Test
    void addInvalidAccommodation() {
        Holiday holiday = new Holiday();
        when(accommodationRepository.findById(1l)).thenReturn(Optional.empty());
        when(holidayRepository.findById(2l)).thenReturn(Optional.of(holiday));

        try {
            holidayService.addAccommodation(2l, 1l);
        } catch (Exception e) {
            assertEquals("Selected accommodation does not exist!", e.getMessage());
        }
    }

    @Test
    void addAccommodationInvalidHoliday() {
        when(holidayRepository.findById(2l)).thenReturn(Optional.empty());

        try {
            holidayService.addAccommodation(2l, 1l);
        } catch (Exception e) {
            assertEquals("Holiday with id 2 not found!", e.getMessage());
        }
    }

    @Test
    void findByInvalidId() {
        when(holidayRepository.findById(2l)).thenReturn(Optional.empty());

        try {
            holidayService.findById(2l);
        } catch (Exception e) {
            assertEquals("Holiday with id 2 not found!", e.getMessage());
        }
    }
    // TODO:

//    @Test
//    void findAllByClient() {
//        when(clientRepository.findById(1l)).thenReturn(Optional.of(new Client()));
//        List<Holiday> holidayList = holidayService.findAllByClient(1l);
//        verify(holidayRepository, times(1)).findAllByClient_Id(1l);
//        assertEquals(new ArrayList<>(), holidayList);
//    }

    //TODO

//    @Test
//    void findAllByInexistentClient() {
//        when(clientRepository.findById(1l)).thenReturn(Optional.empty());
//        try {
//            holidayService.findAllByClient(1l);
//        } catch (Exception e) {
//            assertEquals("The client with the provided username and password does not exist or is not registered.", e.getMessage());
//        }
//    }

    @Test
    void deleteAccommodation() {
        Holiday holiday = new Holiday();
        Destination destination = new Destination("Maldive");
        Accommodation accommodation = new Accommodation(1l, AccommodationType.HOTEL, "Alegria",
                180l, "14:00", "10:00", 300,
                destination);
        holiday.setAccommodation(accommodation);
        when(holidayRepository.findById(1l)).thenReturn(Optional.of(holiday));
        when(accommodationRepository.findById(any())).thenReturn(Optional.of(accommodation));
        Holiday holiday2 = holidayService.deleteAccommodation(1l);
        verify(holidayRepository, times(1)).save(any());
        verify(accommodationRepository, times(1)).save(any());
        verify(accommodationRepository, times(1)).findById(any());
    }

    @Test
    void deleteInexistentAccommodation() {
        Holiday holiday = new Holiday();
        Destination destination = new Destination("Maldive");
        Accommodation accommodation = new Accommodation(1l, AccommodationType.HOTEL, "Alegria",
                180l, "14:00", "10:00", 300,
                destination);
        holiday.setAccommodation(accommodation);
        when(holidayRepository.findById(1l)).thenReturn(Optional.of(holiday));
        when(accommodationRepository.findById(any())).thenReturn(Optional.empty());
        try {
            Holiday holiday2 = holidayService.deleteAccommodation(1l);
        } catch (Exception e) {
            assertEquals("Accommodation with id 1 not found!", e.getMessage());
        }
    }
    @Test
    void deleteUnsetAccommodation() {
        Holiday holiday = new Holiday();
        when(holidayRepository.findById(1l)).thenReturn(Optional.of(holiday));
        try {
            Holiday holiday2 = holidayService.deleteAccommodation(1l);
        } catch (Exception e) {
            assertEquals("Accommodation with id 1 does not exist!", e.getMessage());
        }
    }

    @Test
    void deleteFlight() throws ParseException {
        //prepare
        Destination destination = new Destination("Maldive");
        Holiday holiday = new Holiday();
        Flight flight = new Flight(AirlineType.QATAR_AIRLINE,
                destination,
                "08:00", "12:00", LocalDate.now().plusDays(10), 200l);
        holiday.setFlight(Set.of(flight));
        when(holidayRepository.findById(1L)).thenReturn(Optional.of(holiday));
        when(flightRepository.findById(2L)).thenReturn(Optional.of(flight));
        //act
        Holiday holiday1 = holidayService.deleteFlight(1l, 2l);
        //assert
        assertEquals(new HashSet<>(), holiday1.getFlight());
    }

    @Test
    void deleteUnbookedFlight() {
        when(holidayRepository.findById(1l)).thenReturn(Optional.of(new Holiday()));
        when(flightRepository.findById(2l)).thenReturn(Optional.empty());
        try {
            holidayService.deleteFlight(1l, 2l);
        } catch (Exception e) {
            assertEquals("Flight with id 2 is not booked for this holiday!", e.getMessage());
        }
    }

    @Test
    void deleteById() {
        when(holidayRepository.findById(1l)).thenReturn(Optional.of(new Holiday()));
        holidayService.deleteById(1l);
        verify(holidayRepository, times(1)).delete(any());
    }

    @Test
    void deleteByInexistentId() {
        when(holidayRepository.findById(1l)).thenReturn(Optional.empty());
        try {
            holidayService.deleteById(1l);
        } catch (Exception e) {
            assertEquals("Holiday with id 1 not found!", e.getMessage());
        }
    }

    @Test
    void findById() throws ParseException {
        when(holidayRepository.findById(1l)).thenReturn(Optional.of(new Holiday(new Destination(), new Client(), new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01"), new SimpleDateFormat("yyyy-mm-dd").parse("2023-02-02"))));
        Holiday holiday = holidayService.findById(1l);
        assertEquals(new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01"), holiday.getFirstDay());
        assertEquals(new SimpleDateFormat("yyyy-mm-dd").parse("2023-02-02"), holiday.getEndDay());
    }

    @Test
    void findAll() {
        holidayService.findAll();
        verify(holidayRepository, times(1)).findAll();
    }

    @Test
    void update() {
        when(holidayRepository.findById(1l)).thenReturn(Optional.of(new Holiday()));
        holidayService.update(1l, new Holiday());
        verify(holidayRepository, times(1)).save(any());
    }

    @Test
    void save() {
        holidayService.save(new Holiday());
        verify(holidayRepository, times(1)).save(any());
    }

}