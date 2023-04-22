package com.unibuc.ro.exceptions;

public class AccommodationNotMatchingDestException extends RuntimeException{
    public AccommodationNotMatchingDestException() {
        super("This accommodation cannot be found at the selected destination!");
    }
}
