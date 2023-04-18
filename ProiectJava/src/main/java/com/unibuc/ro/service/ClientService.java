package com.unibuc.ro.service;

import com.unibuc.ro.model.Client;
import com.unibuc.ro.model.ClientRequest;

public interface ClientService extends CrudService<Client>{
    Client findByEmailAndPassword(ClientRequest clientRequest);
}
