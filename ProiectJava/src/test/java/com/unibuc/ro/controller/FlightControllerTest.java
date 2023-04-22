package com.unibuc.ro.controller;

import com.google.gson.Gson;
import com.unibuc.ro.model.AirlineType;
import com.unibuc.ro.model.Flight;
import com.unibuc.ro.service.DestinationService;
import com.unibuc.ro.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FlightControllerTest {
    @Mock
    private FlightService flightService;
    @Mock
    private DestinationService destinationService;
    private static MockMvc mockMvc;
    @InjectMocks
    private static FlightController flightController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
    }

    @Test
    void getAllByPeriod() throws Exception {
        mockMvc.perform(get("/flights?startDate=2023-01-01&endDate=2023-02-02")).andExpect(status().isOk());
    }

    @Test
    void getAllByDest() throws Exception {
        mockMvc.perform(get("/flights/destination/Maldive")).andExpect(status().isOk());
    }

    @Test
    void addFlight() throws Exception {
        Gson gson = new Gson();
        mockMvc.perform(post("/flights").content(gson.toJson(new Flight(AirlineType.QATAR_AIRLINE, destinationService.findByName("Palma de Mallorca"), "08:00", "12:00", LocalDate.now().plusDays(10), 200l).toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(get("/flights/1")).andExpect(status().isOk());
    }
}