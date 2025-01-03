package edu.eci.cvds.pattens.controller;

import edu.eci.cvds.pattens.exception.UserExcepion;
import edu.eci.cvds.pattens.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody HashMap<String, String> body, HttpServletResponse response){
        String username = body.get("username");
        String password = body.get("password");
        try {
            String token = authService.loginUser(username, password);
            Cookie cookie = new Cookie("session-token", token);
            cookie.setMaxAge(60* 60 * 12);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.status(200).body(token);
        } catch (Exception e) {
            if (e instanceof UserExcepion.UserNotFoundException) {
                return ResponseEntity.status(404).body("User not found");
            }
            if (e instanceof UserExcepion.UserIncorrectPasswordException) {
                return ResponseEntity.status(409).body("Incorrect password");
            }
            return ResponseEntity.status(500).body("Unknown server error");
        }
    }
}
