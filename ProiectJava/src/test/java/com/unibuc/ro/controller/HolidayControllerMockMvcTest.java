package com.unibuc.ro.controller;

import com.unibuc.ro.model.Holiday;
import com.unibuc.ro.model.HolidayRequest;
import com.unibuc.ro.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "12345", roles = "ADMIN")
public class HolidayControllerMockMvcTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    HolidayService holidayService;

    @MockBean
    Model model;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void findAllByClientdMockMvc() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("currentHoliday", new Holiday());
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setSession(mockHttpSession);
        List<Holiday> holidays = List.of(new Holiday());

        when(holidayService.findAllByClient()).thenReturn(holidays);

        mockMvc.perform(MockMvcRequestBuilders.get("/holidays").session(mockHttpSession))
                .andExpect(status().isOk()).andExpect(view().name("holiday"))
                .andExpect(model().attribute("holidays",holidays));
    }
    @Test
    void deleteAccommodation() throws Exception {
        mockMvc.perform(delete("/holidays/1/accommodation/delete"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/holidays"))
                .andExpect(model().attribute("successMessage", "Accommodation was deleted!"));
    }
    @Test
    void addHolidayRequest() throws Exception {
        mockMvc.perform(post("/holidays/1", new HolidayRequest("2023-07-21", "2023-09-09")))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/holidays"))
                .andExpect(model().attribute("successMessage", "Period has been set!"));
    }

    @Test
    void testCancelHoliday() throws Exception {
        Holiday holiday = new Holiday();
        holiday.setCanceled(false);
        when(holidayService.findById(any())).thenReturn(holiday);
        mockMvc.perform(post("/holidays/cancellation/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/holidays"))
                .andExpect(model().attribute("successMessage", "Holiday was cancelled!"));

    }
    @Test
    void testAddAccommodationSadFlow() throws Exception {
        when(holidayService.addAccommodation(any(),any())).thenReturn(new Holiday());
        mockMvc.perform(post("/holidays/1/accommodation").param("accommodationId", "user"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/holidays"))
                .andExpect(model().attribute("errorMessage", "The field has to be a number!"));
    }
    @Test
    void testAddAccommodationHappyFlow() throws Exception {
        mockMvc.perform(post("/holidays/1/accommodation").param("accommodationId", "23"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/holidays"))
                .andExpect(model().attribute("successMessage", "Accommodation was added!"));
    }

    @Test
    void testDeleteAccommodation() throws Exception {
        mockMvc.perform(delete("/holidays/1/accommodation/delete"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/holidays"));
    }
}
