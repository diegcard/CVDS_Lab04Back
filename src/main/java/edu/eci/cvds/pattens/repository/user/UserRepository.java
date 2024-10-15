package edu.eci.cvds.pattens.repository.user;

import edu.eci.cvds.pattens.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository {
    User saveUser(User user);
    User findUserById(String id);
    List<User> findAllUsers();
    boolean existsById(String id);
    boolean existsByUsername(String username);

}
