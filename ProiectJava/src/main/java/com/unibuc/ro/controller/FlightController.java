package com.unibuc.ro.controller;

import com.unibuc.ro.model.Flight;
import com.unibuc.ro.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import java.net.URI;
import java.sql.Date;
import java.util.List;

@Validated
@RestController
@RequestMapping("/flight")
public class FlightController {
    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllByPeriod(@FutureOrPresent @RequestParam Date startDate, @FutureOrPresent @RequestParam Date endDate) {
        return ResponseEntity.ok().body(flightService.findAllByPeriod(startDate, endDate));
    }

    @GetMapping("/destination/{destinationName}")
    public ResponseEntity<List<Flight>> getAllByDest(@PathVariable String destinationName) {
        return ResponseEntity.ok().body(flightService.findAllByDest(destinationName));
    }

    @PostMapping
    public ResponseEntity<Flight> addFlight(@RequestBody @Valid Flight flight) {
        flightService.save(flight);
        return ResponseEntity.created(URI.create("/" + flight.getId())).body(flight);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(flightService.findById(id));
    }
}
