package edu.eci.cvds.pattens.service;

import com.auth0.jwt.algorithms.Algorithm;
import edu.eci.cvds.pattens.exception.UserExcepion;
import edu.eci.cvds.pattens.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;

import java.time.LocalDate;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String loginUser(String username, String password) throws UserExcepion.UserNotFoundException, UserExcepion.UserIncorrectPasswordException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UserExcepion.UserNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserExcepion.UserIncorrectPasswordException("Incorrect password");
        }
        userService.updateLastLogin(user.getId());
        String token = JWT.create()
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("email", user.getEmail())
                .withClaim("fullName", user.getFullName())
                .withClaim("creationDate", user.getCreationDate().toString())
                .withClaim("role", user.getRole().toString())
                .sign(Algorithm.HMAC256("secret"));
        return token;
    }


}
