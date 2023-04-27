package com.unibuc.ro.controller;

import com.google.gson.Gson;
import com.unibuc.ro.model.Destination;
import com.unibuc.ro.service.DestinationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DestinationControllerIntegrationTest {
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
    @WithMockUser(username = "admin", password = "12345", roles = "ADMIN")
    void addDestinationByAdmin() throws Exception {
        mockMvc.perform(post("/destinations/add", new Destination()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/destinations/list"));
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
    @Test
    void addDestination() throws Exception {
        mockMvc.perform(post("/destinations/add", new Destination()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/destinations/list"));
    }

    @Test
    void getDestinations() throws Exception {
        mockMvc.perform(get("/destinations/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("destination"));
    }
}