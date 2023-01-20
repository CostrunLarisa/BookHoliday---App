package com.unibuc.ro;

import com.unibuc.ro.model.*;
import com.unibuc.ro.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class BootstrapClass implements CommandLineRunner {
    private final AccommodationService accommodationService;
    private final DestinationService destinationService;
    private final FlightService flightService;
    private final AirportService airportService;
    private final AddressService addressService;

    @Autowired
    public BootstrapClass(AccommodationService accommodationService, DestinationService destinationService, FlightService flightService, AirportService airportService, AddressService addressService) {
        this.accommodationService = accommodationService;
        this.destinationService = destinationService;
        this.flightService = flightService;
        this.airportService = airportService;
        this.addressService = addressService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Accommodation> accomodations = accommodationService.findAll();
        List<Destination> destinations = destinationService.findAll();
        List<Flight> flights = flightService.findAll();
        List<Airport> airports = airportService.findAll();
        if (destinations.size() == 0) {
            destinationService.save(new Destination("Maldive"));
            destinationService.save(new Destination("Bali"));
            destinationService.save(new Destination("Palma de Mallorca"));
            destinationService.save(new Destination("Barcelona"));
            destinationService.save(new Destination("Sicily"));
            destinationService.save(new Destination("Dubai"));
            destinationService.save(new Destination("Bucuresti"));
            destinationService.save(new Destination("Timisoara"));
            destinationService.save(new Destination("Cluj-Napoca"));
        }
        if (accomodations.size() == 0) {
            accommodationService.save(new Accommodation(AccommodationType.HOTEL, "Alegria",
                    180l, "14:00", "10:00", 300,
                    destinationService.findByName("Maldive")));
            addressService.save(new Address(accommodationService.findByName("Alegria"), "10km", "Str.Alegrias 79"));

            accommodationService.save(new Accommodation(AccommodationType.HOTEL, "Meeru",
                    160l, "14:00", "10:00", 150,
                    destinationService.findByName("Maldive")));
            addressService.save(new Address(accommodationService.findByName("Meeru"), "8km", "Str.Punta 90"));

            accommodationService.save(new Accommodation(AccommodationType.HOUSE_ON_THE_WATER, "Furaveri",
                    220l, "12:00", "11:00", 80,
                    destinationService.findByName("Bali")));
            addressService.save(new Address(accommodationService.findByName("Furaveri"),"300m","Island 12"));

            accommodationService.save(new Accommodation(AccommodationType.HOSTEL, "SunnyWaves",
                    90l, "12:00", "10:00", 10,
                    destinationService.findByName("Bali")));
            addressService.save(new Address(accommodationService.findByName("SunnyWaves"),"2km","Kambugy 12"));

            accommodationService.save(new Accommodation(AccommodationType.GUEST_HOUSE, "Kandima",
                    120l, "11:00", "09:00", 30,
                    destinationService.findByName("Maldive")));
            addressService.save(new Address(accommodationService.findByName("Kandima"),"1km","Str.Leila 34"));

            accommodationService.save(new Accommodation(AccommodationType.HOTEL, "LuxuryDubai",
                    300l, "15:00", "10:00", 300,
                    destinationService.findByName("Dubai")));
            addressService.save(new Address(accommodationService.findByName("LuxuryDubai"),"3km","Str.Leila 12"));

            accommodationService.save(new Accommodation(AccommodationType.GUEST_HOUSE, "Ayada",
                    270l, "15:00", "11:00", 8,
                    destinationService.findByName("Dubai")));
            addressService.save(new Address(accommodationService.findByName("Ayada"),"11km","Str.Nepalo 99"));

            accommodationService.save(new Accommodation(AccommodationType.CAMPING, "LostInSicily",
                    60l, "10:00", "11:00", 200,
                    destinationService.findByName("Sicily")));
            addressService.save(new Address(accommodationService.findByName("LostInSicily"),"25km","Forest Camping"));

            accommodationService.save(new Accommodation(AccommodationType.HOSTEL, "SagradaFamily",
                    135l, "13:00", "09:00", 90,
                    destinationService.findByName("Barcelona")));
            addressService.save(new Address(accommodationService.findByName("SagradaFamily"),"300m","Str.Sagrada 2"));

            accommodationService.save(new Accommodation(AccommodationType.HOSTEL, "Arena",
                    285l, "12:30", "10:30", 120,
                    destinationService.findByName("Palma de Mallorca")));
            addressService.save(new Address(accommodationService.findByName("Arena"),"12km","Str.Landia 53"));
        }

        if (flights.size() == 0) {
            Flight flight1 = new Flight(AirlineType.QATAR_AIRLINE,
                    destinationService.findByName("Palma de Mallorca"),
                    "08:00", "12:00", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(10))), 200l);
            Flight flight2 = new Flight(AirlineType.QATAR_AIRLINE,
                    destinationService.findByName("Bucuresti"),
                    "12:00", "16:00", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(15))), 180l);
            Flight flight3 = new Flight(AirlineType.RYANNAIR,
                    destinationService.findByName("Maldive"),
                    "13:40", "23:40", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(20))), 80l);
            Flight flight4 = new Flight(AirlineType.RYANNAIR,
                    destinationService.findByName("Bucuresti"),
                    "10:35", "20:35", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(30))), 230l);
            Flight flight5 = new Flight(AirlineType.WIZZAIR,
                    destinationService.findByName("Cluj-Napoca"),
                    "13:47", "23:37", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(29))), 270l);
            Flight flight6 = new Flight(AirlineType.RYANNAIR,
                    destinationService.findByName("Dubai"),
                    "10:35", "20:35", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(45))), 255l);
            Flight flight7 = new Flight(AirlineType.WIZZAIR,
                    destinationService.findByName("Dubai"),
                    "10:35", "20:35", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(45))), 330l);
            Flight flight8 = new Flight(AirlineType.RYANNAIR,
                    destinationService.findByName("Timisoara"),
                    "17:15", "03:15", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(60))), 230l);
            Flight flight9 = new Flight(AirlineType.JETBLUE,
                    destinationService.findByName("Bali"),
                    "17:15", "07:47", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(37))), 420l);
            Flight flight10 = new Flight(AirlineType.HIGHSKY,
                    destinationService.findByName("Bucuresti"),
                    "23:53", "11:59", new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(LocalDate.now().plusDays(52))), 368l);
            flightService.save(flight1);
            flightService.save(flight2);
            flightService.save(flight3);
            flightService.save(flight4);
            flightService.save(flight5);
            flightService.save(flight6);
            flightService.save(flight7);
            flightService.save(flight8);
            flightService.save(flight9);
            flightService.save(flight10);

            if (airports.size() == 0) {
                airportService.save(new Airport("Henri-Coanda", Set.of(flight1, flight3, flight9)));
                airportService.save(new Airport("Cluj-Napoca", Set.of(flight7)));
                airportService.save(new Airport("Timisoara", Set.of(flight6)));
                airportService.save(new Airport("Oradea"));
                airportService.save(new Airport("Bacau"));
                airportService.save(new Airport("Spain", Set.of(flight2)));
                airportService.save(new Airport("Dubai", Set.of(flight8)));
                airportService.save(new Airport("Bali", Set.of(flight10)));
                airportService.save(new Airport("Maldive", Set.of(flight5, flight4)));
                airportService.save(new Airport("Barcelona"));
            }
        }
    }
}


