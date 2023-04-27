package com.unibuc.ro.exceptions;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException() {
        super("Access denied!");
    }
}
