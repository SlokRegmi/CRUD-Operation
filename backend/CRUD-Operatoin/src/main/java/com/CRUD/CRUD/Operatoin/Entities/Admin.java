package com.CRUD.CRUD.Operatoin.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Data // Lombok for getters, setters, equals, hashCode, and toString.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin", uniqueConstraints = {
        @UniqueConstraint(name = "unique_email", columnNames = "email"),
        @UniqueConstraint(name = "unique_phone", columnNames = "phone")
})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50) // VARCHAR(50)
    private String firstName;

    @Column(nullable = false, length = 50) // VARCHAR(50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100) // Ensures unique email
    private String email;

    @Column(nullable = false)
    private String password; // Store hashed passwords only!


    @Enumerated(EnumType.STRING) // Strongly-typed role field
    @Column(nullable = false, length = 20)
    private Role role;

    @CreationTimestamp // Automatically sets the creation timestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Automatically sets the update timestamp
    private LocalDateTime updatedAt;

    // Utility method for password hashing
    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    // Enum for admin roles
    public enum Role {
        ViewOnlyOperation,
        DeleteOperation,
        UpdateOperation
    }
}
