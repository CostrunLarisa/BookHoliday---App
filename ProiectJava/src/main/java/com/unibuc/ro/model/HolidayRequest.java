package com.unibuc.ro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import java.util.Date;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HolidayRequest {
    @FutureOrPresent
    private Date firstDay;
    @FutureOrPresent
    private Date endDay;
}
