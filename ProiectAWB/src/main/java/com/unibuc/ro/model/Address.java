package com.unibuc.ro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Accommodation accommodation;

    private String kmFromCenter;

    private String streetAndNumber;

    public Address(Accommodation accommodation, String kmFromCenter, String streetAndNumber) {
        this.accommodation = accommodation;
        this.kmFromCenter = kmFromCenter;
        this.streetAndNumber = streetAndNumber;
    }
}
