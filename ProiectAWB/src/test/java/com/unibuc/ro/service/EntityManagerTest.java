package com.unibuc.ro.service;

import com.unibuc.ro.model.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("h2")
public class EntityManagerTest {
    private Logger LOGGER = LoggerFactory.getLogger(EntityManagerTest.class);
    @Autowired
    private EntityManager entityManager;

    @Test
    public void testFindDestination() {
        List<Destination> destinations = entityManager.createQuery("select d from Destination d", Destination.class).getResultList();
        LOGGER.info(String.valueOf(destinations.size()));
        Destination destination = entityManager.find(Destination.class,1l );
        assertEquals("Maldive",destination.getDestinationName());
    }
    @Test
    public void findAccommodation() {
     Accommodation accommodation = entityManager.find(Accommodation.class, 10l);
     assertEquals("Alegria", accommodation.getName());
    }
    @Test
    @Transactional
    public void updateAccommodation() {
     Accommodation accommodation = entityManager.find(Accommodation.class, 10l);
     accommodation.setCheckInHour("13:00");
     entityManager.persist(accommodation);
     accommodation = entityManager.find(Accommodation.class, 10l);
     assertEquals("13:00", accommodation.getCheckInHour());
     entityManager.flush();
    }
    @Test
    public void findFlight() {
     Flight flight = entityManager.find(Flight.class, 30l);
     assertEquals(3, flight.getDestination().getId());
    }
    @Test
    public void findAirport() {
     Airport airport = entityManager.find(Airport.class, 40l);
     assertEquals("Henri-Coanda", airport.getAirportName());
    }
    @Test
    public void findAddress() {
     Address address = entityManager.find(Address.class, 11l);
     assertEquals("10km", address.getKmFromCenter());
    }

}
