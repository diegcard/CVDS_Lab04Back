package edu.eci.cvds.pattens.service;

import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("User already exists");
        }
        return userRepository.saveUser(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAllUsers();
    }
}
