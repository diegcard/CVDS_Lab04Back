package edu.eci.cvds.pattens.repository.user;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * UserMongoRepository interface extends both UserRepository and MongoRepository.
 * It provides methods for common CRUD operations on User documents in a MongoDB collection.
 */
@Repository
public interface UserMongoRepository extends UserRepository, MongoRepository<User, String> {

    /**
     * Checks if a User with the specified ID exists in the repository.
     * @param id the ID of the User to check.
     * @return true if the User exists, false otherwise.
     */
    @Override
    default boolean existsById(String id) {
        User user = findUserById(id);
        return user != null;
    }

    /**
     * Generates a unique 8-character alphanumeric ID.
     * The ID is composed of uppercase letters, lowercase letters, and digits.
     * Ensures that the generated ID is unique by checking against a set of previously generated IDs.
     * @return A unique 8-character alphanumeric ID.
     */
    private String generateId() {
        StringBuilder id = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        do {
            for (int i = 0; i < 8; i++) {
                id.append(characters.charAt((int) Math.floor(Math.random() * characters.length())));
            }
        } while (existsById(id.toString()));
        return id.toString();
    }

    /**
     * Saves and creates a new User.
     * If the User does not have an ID, it generates one.
     * Sets the creation date of the User to the current date.
     * @param user the User to create.
     * @return the created User.
     */
    @Override
    default User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(generateId());
        }
        user.setCreationDate(LocalDate.now());
        user.setLastLogin(null);
        save(user);
        return user;
    }

    /**
     * Finds a User by their ID.
     * @param id the ID of the User to find.
     * @return the User if found, null otherwise.
     */
    @Override
    default User findUserById(String id) {
        return findById(id).orElse(null);
    }

    /**
     * Retrieves all Users.
     * @return a list of all Users.
     */
    @Override
    default List<User> findAllUsers() {
        return findAll();
    }

    /**
     * Deletes a User by their ID.
     * Throws an exception if the User does not exist.
     * @param id the ID of the User to delete.
     */
    @Override
    default void deleteUserById(String id) {
        if (!existsById(id)) {
            throw new RuntimeException("User not found");
        }
        deleteById(id);
    }

    /**
     * Updates an existing User.
     * if the User is not found, throw an exception
     * @param user the User to update
     */
    @Override
    default User updateUser(User user) {
        if (!existsById(user.getId())) {
            throw new RuntimeException("User not found");
        }
        save(user);
        return user;
    }
}
