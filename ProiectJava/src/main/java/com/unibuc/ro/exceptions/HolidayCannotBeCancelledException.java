package com.unibuc.ro.exceptions;

public class HolidayCannotBeCancelledException extends RuntimeException{
    public HolidayCannotBeCancelledException() {
        super("Holiday has the first day in the past so it cannot be modified, added or cancelled!");
    }
}
