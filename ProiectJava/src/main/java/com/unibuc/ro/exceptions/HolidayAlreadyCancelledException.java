package com.unibuc.ro.exceptions;

public class HolidayAlreadyCancelledException extends RuntimeException{
    public HolidayAlreadyCancelledException() {
        super("Holiday is already cancelled!");
    }
}
