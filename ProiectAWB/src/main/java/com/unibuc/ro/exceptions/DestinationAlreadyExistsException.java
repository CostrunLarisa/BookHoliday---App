package com.unibuc.ro.exceptions;

public class DestinationAlreadyExistsException extends RuntimeException{
    public DestinationAlreadyExistsException() {
        super("Destination already exists!");
    }
}
