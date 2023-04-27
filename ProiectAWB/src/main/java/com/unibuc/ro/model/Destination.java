package com.unibuc.ro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull(message = "Destination must have a name!")
    private String destinationName;

    @OneToMany(mappedBy = "destination")
    @JsonIgnore
    private Set<Accommodation> accommodations;

    @OneToMany(mappedBy = "destination")
    @JsonIgnore
    private Set<Holiday> holidays;

    public Destination(Long id, String destinationName, Set<Accommodation> accommodations) {
        this.id = id;
        this.destinationName = destinationName;
        this.accommodations = accommodations;
    }

    public Destination(String destinationName) {
        this.destinationName = destinationName;
    }
}
