package com.unibuc.ro.repository;

import com.unibuc.ro.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport,Long> {
    Optional<Airport> findAirportByAirportName(String name);
}
