package com.unibuc.ro.controller;

import com.google.gson.Gson;
import com.unibuc.ro.model.Accommodation;
import com.unibuc.ro.model.Destination;
import com.unibuc.ro.service.DestinationService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DestinationControllerTest {
    @InjectMocks
    private static DestinationController destinationController;
    @Mock
    private DestinationService destinationService;
    private static MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(destinationController).build();
    }

    @Test
    void addDestination() throws Exception {
        Gson gson = new Gson();
        mockMvc.perform(post("/destinations").content(gson.toJson(new Destination().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/destinations")).andExpect(status().isOk());
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/destinations/1")).andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/destinations/1")).andExpect(status().isOk());
    }

    @Test
    void updateById() throws Exception {
        Gson gson = new Gson();
        mockMvc.perform(put("/destinations").content(gson.toJson(new Destination().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
}