package com.unibuc.ro.repository;

import com.unibuc.ro.model.Destination;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@ActiveProfiles("postgres")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(value = false)
@Slf4j
public class DestinationRepositoryTest {
    @Autowired
    DestinationRepository destinationRepository;
    @Test
    @Order(1)
    public void addDestination() {
        destinationRepository.save(new Destination("Maldive3"));
    }
    @Test
    @Order(2)
    public void  findByName() {
        Optional<Destination> destination = destinationRepository.findByDestinationName("Maldive3");
        assertFalse(destination.isEmpty());
        log.info(destination.get().getId().toString() +": " +destination.get().getDestinationName());
    }
    @Test
    @Order(3)
    public void deleteById() {
        Optional<Destination> destination = destinationRepository.findByDestinationName("Maldive3");
        assertFalse(destination.isEmpty());
        log.info(destination.get().getId().toString() +": " +destination.get().getDestinationName());
        destinationRepository.deleteById(destination.get().getId());
        Optional<Destination> destinationDeleted = destinationRepository.findByDestinationName("Maldive3");
        assertTrue(destinationDeleted.isEmpty());
    }
}
