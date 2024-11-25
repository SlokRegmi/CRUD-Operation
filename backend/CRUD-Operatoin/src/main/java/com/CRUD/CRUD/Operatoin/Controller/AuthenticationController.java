package com.CRUD.CRUD.Operatoin.Controller;

import com.CRUD.CRUD.Operatoin.DTO.LoginRequestDTO;
import com.CRUD.CRUD.Operatoin.DTO.RegisterRequestDTO;
import com.CRUD.CRUD.Operatoin.Entities.User;
import com.CRUD.CRUD.Operatoin.Entities.Admin;
import com.CRUD.CRUD.Operatoin.Services.UserService;
import com.CRUD.CRUD.Operatoin.Services.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    private final UserService userService;
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(UserService userService, AdminService adminService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest, @RequestHeader("X-Referer-Type") String refererType) {
        System.out.println("X-Referer-Type: " + refererType); // Debugging log

        if ("admin".equalsIgnoreCase(refererType)) {
            return handleAdminLogin(loginRequest);
        } else if ("user".equalsIgnoreCase(refererType)) {
            return handleUserLogin(loginRequest);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Unauthorized origin or incorrect path in the request.");
    }

    private ResponseEntity<?> handleAdminLogin(LoginRequestDTO loginRequest) {
        System.out.println("Admin login attempt with email: " + loginRequest.getEmail()); // Debugging log
        System.out.println("Password: " + loginRequest.getPassword()); // Debugging log
        return adminService.validateAdmin(loginRequest.getEmail(), loginRequest.getPassword())
                .map(admin -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Admin login successful");
                    response.put("name", admin.getFirstName() + " " + admin.getLastName());
                    response.put("email", admin.getEmail());
                    response.put("adminId", admin.getId());
                    response.put("role", admin.getRole());
                    response.put("timestamp", Instant.now());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    System.out.println("Invalid admin email or password"); // Debugging log
                    Map<String, Object> response = new HashMap<>();
                    response.put("error", "Invalid user email or password.");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                });
    }

    private ResponseEntity<?> handleUserLogin(LoginRequestDTO loginRequest) {
        System.out.println("User login attempt with email: " + loginRequest.getEmail()); // Debugging log
        return userService.validateUser(loginRequest.getEmail(), loginRequest.getPassword())
                .map(user -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "User login successful");
                    response.put("username", user.getFirstName() + " " + user.getLastName());
                    response.put("email", user.getEmail());
                    response.put("userId", user.getId());
                    response.put("timestamp", Instant.now());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    System.out.println("Invalid user email or password"); // Debugging log
                    Map<String, Object> response = new HashMap<>();
                    response.put("error", "Invalid user email or password.");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                });
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequest, @RequestHeader("X-Referer-Type") String refererType) {
        System.out.println("X-Referer-Type: " + refererType); // Debugging log

        if ("admin".equalsIgnoreCase(refererType)) {
            return handleAdminRegister(registerRequest);
        } else if ("user".equalsIgnoreCase(refererType)) {
            return handleUserRegister(registerRequest);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Unauthorized origin or incorrect path in the request.");
    }

    private ResponseEntity<?> handleAdminRegister(RegisterRequestDTO registerRequest) {
        System.out.println("Admin registration attempt with email: " + registerRequest.getEmail()); // Debugging log

        Admin admin = new Admin();
        admin.setFirstName(registerRequest.getFirstName());
        admin.setLastName(registerRequest.getLastName());
        admin.setEmail(registerRequest.getEmail());
        admin.setPassword(registerRequest.getPassword()); // Set plain password

        // Set default role or handle dynamically
        admin.setRole(Admin.Role.ViewOnlyOperation); // Adjust as needed

        Admin savedAdmin = adminService.registerAdmin(admin); // Password will be encrypted in the service

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Admin registered successfully");
        response.put("adminId", savedAdmin.getId());
        response.put("role", savedAdmin.getRole());
        response.put("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private ResponseEntity<?> handleUserRegister(RegisterRequestDTO registerRequest) {
        System.out.println("User registration attempt with email: " + registerRequest.getEmail()); // Debugging log

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword()); // Set plain password

        User savedUser = userService.registerUser(user); // Password will be encrypted in the service

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("userId", savedUser.getId());
        response.put("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/passwordChange")
    public ResponseEntity<?> passwordChange(@RequestBody RegisterRequestDTO registerRequest, @RequestHeader("X-Referer-Type") String refererType) {
        System.out.println("X-Referer-Type: " + refererType); // Debugging log

        if ("admin".equalsIgnoreCase(refererType)) {
            return adminService.handleAdminPasswordChange(registerRequest.getEmail(), registerRequest.getPassword());
        } else if ("user".equalsIgnoreCase(refererType)) {
            return userService.userPasswordChange(registerRequest.getEmail(), registerRequest.getPassword());
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Unauthorized origin or incorrect path in the request.");
    }


}
