package edu.eci.cvds.pattens.repository.user;

import edu.eci.cvds.pattens.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserMongoRepository extends UserRepository, MongoRepository<User, String> {

    default boolean existsById(String id){
        User user = findUserById(id);
        return user != null;
    }

    /**
     * Generates a unique 8-character alphanumeric ID.
     * The ID is composed of uppercase letters, lowercase letters, and digits.
     * Ensures that the generated ID is unique by checking against a set of previously generated IDs.
     *
     * @return string A unique 5-character alphanumeric ID.
     */
    private String generateId() {
        String id = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        do {
            for (int i = 0; i < 8; i++) {
                id += characters.charAt((int) Math.floor(Math.random() * characters.length()));
            }
        } while (existsById(id));
        return id;
    }

    /**
     * Save and create a new user
     * @param user the user to create
     * @return the created user
     */
    @Override
    default User saveUser(User user){
        if(user.getId() == null){
            user.setId(generateId());
        }
        user.setCreationDate(LocalDate.now());
        save(user);
        return user;
    }

    @Override
    default User findUserById(String id){
        return findById(id).orElse(null);
    }

    @Override
    default List<User> findAllUsers(){
        return findAll();
    }

    User findByUsername(String username);

}
