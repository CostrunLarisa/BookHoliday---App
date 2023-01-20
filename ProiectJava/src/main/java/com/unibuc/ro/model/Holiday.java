package com.unibuc.ro.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(updatable = false, nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Destination destination;

    private Date firstDay;

    private Date endDay;
    @ManyToOne
    private Accommodation accommodation;
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "holiday_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private Set<Flight> flight = new HashSet<>();
    @ColumnDefault("false")
    private boolean isCanceled = false;

    public Holiday(Destination destination, Client client, Date firstDay, Date endDay) {
        this.destination = destination;
        this.client = client;
        this.firstDay = firstDay;
        this.endDay = endDay;
    }

    public Holiday(Client client, Destination destination, Date firstDay, Date endDay, boolean isCanceled) {
        this.client = client;
        this.destination = destination;
        this.firstDay = firstDay;
        this.endDay = endDay;
        this.isCanceled = isCanceled;
    }

}
