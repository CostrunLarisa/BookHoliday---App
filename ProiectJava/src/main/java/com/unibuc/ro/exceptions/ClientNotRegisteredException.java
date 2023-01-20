package com.unibuc.ro.exceptions;

public class ClientNotRegisteredException extends RuntimeException{
    public ClientNotRegisteredException() {
        super("This client is not registered on our platform!");
    }
}
