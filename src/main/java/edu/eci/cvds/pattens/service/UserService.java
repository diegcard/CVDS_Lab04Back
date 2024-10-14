package edu.eci.cvds.pattens.service;

import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.saveUser(user);
    }
}
