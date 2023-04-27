package com.unibuc.ro.repository;

import com.unibuc.ro.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    Optional<Accommodation> findByName(String name);
    @Query("select a from Accommodation a where a.capacity > 0 and a.destination.destinationName = :destinationName")
    List<Accommodation> findAllByDestination(String destinationName);
}
