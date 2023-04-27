package com.unibuc.ro.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "accomodation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private AccommodationType accomodationType;
    private String name;
    private Long pricePerNight;
    private String checkInHour;
    private String checkOutHour;
    private Integer capacity;

    @ManyToOne
    @NotNull
    private Destination destination;

    public Accommodation(AccommodationType accomodationType, String name, Long pricePerNight,
                         String checkInHour, String checkOutHour, Integer capacity, Destination destination) {
        this.accomodationType = accomodationType;
        this.name = name;
        this.pricePerNight = pricePerNight;
        this.checkInHour = checkInHour;
        this.checkOutHour = checkOutHour;
        this.capacity = capacity;
        this.destination = destination;
    }
}
