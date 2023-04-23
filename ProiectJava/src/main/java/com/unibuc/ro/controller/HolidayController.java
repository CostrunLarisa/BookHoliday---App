package com.unibuc.ro.controller;

import com.unibuc.ro.exceptions.InvalidSessionException;
import com.unibuc.ro.model.Holiday;
import com.unibuc.ro.model.HolidayRequest;
import com.unibuc.ro.service.HolidayService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/holidays")
public class HolidayController {
    private final HolidayService holidayService;
    private Logger LOGGER = LoggerFactory.getLogger(HolidayController.class);

    @Autowired
    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PostMapping("/{id}")
    public ModelAndView addHolidayRequest(@PathVariable("id") Long id, HolidayRequest holiday) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/holidays");

        holidayService.updateById(id, holiday);
        LOGGER.info("Period has been set!");
        modelAndView.addObject("successMessage", "Period has been set!");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addHolidayWithDestination(HttpServletRequest request, @RequestParam("session") String sessionId, @RequestParam("destinationName") String destinationName) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null || !httpSession.getId().equals(sessionId)) {
            throw new InvalidSessionException();
        }
        Holiday holiday = (Holiday) httpSession.getAttribute("currentHoliday");
        if (holiday != null) {
            modelAndView.addObject("errorMessage", "There is already an holiday in the booking process!");
            LOGGER.info(holiday.getDestination().getDestinationName());
        } else {
            Holiday newHoliday = holidayService.saveByClientAndDest(destinationName);
            httpSession.setAttribute("currentHoliday", newHoliday);
            modelAndView.addObject("successMessage", "The new holiday is in the booking process!");
        }
        modelAndView.setViewName("redirect:/destinations/list");
        return modelAndView;
    }

    @PostMapping("/cancellation/{id}")
    public ModelAndView cancelHoliday(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/holidays");
        modelAndView.addObject("successMessage", "Holiday was cancelled!");
        holidayService.cancelHoliday(id);
        return modelAndView;
    }

    @PutMapping("/{id}/flight")
    public ResponseEntity<Holiday> addFlight(@PathVariable Long id, @RequestParam Long flightId) {
        return ResponseEntity.ok().body(holidayService.addFlight(id, flightId));
    }

    @PostMapping("/{id}/accommodation")
    public ModelAndView addAccommodation(@PathVariable Long id, String accommodationId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/holidays");
        Long accommodationIdLong;
        try {
            accommodationIdLong = Long.valueOf(accommodationId);
        } catch (NumberFormatException e) {
            modelAndView.addObject("errorMessage", "The field has to be a number!");
            return modelAndView;
        }
        holidayService.addAccommodation(id, accommodationIdLong);
        modelAndView.addObject("successMessage", "Accommodation was added!");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Holiday> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(holidayService.findById(id));
    }

    @GetMapping
    public ModelAndView findAllByClient(@RequestParam(value = "errorMessage", required = false) String error,
                                        @RequestParam(value = "successMessage", required = false) String success) {
        List<Holiday> holidays = holidayService.findAllByClient();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("holiday");
        modelAndView.addObject("holidays", holidays);
        if (error != null) {
            modelAndView.addObject("errorMessage", error);
        }
        if (success != null) {
            modelAndView.addObject("successMessage", success);
        }
        return modelAndView;
    }

    @RequestMapping("/{id}/accommodation/delete")
    public ModelAndView deleteAccommodation(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/holidays");
        holidayService.deleteAccommodation(id);
        modelAndView.addObject("successMessage", "Accommodation was deleted!");
        return modelAndView;
    }

    @RequestMapping("/{id}/flight")
    public ResponseEntity<Holiday> deleteFlight(@PathVariable Long id, @RequestParam Long flightId) {
        return ResponseEntity.ok().body(holidayService.deleteFlight(id, flightId));
    }

    @GetMapping("/currentHoliday/{sessionId}")
    public ModelAndView finishBooking(HttpServletRequest request, @PathVariable("sessionId") String sessionId) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null || !httpSession.getId().equals(sessionId)) {
            throw new InvalidSessionException();
        }
        httpSession.removeAttribute("currentHoliday");
        modelAndView.addObject("successMessage", "Holiday was booked!");
        modelAndView.setViewName("redirect:/holidays");
        return modelAndView;
    }
}
