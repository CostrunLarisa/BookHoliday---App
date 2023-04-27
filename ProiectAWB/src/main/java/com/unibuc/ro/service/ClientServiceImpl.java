package com.unibuc.ro.service;

import com.unibuc.ro.exceptions.ClientAlreadyRegisteredException;
import com.unibuc.ro.exceptions.ClientNotRegisteredException;
import com.unibuc.ro.model.Authority;
import com.unibuc.ro.model.Client;
import com.unibuc.ro.model.ClientRequest;
import com.unibuc.ro.model.ClientSignupRequest;
import com.unibuc.ro.repository.security.AuthorityRepository;
import com.unibuc.ro.repository.security.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class ClientServiceImpl extends AbstractService<Client> implements ClientService {
    private final ClientRepository clientRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    public ClientServiceImpl(ClientRepository repository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.clientRepository = repository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client findByEmailAndPassword(ClientRequest clientRequest) {
        String email = clientRequest.getEmail();
        String password = passwordEncoder.encode(clientRequest.getPassword());
        Optional<Client> client = clientRepository.findClientByEmailAndPassword(email, password);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new ClientNotRegisteredException();
        }
    }

    @Override
    public Client save(ClientSignupRequest client) {
        Optional<Client> clientFound = this.findByEmail(client.getEmail());
        if (clientFound.isPresent()) {
            throw new ClientAlreadyRegisteredException();
        }
        String password = passwordEncoder.encode(client.getPassword());
        Date birthDate = null;
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(client.getBirthDate() + " 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException();
        }
        Authority authority = authorityRepository.findByRole("ROLE_GUEST");
        LOGGER.info(authority.getRole());
        Client clientToBeSaved = Client.builder()
                .password(password)
                .birthDate(birthDate)
                .authority(authority)
                .email(client.getEmail())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .build();
        clientRepository.save(clientToBeSaved);
        return clientToBeSaved;
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return clientRepository.findClientByEmail(email);
    }
}
