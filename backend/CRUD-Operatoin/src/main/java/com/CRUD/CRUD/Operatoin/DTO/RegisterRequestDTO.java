package com.CRUD.CRUD.Operatoin.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // Getters and setters
}

