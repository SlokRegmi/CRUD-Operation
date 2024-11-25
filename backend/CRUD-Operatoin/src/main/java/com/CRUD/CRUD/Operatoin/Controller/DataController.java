package com.CRUD.CRUD.Operatoin.Controller;

import com.CRUD.CRUD.Operatoin.DTO.UserResponseDTO;
import com.CRUD.CRUD.Operatoin.Entities.User;
import com.CRUD.CRUD.Operatoin.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data")
public class DataController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public DataController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/getData")
    public ResponseEntity<?> getData(@RequestBody Map<String, Object> request) {
        // Extract role from the request body
        String role = (String) request.get("role");

        // Initialize response
        Map<String, Object> response = new HashMap<>();
        response.put("originalData", request);

        // Handle roles and fetch user data accordingly
        switch (role.toUpperCase()) {
            case "VIEWONLYOPERATION":
                List<User> allUsers = userService.getAllUsers(); // Fetch all users
                List<UserResponseDTO> userDTOs = allUsers.stream()
                        .map(user -> modelMapper.map(user, UserResponseDTO.class))
                        .collect(Collectors.toList()); // Map entities to DTOs
                response.put("access", "You can view all users.");
                response.put("permissions", "View only");
                response.put("users", userDTOs); // Include user DTOs in the response
                break;
            case "DELETEOPERATION":
                response.put("access", "You have delete permissions but cannot view user data.");
                response.put("permissions", "Delete");
                break;
            case "UPDATEOPERATION":
                response.put("access", "You have update permissions but cannot view user data.");
                response.put("permissions", "Update");
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid role: " + role);
        }

        // Return the full response
        return ResponseEntity.ok(response);
    }
    @GetMapping(path= "/Hello")
    public String Hello(){
        return "Hello";
    }
    @PostMapping(path= "/Update")
    public User Update(@RequestBody User user){
        return userService.updateUser(user);
    }




}
