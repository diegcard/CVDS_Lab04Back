package edu.eci.cvds.pattens.controller;

import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

/**
 * UserController class provides RESTful endpoints for User-related operations.
 * It uses UserService to handle business logic.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves all Users.
     * @return a list of all Users.
     */
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    /**
     * Creates a new User.
     * @param user the User to create.
     * @return a ResponseEntity with the created User or an error message.
     */
    @PostMapping("/create")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        HashMap<String, String> response;
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        } catch (Exception e) {
            response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Retrieves a User by their ID.
     * @param id the ID of the User to retrieve.
     * @return a ResponseEntity with the User or an error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        HashMap<String, String> response;
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
        } catch (Exception e) {
            response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Deletes a User by their ID.
     * @param id the ID of the User to delete.
     * @return a ResponseEntity with a success message or an error message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        HashMap<String, String> response;
        try {
            userService.deleteUser(id);
            response = new HashMap<>();
            response.put("user-delete", id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
