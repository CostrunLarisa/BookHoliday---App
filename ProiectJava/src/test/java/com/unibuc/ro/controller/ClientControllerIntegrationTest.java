package com.unibuc.ro.controller;

import com.google.gson.Gson;
import com.unibuc.ro.model.Client;
import com.unibuc.ro.model.ClientSignupRequest;
import com.unibuc.ro.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ClientControllerIntegrationTest {
    @InjectMocks
    private static ClientController clientController;
    @Mock
    private ClientService clientService;
    private static MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void addClient() throws Exception {
        mockMvc.perform(post("/client", new ClientSignupRequest())).andExpect(status().isFound()).andExpect(view().name("redirect:/login"));

    }
    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/client/1")).andExpect(status().isOk());
    }
    @Test
    void showErrorPage() throws Exception {
        mockMvc.perform(get("/client/notFound")).andExpect(status().isOk());

    }
}