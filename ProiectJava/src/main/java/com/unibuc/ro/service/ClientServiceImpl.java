package com.unibuc.ro.service;

import com.unibuc.ro.model.Client;
import com.unibuc.ro.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl extends AbstractService<Client> implements ClientService {
    private final ClientRepository clientRepository;
    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        super(repository);
        this.clientRepository = repository;
    }
}
