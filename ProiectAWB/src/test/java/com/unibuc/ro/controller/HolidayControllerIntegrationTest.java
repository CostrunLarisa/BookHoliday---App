package com.unibuc.ro.controller;

import com.unibuc.ro.model.*;
import com.unibuc.ro.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class HolidayControllerIntegrationTest {
    private static MockMvc mockMvc;

    @InjectMocks
    private static HolidayController holidayController;

    @Mock
    private HolidayService holidayService;
    private static Destination destination;
    private static Accommodation accommodation;
    private static Client client;
    private static Holiday holiday;

    @BeforeEach
    void setup() throws ParseException {
        destination = new Destination("Maldive");
        accommodation = new Accommodation(1l, AccommodationType.HOTEL, "Alegria",
                180l, "14:00", "10:00", 300,
                destination);
        client = new Client();
        holiday = new Holiday(1l, client, destination,
                new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01"),
                new SimpleDateFormat("yyyy-mm-dd").parse("2023-02-01"),
                new Accommodation(),
                new HashSet<Flight>(), false);
        mockMvc = MockMvcBuilders.standaloneSetup(holidayController).build();
    }

    @Test
    void addFlight() throws Exception {
        mockMvc.perform(put("/holidays/1/flight?flightId=1")).andExpect(status().isOk());
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/holidays/1")).andExpect(status().isOk());
    }

    @Test
    void findAllByClient() throws Exception {
        mockMvc.perform(get("/holidays"))
                .andExpect(status().isOk()).andExpect(view().name("holiday"));
    }

    @Test
    void deleteAccommodation() throws Exception {
        mockMvc.perform(delete("/holidays/1/accommodation/delete"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/holidays"));
    }

    @Test
    void deleteFlight() throws Exception {
        mockMvc.perform(delete("/holidays/1/flight?flightId=1"))
                .andExpect(status().isOk());
    }

    @Test
    void addHolidayRequest() throws Exception {
        mockMvc.perform(post("/holidays/1", new HolidayRequest("2023-07-21", "2023-09-09")))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/holidays"));
    }

    @Test
    void testAddAccommodation() throws Exception {
        mockMvc.perform(post("/holidays/1/accommodation","28"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/holidays"));
    }

    @Test
    void testDeleteAccommodation() throws Exception {
        mockMvc.perform(delete("/holidays/1/accommodation/delete"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/holidays"));

    }
}