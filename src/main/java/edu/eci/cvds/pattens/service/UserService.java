package edu.eci.cvds.pattens.service;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import edu.eci.cvds.pattens.repository.task.TaskRepository;

/**
 * UserService class provides service methods for User-related operations.
 * It uses UserRepository to interact with the database.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

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

    /**
     * get user by his username
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    /**
     * get all user tasks by user id
     * @param id
     * @return a list of all tasks of the user
     */
    public List<Task> getAllTaskByUserId(String id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        List<Task> tasks = taskRepository.findAllTasks();
        List<Task> userTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getUser().equals(id)) {
                userTasks.add(task);
            }
        }
        return userTasks;
    }

    /**
     * Update the lastLogin of the userto the current date
     * @param id
     */
    public void updateLastLogin(String id) {
        User user = userRepository.findUserById(id);
        user.setLastLogin(LocalDate.now());
        userRepository.updateUser(user);
    }
}
