package com.unibuc.ro.repository;

import com.unibuc.ro.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
    @Query("select f from Flight f where f.date = :startDate or f.date = :endDate")
    List<Flight> findByPeriod(LocalDate startDate, LocalDate endDate);

    @Query("select f from Flight f where f.destination.destinationName = :destinationName")
    List<Flight> findByDest(String destinationName);
    @Query("select f from Flight f where f.destination.destinationName = :destinationName")
    Page<Flight> findAllPaginatedByDest(Pageable pageable, String destinationName);
}
