package com.unibuc.ro.service;

import com.unibuc.ro.model.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("postgres")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class EntityManagerPostgresTest {
    @Autowired
    private EntityManager entityManager;
    private Logger LOGGER = LoggerFactory.getLogger(EntityManagerPostgresTest.class);
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
