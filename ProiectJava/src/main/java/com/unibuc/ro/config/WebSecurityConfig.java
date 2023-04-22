package com.unibuc.ro.config;

import com.unibuc.ro.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile("postgres")
public class WebSecurityConfig {
    private final JpaUserDetailsService userDetailsService;
    public WebSecurityConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .antMatchers("/signup","/").permitAll()
                        .anyRequest().authenticated()
                ).userDetailsService(userDetailsService)
                .formLogin()
                .loginPage("/")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/signup?error=true")
                .and()
                .logout((logout) -> logout.logoutUrl("/logout").permitAll())
                .exceptionHandling()
                .accessDeniedPage("/access_denied")
                .and()
                .httpBasic(withDefaults());

        return http.build();
    }
}
