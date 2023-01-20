package com.unibuc.ro.controller;

import com.google.gson.Gson;
import com.unibuc.ro.model.*;
import com.unibuc.ro.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class HolidayControllerTest {
    private static MockMvc mockMvc;

    @Mock
    private HolidayService holidayService;

    @InjectMocks
    private static HolidayController holidayController;
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
    void addHoliday() throws Exception {
        Gson gson = new Gson();
        mockMvc.perform(post("/holiday")
                        .param("destinationId","1")
                        .param("clientId","1")
                        .content(gson.toJson(new HolidayRequest().toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cancelHoliday() throws Exception {
        mockMvc.perform(put("/holiday/cancellation/1")).andExpect(status().isOk());
    }

    @Test
    void addFlight() throws Exception {
        mockMvc.perform(put("/holiday/1/flight?flightId=1")).andExpect(status().isOk());
    }

    @Test
    void addAccommodation() throws Exception {
        mockMvc.perform(put("/holiday/1/accommodation?accommodationId=1")).andExpect(status().isOk());
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/holiday/1")).andExpect(status().isOk());
    }

    @Test
    void findAllByClient() throws Exception {
        mockMvc.perform(get("/holiday?clientId=1")).andExpect(status().isOk());
    }

    @Test
    void deleteAccommodation() throws Exception {
        mockMvc.perform(delete("/holiday/1/accommodation")).andExpect(status().isOk());
    }

    @Test
    void deleteFlight() throws Exception {
        mockMvc.perform(delete("/holiday/1/flight?flightId=1")).andExpect(status().isOk());
    }
}