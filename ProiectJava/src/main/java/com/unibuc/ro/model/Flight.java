package com.unibuc.ro.model;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

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
    @NotNull
    private AirlineType airline;

    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    @NotNull(message = "Destination cannot be null!")
    private Destination destination;
    @NotNull
    private String departureHour;
    @NotNull
    private String arrivalHour;
    @FutureOrPresent(message = "The flight must be in the future or present!")
    @NotNull(message = "Date cannot be null!")
    @NotEmpty(message = "Date cannot be empty!")
    @NotBlank(message = "Date cannot be empty!")
    private Date date;

    private Long price;

    public Flight(AirlineType airline, Destination destination, String departureHour, String arrivalHour, Date date, Long price) {
        this.airline = airline;
        this.destination = destination;
        this.departureHour = departureHour;
        this.arrivalHour = arrivalHour;
        this.date = date;
        this.price = price;
    }
}
