package com.unibuc.ro;

import com.unibuc.ro.model.Authority;
import com.unibuc.ro.model.Client;
import com.unibuc.ro.repository.security.AuthorityRepository;
import com.unibuc.ro.repository.security.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@AllArgsConstructor
@Component
@Profile("postgres")
public class DataLoader implements CommandLineRunner {

    private AuthorityRepository authorityRepository;
    private ClientRepository userRepository;
    private PasswordEncoder passwordEncoder;


    private void loadUserData() throws ParseException {
        if (userRepository.count() == 0){
            SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
            Date date = DateFor.parse("08/07/2019");
            Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
            Authority guestRole = authorityRepository.save(Authority.builder().role("ROLE_GUEST").build());

            Client admin = Client.builder()
                    .email("admin")
                    .password(passwordEncoder.encode("12345"))
                    .authority(adminRole)
                    .birthDate(date)
                    .build();

            Client guest = Client.builder()
                    .email("guest")
                    .password(passwordEncoder.encode("12345"))
                    .authority(guestRole)
                    .birthDate(date)
                    .build();

            userRepository.save(admin);
            userRepository.save(guest);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}
