package com.unibuc.ro.controller;

import com.unibuc.ro.model.Holiday;
import com.unibuc.ro.model.HolidayRequest;
import com.unibuc.ro.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/holiday")
public class HolidayController {
    private final HolidayService holidayService;

    @Autowired
    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PostMapping
    public ResponseEntity<Holiday> addHoliday(@RequestParam Long destinationId,
                                              @RequestParam Long clientId,
                                              @RequestBody HolidayRequest holiday) {
        Holiday newHoliday = holidayService.saveByClientAndDest(destinationId, clientId, holiday);
        return ResponseEntity.created(URI.create("/" + newHoliday.getId())).body(newHoliday);
    }

    @PutMapping("/cancellation/{id}")
    public ResponseEntity<String> cancelHoliday(@PathVariable Long id) {
        holidayService.cancelHoliday(id);
        return ResponseEntity.ok().body("Holiday was cancelled!");
    }

    @PutMapping("/{id}/flight")
    public ResponseEntity<Holiday> addFlight(@PathVariable Long id, @RequestParam Long flightId) {
        return ResponseEntity.ok().body(holidayService.addFlight(id, flightId));
    }

    @PutMapping("/{id}/accommodation")
    public ResponseEntity<Holiday> addAccommodation(@PathVariable Long id, @RequestParam Long accommodationId) {
        return ResponseEntity.ok().body(holidayService.addAccommodation(id, accommodationId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Holiday> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(holidayService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Holiday>> findAllByClient(@RequestParam Long clientId) {
        return ResponseEntity.ok().body(holidayService.findAllByClient(clientId));
    }

    @DeleteMapping("/{id}/accommodation")
    public ResponseEntity<Holiday> deleteAccommodation(@PathVariable Long id) {
        return ResponseEntity.ok().body(holidayService.deleteAccommodation(id));
    }

    @DeleteMapping("/{id}/flight")
    public ResponseEntity<Holiday> deleteFlight(@PathVariable Long id, @RequestParam Long flightId) {
        return ResponseEntity.ok().body(holidayService.deleteFlight(id, flightId));
    }
}
