package com.unibuc.ro.service;

import com.unibuc.ro.model.Holiday;
import com.unibuc.ro.model.HolidayRequest;

import java.util.List;

public interface HolidayService {
    Holiday saveByClientAndDest(Long destinationId, Long clientId, HolidayRequest holiday);

    void cancelHoliday(Long id);

    Holiday addFlight(Long holidayId,Long flightId);

    Holiday addAccommodation(Long holidayId,Long accommodationId);

    Holiday findById(Long id);

    List<Holiday> findAllByClient(Long clientId);

    Holiday deleteAccommodation(Long id);

    Holiday deleteFlight(Long id, Long flightId);
}
