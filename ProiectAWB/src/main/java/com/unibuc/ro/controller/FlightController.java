package com.unibuc.ro.controller;

import com.unibuc.ro.model.Flight;
import com.unibuc.ro.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;
    private Logger LOGGER = LoggerFactory.getLogger(FlightController.class);
    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllByPeriod(@RequestParam String startDate, @RequestParam String endDate) {
        return ResponseEntity.ok().body(flightService.findAllByPeriod(startDate, endDate));
    }

    @GetMapping("/destination/{destinationName}")
    public ResponseEntity<List<Flight>> getAllByDest(@PathVariable String destinationName) {
        return ResponseEntity.ok().body(flightService.findAllByDest(destinationName));
    }

    @PostMapping
    public ResponseEntity<Flight> addFlight(@RequestBody @Valid Flight flight) {
        flightService.save(flight);
        LOGGER.info("Flight with id: " + flight.getId() +" was added in the database.");
        return ResponseEntity.created(URI.create("/" + flight.getId())).body(flight);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(flightService.findById(id));
    }

    @GetMapping("/flightsPaginated/{destinationName}")
    public ModelAndView getFlightPage(ModelAndView model,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam("page") Optional<Integer> page,
                               @PathVariable String destinationName) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Flight> flightPage = flightService.findPaginated(PageRequest.of(currentPage - 1, pageSize), destinationName);
        model.addObject("flightPage", flightPage);
        model.addObject("destinationName", destinationName);
        model.setViewName("flightsPaginated");
        return model;
    }

}
