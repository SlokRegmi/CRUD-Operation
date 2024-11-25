package com.CRUD.CRUD.Operatoin.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Permit all requests
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}