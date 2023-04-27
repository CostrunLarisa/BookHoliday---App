package com.unibuc.ro.repository.security;

import com.unibuc.ro.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findClientByEmailAndPassword(String email, String password);

    Optional<Client> findClientByEmail(String username);
}
