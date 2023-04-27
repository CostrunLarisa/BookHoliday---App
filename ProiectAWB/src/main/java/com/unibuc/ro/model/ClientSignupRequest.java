package com.unibuc.ro.model;

import lombok.*;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClientSignupRequest {
    private String firstName;

    private String lastName;
    private String birthDate;
    private String password;
    private String email;

    private Set<Authority> authorities;

}
