package com.unibuc.ro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter

public class HolidayId implements Serializable {
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "destination_id")
    private Long destinationId;

    private Client client;
    private  Destination destination;

    @Override
    public int hashCode() {
        return Objects.hash(clientId,destinationId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        HolidayId holidayId = (HolidayId) obj;
        return clientId.equals(holidayId.clientId) && destinationId.equals(holidayId.destinationId);
    }
}
