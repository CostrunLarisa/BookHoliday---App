package com.unibuc.ro.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private AirlineType airline;

    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Destination destination;

    private String departureHour;

    private String arrivalHour;

    private LocalDate date;

    private Long price;

    public Flight(AirlineType airline, Destination destination, String departureHour, String arrivalHour, LocalDate date, Long price) {
        this.airline = airline;
        this.destination = destination;
        this.departureHour = departureHour;
        this.arrivalHour = arrivalHour;
        this.date = date;
        this.price = price;
    }
}
