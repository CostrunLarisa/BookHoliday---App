package com.unibuc.ro.repository;

import com.unibuc.ro.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findAllByClientEmail(String email);
    Optional<Holiday> findByDestinationDestinationName(String destinationName);
    Optional<Holiday> findByDestinationDestinationNameAndIsCanceledTrue(String destinationName);
}
