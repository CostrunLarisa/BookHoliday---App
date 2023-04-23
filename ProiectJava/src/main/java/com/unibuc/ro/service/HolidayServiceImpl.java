package com.unibuc.ro.service;

import com.unibuc.ro.exceptions.*;
import com.unibuc.ro.model.*;
import com.unibuc.ro.repository.*;
import com.unibuc.ro.repository.security.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HolidayServiceImpl extends AbstractService<Holiday> implements HolidayService {
    private final ClientRepository clientRepository;
    private final DestinationRepository destinationRepository;
    private final FlightRepository flightRepository;
    private final AccommodationRepository accommodationRepository;
    private final HolidayRepository holidayRepository;
    private Logger LOGGER = LoggerFactory.getLogger(HolidayServiceImpl.class);

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
    public Holiday updateById(Long id, HolidayRequest holiday) throws ParseException {
        Holiday holidayFound = this.findById(id);
        Date firstDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(holiday.getFirstDay() + " 00:00:00");
        Date endDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(holiday.getEndDay() + " 00:00:00");

        Date firstDayComp = new SimpleDateFormat("yyyy-MM-dd").parse(holiday.getFirstDay() );
        Date endDayComp = new SimpleDateFormat("yyyy-MM-dd").parse(holiday.getEndDay());
        LOGGER.info(firstDay.toString());
        LOGGER.info(endDay.toString());

        if (!(firstDayComp.before(endDayComp)) || firstDayComp.before(new Date())) {
            throw new HolidayCannotBeCancelledException();
        }
        Holiday newHoliday = Holiday.builder()
                .id(id)
                .firstDay(firstDay)
                .endDay(endDay)
                .client(holidayFound.getClient())
                .destination(holidayFound.getDestination())
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
        if (holiday.getFirstDay() != null && holiday.getFirstDay().before(new Date())) {
            throw new HolidayCannotBeCancelledException();
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
            if (!accommodation.get().getDestination().getDestinationName().equals(newHoliday.getDestination().getDestinationName())) {
                throw new AccommodationNotMatchingDestException();
            }
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
    public List<Holiday> findAllByClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if (auth != null) {
            email = auth.getName();
        }
        if (clientRepository.findClientByEmail(email).isEmpty()) {
            throw new ClientNotRegisteredException();
        }
        return holidayRepository.findAllByClientEmail(email);
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
            throw new EntityNotFoundException("The holiday does not have an accommodation!");
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

    @Override
    public Holiday saveByClientAndDest(String destinationName) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        Optional<Client> client = Optional.empty();
        Optional<Destination> destination;
        Holiday newHoliday = null;
        if (auth != null) {
            client = clientRepository.findClientByEmail(auth.getName());
        }
        if (!client.isEmpty()) {
            destination = destinationRepository.findByDestinationName(destinationName);
        } else {
            throw new ClientNotRegisteredException();
        }
        if (!destination.isEmpty()) {
            newHoliday = Holiday.builder().client(client.get()).destination(destination.get()).build();
        }

        return holidayRepository.save(newHoliday);
    }
}
