package com.unibuc.ro.exceptions;

public class CustomNumberFormatException extends RuntimeException{
    public CustomNumberFormatException() {
        super("The field has to be a number!");
    }
}
