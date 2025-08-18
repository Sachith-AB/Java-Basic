package controller;

import annotation.*;
import application.user.UserService;
import domain.user.User;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        // Validation
        if (user.name() == null || user.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (user.email() == null || !user.email().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        
        return userService.createUser(user);
    }

    @PostMapping("/create")
    public User createUserWithValidation(@RequestBody CreateUserRequest request) {
        // Validation
        if (request.name() == null || request.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (request.email() == null || !request.email().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        
        User user = new User(0, request.name(), request.email());
        return userService.createUser(user);
    }

    // DTO for create user request
    public record CreateUserRequest(String name, String email) {}
}
