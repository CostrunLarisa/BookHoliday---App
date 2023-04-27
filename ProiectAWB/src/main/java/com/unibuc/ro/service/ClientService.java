package com.unibuc.ro.service;

import com.unibuc.ro.model.Client;
import com.unibuc.ro.model.ClientRequest;
import com.unibuc.ro.model.ClientSignupRequest;

import java.util.Optional;

public interface ClientService extends CrudService<Client>{
    Client findByEmailAndPassword(ClientRequest clientRequest);
    Client save(ClientSignupRequest clientSignupRequest);

    Optional<Client> findByEmail(String email);
}
