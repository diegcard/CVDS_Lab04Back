package edu.eci.cvds.pattens.service;

import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * UserService class provides service methods for User-related operations.
 * It uses UserRepository to interact with the database.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a User to the database.
     * Checks if the User's username or email already exists before saving.
     * Throws a RuntimeException if the username or email already exists.
     * @param user the User to save.
     * @return the saved User.
     */
    public User save(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("User already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.saveUser(user);
    }

    /**
     * Retrieves all Users from the database.
     * @return a list of all Users.
     */
    public List<User> getAllUser() {
        return userRepository.findAllUsers();
    }

    /**
     * Finds a User by their ID.
     * @param id the ID of the User to find.
     * @return the User if found, null otherwise.
     */
    public User getUserById(String id) {
        return userRepository.findUserById(id);
    }

    /**
     * Deletes a User by their ID.
     * @param id the ID of the User to delete.
     */
    public void deleteUser(String id) {
        userRepository.deleteUserById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
