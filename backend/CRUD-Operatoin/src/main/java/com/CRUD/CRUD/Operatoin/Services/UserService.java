package com.CRUD.CRUD.Operatoin.Services;

import com.CRUD.CRUD.Operatoin.Entities.Admin;
import com.CRUD.CRUD.Operatoin.Entities.User;
import com.CRUD.CRUD.Operatoin.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> validateUser(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        userOpt.ifPresent(user -> {
            System.out.println("Encoded Password from DB: " + user.getPassword());
            System.out.println("Raw Password to Match: " + rawPassword);
            System.out.println("Password match result: " + passwordEncoder.matches(rawPassword, user.getPassword()));
        });
        return userOpt.filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()));
    }


    public User registerUser(User user) {
        String rawPassword = user.getPassword();

        System.out.println("Registering admin with email: " + user.getEmail());
        System.out.println("Raw password: " + rawPassword);

        return userRepository.save(user);
    }

public User updateUser(User user) {

        return userRepository.save(user);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

