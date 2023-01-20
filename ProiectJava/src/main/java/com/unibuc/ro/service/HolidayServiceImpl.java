package com.unibuc.ro.service;

import com.unibuc.ro.exceptions.ClientNotRegisteredException;
import com.unibuc.ro.exceptions.EntityNotFoundException;
import com.unibuc.ro.exceptions.HolidayAlreadyCancelledException;
import com.unibuc.ro.model.Accommodation;
import com.unibuc.ro.model.Flight;
import com.unibuc.ro.model.Holiday;
import com.unibuc.ro.model.HolidayRequest;
import com.unibuc.ro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HolidayServiceImpl extends AbstractService<Holiday> implements HolidayService {
    private final ClientRepository clientRepository;
    private final DestinationRepository destinationRepository;
    private final FlightRepository flightRepository;
    private final AccommodationRepository accommodationRepository;
    private final HolidayRepository holidayRepository;

    @Autowired
    public HolidayServiceImpl(HolidayRepository holidayRepository, ClientRepository clientRepository, DestinationRepository destinationRepository, FlightRepository flightRepository, AccommodationRepository accommodationRepository, HolidayRepository holidayRepository1) {
        super(holidayRepository);
        this.clientRepository = clientRepository;
        this.destinationRepository = destinationRepository;
        this.flightRepository = flightRepository;
        this.accommodationRepository = accommodationRepository;
        this.holidayRepository = holidayRepository1;
    }

    @Override
    public Holiday saveByClientAndDest(Long destinationId, Long clientId, HolidayRequest holiday) {
        if (clientRepository.findById(clientId).isEmpty()) {
            throw new ClientNotRegisteredException();
        }
        if (destinationRepository.findById(destinationId).isEmpty()) {
            throw new EntityNotFoundException("Destination with id " + destinationId + " does not exist!");
        }
        Holiday newHoliday = Holiday.builder()
                .firstDay(holiday.getFirstDay())
                .endDay(holiday.getEndDay())
                .client(clientRepository.findById(clientId).get())
                .destination(destinationRepository.findById(destinationId).get())
                .build();
        repository.save(newHoliday);
        return newHoliday;
    }

    @Override
    public void cancelHoliday(Long id) {
        Holiday holiday = findById(id);
        if (holiday.isCanceled()) {
            throw new HolidayAlreadyCancelledException();
        }
        if (holiday.getAccommodation() != null) {
            Optional<Accommodation> accommodation = accommodationRepository.findById(holiday.getAccommodation().getId());
            if (accommodation.isPresent()) {
                accommodation.get().setCapacity(accommodation.get().getCapacity() + 1);
                accommodationRepository.save(accommodation.get());
            }
        }
        holiday.setCanceled(true);
        save(holiday);
    }

    @Override
    public Holiday addFlight(Long holidayId, Long flightId) {
        Holiday newHoliday = findById(holidayId);
        Optional<Flight> flight = flightRepository.findById(flightId);
        if (flight.isPresent()) {
            List<Flight> flights = new ArrayList<>(newHoliday.getFlight());
            flights.add(flight.get());
            newHoliday.setFlight(new HashSet<>(flights));

        } else {
            throw new EntityNotFoundException("The selected flight does not exist!");
        }
        return save(newHoliday);
    }

    @Override
    public Holiday addAccommodation(Long holidayId, Long accommodationId) {
        Holiday newHoliday = findById(holidayId);
        Optional<Accommodation> accommodation = accommodationRepository.findById(accommodationId);
        if (accommodation.isPresent()) {
            accommodation.get().setCapacity(accommodation.get().getCapacity() - 1);
            accommodationRepository.save(accommodation.get());
            newHoliday.setAccommodation(accommodation.get());
        } else {
            throw new EntityNotFoundException("Selected accommodation does not exist!");
        }
        return save(newHoliday);
    }

    @Override
    public Holiday findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<Holiday> findAllByClient(Long clientId) {
        if (clientRepository.findById(clientId).isEmpty()) {
            throw new ClientNotRegisteredException();
        }
        return holidayRepository.findAllByClient_Id(clientId);
    }

    @Override
    public Holiday deleteAccommodation(Long id) {
        Holiday holiday = findById(id);
        if (holiday.getAccommodation() != null) {
            Optional<Accommodation> accommodation = accommodationRepository.findById(holiday.getAccommodation().getId());
            if (accommodation.isPresent()) {
                accommodation.get().setCapacity(accommodation.get().getCapacity() + 1);
                accommodationRepository.save(accommodation.get());
            }
        } else {
            throw new EntityNotFoundException("Accommodation with id " + id +" does not exist!");
        }
        Holiday newHoliday = Holiday.builder()
                .firstDay(holiday.getFirstDay())
                .endDay(holiday.getEndDay())
                .client(holiday.getClient())
                .flight(holiday.getFlight())
                .destination(holiday.getDestination())
                .isCanceled(holiday.isCanceled())
                .id(id)
                .build();
        return save(newHoliday);
    }

    @Override
    public Holiday deleteFlight(Long id, Long flightId) {
        Holiday holiday = findById(id);
        List<Flight> flights = new ArrayList<>(holiday.getFlight());
        Optional<Flight> flight = flightRepository.findById(flightId);
        if (flight.isPresent()) {
            flights.remove(flight.get());
            holiday.setFlight(new HashSet<>(flights));
        } else {
            throw new EntityNotFoundException("Flight with id " + flightId + " is not booked for this holiday!");
        }
        return save(holiday);
    }
}
