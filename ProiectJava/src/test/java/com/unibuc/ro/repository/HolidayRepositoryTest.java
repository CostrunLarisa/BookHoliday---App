package com.unibuc.ro.repository;

import com.unibuc.ro.model.Client;
import com.unibuc.ro.model.Destination;
import com.unibuc.ro.model.Holiday;
import com.unibuc.ro.repository.security.ClientRepository;
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

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@DataJpaTest
@ActiveProfiles("postgres")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(value = false)
@Slf4j
public class HolidayRepositoryTest {
    @Autowired
    HolidayRepository holidayRepository;
    @Autowired
    DestinationRepository destinationRepository;

    @Autowired
    ClientRepository clientRepository;
    @Test
    @Transactional
    @Order(1)
    public void addHoliday() {
        Optional<Client> client = clientRepository.findById(55L);
        destinationRepository.save(new Destination("Maldive2"));
        Optional<Destination> destination = destinationRepository.findByDestinationName("Maldive2");
        Holiday holiday = new Holiday();
        holiday.setClient(client.get());
        holiday.setDestination(destination.get());
        Holiday savedHoliday = holidayRepository.save(holiday);
        assertFalse(holidayRepository.findById(savedHoliday.getId()).isEmpty());
        assertEquals(holiday.getDestination().getDestinationName(), savedHoliday.getDestination().getDestinationName());
    }
    @Test
    @Order(2)
    public void updateIsCancelled() {
        Optional<Holiday> holiday = holidayRepository.findByDestinationDestinationName("Maldive2");
        assertFalse(holiday.isEmpty());
        Holiday holidayToSave = holiday.get();
        holidayToSave.setCanceled(true);
        holidayRepository.save(holidayToSave);
    }

    @Test
    @Order(3)
    public void deleteByStatusAndName() {
        Optional<Holiday> holiday = holidayRepository.findByDestinationDestinationNameAndIsCanceledTrue("Maldive2");
        assertFalse(holiday.isEmpty());
        assertTrue(holiday.get().isCanceled());
        holidayRepository.deleteById(holiday.get().getId());
        destinationRepository.deleteById(destinationRepository.findByDestinationName("Maldive2").get().getId());
    }
}
