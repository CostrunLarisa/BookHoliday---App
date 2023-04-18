package com.unibuc.ro.exceptions;

public class ClientNotRegisteredException extends RuntimeException{
    public ClientNotRegisteredException() {
        super("The client with the provided username and password does not exist or is not registered.");
    }
}
