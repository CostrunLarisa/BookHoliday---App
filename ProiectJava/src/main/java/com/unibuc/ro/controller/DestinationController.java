package com.unibuc.ro.controller;

import com.unibuc.ro.model.Destination;
import com.unibuc.ro.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/destination")
@Validated
public class DestinationController {
    private final DestinationService destinationService;

    @Autowired
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @PostMapping
    public ResponseEntity<Destination> addDestination(@Valid @RequestBody Destination destination) {
        destinationService.save(destination);
        return ResponseEntity.created(URI.create("/" + destination.getId())).body(destination);
    }
    @GetMapping
    public ResponseEntity<List<Destination>> findAll() {
        return ResponseEntity.ok().body(destinationService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Destination> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(destinationService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        destinationService.deleteById(id);
        return ResponseEntity.ok().body("Deleted destination with id " + id.toString() + ".");
    }
    @PutMapping
    public ResponseEntity<Destination> updateById(@RequestBody Destination destination) {
        destinationService.update(destination.getId(),destination);
        return ResponseEntity.ok().body(destination);
    }
}
