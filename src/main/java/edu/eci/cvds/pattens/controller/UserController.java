package edu.eci.cvds.pattens.controller;

import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        HashMap<String, String> response;
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        }catch (Exception e){
            response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
