package com.unibuc.ro.controller;

import com.unibuc.ro.model.Destination;
import com.unibuc.ro.service.DestinationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/destinations")
@Validated
public class DestinationController {
    private final DestinationService destinationService;
    private Logger LOGGER = LoggerFactory.getLogger(DestinationController.class);


    @Autowired
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @PostMapping
    public ResponseEntity<Destination> addDestination(@Valid @RequestBody Destination destination) {
        destinationService.save(destination);
        return ResponseEntity.created(URI.create("/" + destination.getId())).body(destination);
    }
    @PostMapping("/add")
    public String addDestination(@Valid @ModelAttribute Destination destination, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.debug(bindingResult.getFieldErrors().toString());
            return "redirect:/destination";
        }
        destinationService.save(destination);
        LOGGER.info("Destination with id: " + destination.getId() +" was added in the database.");
        return "redirect:/destination";
    }
    @GetMapping
    public ResponseEntity<List<Destination>> findAll() {
        return ResponseEntity.ok().body(destinationService.findAll());
    }
    @GetMapping("/list")
    public ModelAndView getDestinations(@RequestParam(value = "errorMessage", required = false) String error,
                                        @RequestParam(value = "successMessage", required = false) String success) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("destination");
        modelAndView.addObject("destinations", destinationService.findAll());
        if (error != null) {
            modelAndView.addObject("errorMessage", error);
        }
        if (success != null) {
            modelAndView.addObject("successMessage", success);
        }
        return modelAndView;
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
