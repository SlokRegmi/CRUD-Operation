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
@Data // Lombok to generate getters, setters, equals, hashCode, and toString.
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "unique_email", columnNames = "email"),
        @UniqueConstraint(name = "unique_phone", columnNames = "phone")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50) // VARCHAR(50)
    private String firstName;

    @Column(nullable = false, length = 50) // VARCHAR(50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100) // Ensures unique emails
    private String email;

    @Column(nullable = false)
    private String password; // Store hashed passwords only!


    @CreationTimestamp // Automatically sets timestamp on creation
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Automatically updates timestamp on modification
    private LocalDateTime updatedAt;

    // Utility method for password hashing
    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
