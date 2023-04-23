package com.unibuc.ro.exceptions;

public class ClientAlreadyRegisteredException extends RuntimeException{
    public ClientAlreadyRegisteredException() {
        super("A client with this email is already registered!");
    }
}
