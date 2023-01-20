package com.unibuc.ro.controller;

import com.google.gson.Gson;
import com.unibuc.ro.model.Flight;
import com.unibuc.ro.model.Holiday;
import com.unibuc.ro.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class FlightControllerTest {
    @Mock
    private FlightService flightService;
    private static MockMvc mockMvc;
    @InjectMocks
    private static FlightController flightController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
    }

    @Test
    void getAllByPeriod() throws Exception {
        mockMvc.perform(get("/flight?startDate=2023-01-01&endDate=2023-02-02")).andExpect(status().isOk());
    }

    @Test
    void getAllByDest() throws Exception {
        mockMvc.perform(get("/flight/destination/Maldive")).andExpect(status().isOk());
    }

    @Test
    void addFlight() throws Exception {
        Gson gson = new Gson();
        mockMvc.perform(post("/flight").content(gson.toJson(new Flight().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(get("/flight/1")).andExpect(status().isOk());
    }
}