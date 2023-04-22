package com.unibuc.ro.controller;

import com.google.gson.Gson;
import com.unibuc.ro.model.Accommodation;
import com.unibuc.ro.model.Client;
import com.unibuc.ro.service.AccommodationService;
import com.unibuc.ro.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccommodationControllerTest {
    @InjectMocks
    private static AccommodationController accommodationController;
    @Mock
    private AccommodationService accommodationService;
    private static MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(accommodationController).build();
    }

    @Test
    void getAllByDest() throws Exception {
        mockMvc.perform(get("/accommodations/destination/Maldive")).andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(get("/accommodations/1")).andExpect(status().isOk());
    }

    @Test
    void addAccommodation() throws Exception {
        Gson gson = new Gson();
        mockMvc.perform(post("/accommodations").content(gson.toJson(new Accommodation().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
}