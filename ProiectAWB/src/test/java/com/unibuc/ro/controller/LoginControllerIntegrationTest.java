package com.unibuc.ro.controller;

import com.unibuc.ro.model.ClientRequest;
import com.unibuc.ro.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LoginControllerIntegrationTest {
    @InjectMocks
    private LoginController loginController;
    @Mock
    private ClientService clientService;
    private MockMvc mockMvc;
    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }
    @Test
    void login() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("login.html"));
    }

    @Test
    void getHomePage() throws Exception {
        mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("home.html"));

    }

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(post("/",new ClientRequest())).andExpect(status().isOk());
    }

    @Test
    void logoutPage() throws Exception {
        mockMvc.perform(get("/logout")).andExpect(status().isFound()).andExpect(view().name("redirect:/"));
    }
}