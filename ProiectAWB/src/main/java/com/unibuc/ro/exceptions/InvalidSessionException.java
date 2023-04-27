package com.unibuc.ro.exceptions;

public class InvalidSessionException extends RuntimeException{
    public InvalidSessionException() {
        super("The current session has expired or is not available!");
    }
}
