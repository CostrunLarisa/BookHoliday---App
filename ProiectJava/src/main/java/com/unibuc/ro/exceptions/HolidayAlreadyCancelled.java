package com.unibuc.ro.exceptions;

public class HolidayAlreadyCancelled extends RuntimeException{
    public HolidayAlreadyCancelled() {
        super("Holiday is already cancelled!");
    }
}
