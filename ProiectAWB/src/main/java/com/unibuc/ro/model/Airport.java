package com.unibuc.ro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String airportName;
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "airport_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private Set<Flight> flight;

    public Airport(String airportName, Set<Flight> flight) {
        this.airportName = airportName;
        this.flight = flight;
    }

    public Airport(String airportName) {
        this.airportName = airportName;
    }
}
