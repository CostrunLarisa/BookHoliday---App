package com.unibuc.ro.service;

import com.unibuc.ro.model.Authority;
import com.unibuc.ro.model.Client;
import com.unibuc.ro.model.ClientRequest;
import com.unibuc.ro.model.ClientSignupRequest;
import com.unibuc.ro.repository.security.AuthorityRepository;
import com.unibuc.ro.repository.security.ClientRepository;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @InjectMocks
    private ClientServiceImpl clientService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthorityRepository authorityRepository;
    @Test
    void save() {
        clientService.save(new Client());
        verify(clientRepository,times(1)).save(any());
    }
    @Test
    void update() {
        when(clientRepository.findById(1l)).thenReturn(Optional.of(new Client()));
        clientService.update(1l,new Client());
        verify(clientRepository,times(1)).save(any());
    }
    @Test
    void findAll() {
        clientService.findAll();
        verify(clientRepository,times(1)).findAll();
    }
    @Test
    void deleteById() {
        when(clientRepository.findById(1l)).thenReturn(Optional.of(new Client()));
        clientService.deleteById(1l);
        verify(clientRepository, times(1)).delete(any());
    }
    @Test
    void deleteByInexistentId() {
        when(clientRepository.findById(1l)).thenReturn(Optional.empty());
        try {
            clientService.deleteById(1l);
        }catch(Exception e) {
            assertEquals("Client with id 1 not found!",e.getMessage());
        }
    }

    @Test
    void findByEmailAndPasswordSadFlow() {
        when(clientRepository.findClientByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn(anyString());
        try {
            clientService.findByEmailAndPassword(new ClientRequest("larisa@costrun","pass"));
        } catch (Exception e) {
            assertEquals("The client with the provided username and password does not exist or is not registered.", e.getMessage());
        }
    }

    @Test
    void testSaveClientRequestHappyFlow() {
        ClientSignupRequest clientSignupRequest = new ClientSignupRequest();
        clientSignupRequest.setEmail("larisa");
        clientSignupRequest.setPassword("larisa");
        clientSignupRequest.setBirthDate("2000-06-07");
        when(clientService.findByEmail(any())).thenReturn(Optional.empty());
        when(authorityRepository.findByRole(anyString())).thenReturn(new Authority());
        clientService.save(clientSignupRequest);
        verify(clientRepository,times(1)).save(any());
    }
    @Test
    void testSaveClientRequestSadFlow() {
        ClientSignupRequest clientSignupRequest = new ClientSignupRequest();
        clientSignupRequest.setEmail("larisa");
        when(clientService.findByEmail(any())).thenReturn(Optional.of(new Client()));
        try {
            clientService.save(clientSignupRequest);
        } catch (Exception e) {
            assertEquals("A client with this email is already registered!", e.getMessage());
        }
    }
}