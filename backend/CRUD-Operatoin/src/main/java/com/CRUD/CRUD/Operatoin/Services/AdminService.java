package com.CRUD.CRUD.Operatoin.Services;

import com.CRUD.CRUD.Operatoin.Entities.Admin;
import com.CRUD.CRUD.Operatoin.Repositories.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Validates the admin's credentials.
     * @param email Admin's email.
     * @param rawPassword Admin's raw password (plaintext).
     * @return Optional containing the Admin object if validation is successful, empty otherwise.
     */
    // AdminService.java
    public Optional<Admin> validateAdmin(String email, String rawPassword) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        adminOpt.ifPresent(admin -> {
            System.out.println("Encoded Password from DB: " + admin.getPassword());
            System.out.println("Raw Password to Match: " + rawPassword);
            System.out.println("Password match result: " + passwordEncoder.matches(rawPassword, admin.getPassword()));
        });
        return adminOpt.filter(admin -> passwordEncoder.matches(rawPassword, admin.getPassword()));
    }



    /**
     * Registers a new admin after encrypting their password.
     * @param admin Admin entity containing details for registration.
     * @return The saved Admin entity.
     */
    public Admin registerAdmin(Admin admin) {
        String rawPassword = admin.getPassword();

        System.out.println("Registering admin with email: " + admin.getEmail());
        System.out.println("Raw password: " + rawPassword);
        return adminRepository.save(admin);
    }




  

}
