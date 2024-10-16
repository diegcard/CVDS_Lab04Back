package edu.eci.cvds.pattens.repository.user;

import edu.eci.cvds.pattens.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository {
    User saveUser(User user);
    User findUserById(String id);
    User updateUser(User user);
    List<User> findAllUsers();
    boolean existsById(String id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void deleteUserById(String id);

}
