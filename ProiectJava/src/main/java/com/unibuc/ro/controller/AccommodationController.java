package com.unibuc.ro.controller;

import com.unibuc.ro.model.Accommodation;
import com.unibuc.ro.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/accommodation")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @Autowired
    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping({"/destination/{destinationName}"})
    public ResponseEntity<List<Accommodation>> getAllByDest(@PathVariable String destinationName) {
        return ResponseEntity.ok().body(accommodationService.findAllByDestination(destinationName));
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Accommodation> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(accommodationService.findById(id));
    }
    @PostMapping
    public ResponseEntity<Accommodation> addAccommodation(@RequestBody Accommodation accommodation) {
        return ResponseEntity.created(URI.create("/" + accommodation.getId())).body(accommodation);
    }
}
