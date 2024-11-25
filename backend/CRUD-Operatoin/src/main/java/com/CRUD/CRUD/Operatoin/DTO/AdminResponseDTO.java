package com.CRUD.CRUD.Operatoin.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AdminResponseDTO {
    private String name;
    private String email;
    private Long adminId;
    private String role;
    private Instant timestamp;

    // Getters and setters
}
