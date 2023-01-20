package com.unibuc.ro.service;

import com.unibuc.ro.model.Client;
import com.unibuc.ro.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}