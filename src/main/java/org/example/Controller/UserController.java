package org.example.Controller;

import org.example.Domain.User;
import org.example.DTO.UserRegisterDTO;
import org.example.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO dto) {
        try {
            User savedUser = userService.registerUser(dto);

            Map<String, Long> response = new HashMap<>();
            response.put("id", savedUser.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("Error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
