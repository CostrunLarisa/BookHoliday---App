package com.unibuc.ro.service;

import com.unibuc.ro.exceptions.ClientNotRegisteredException;
import com.unibuc.ro.model.Client;
import com.unibuc.ro.model.ClientRequest;
import com.unibuc.ro.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl extends AbstractService<Client> implements ClientService {
    private final ClientRepository clientRepository;
    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        super(repository);
        this.clientRepository = repository;
    }

    @Override
    public Client findByEmailAndPassword(ClientRequest clientRequest) {
        String email = clientRequest.getEmail();
        String password = clientRequest.getPassword();
        Optional<Client> client = clientRepository.findClientByEmailAndPassword(email, password);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new ClientNotRegisteredException();
        }
    }
}
