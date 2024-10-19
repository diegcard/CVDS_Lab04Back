package edu.eci.cvds.pattens.modelTest;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelUserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void shouldSetAndGetId() {
        String id = "123";
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void shouldSetAndGetUsername() {
        String username = "Test User";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void shouldSetAndGetEmail() {
        String email = "testuser@gmail.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void shouldSetAndGetPassword() {
        String password = "jaja";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    public void shouldSetAndGetFullName() {
        String fullName = "User Test";
        user.setFullName(fullName);
        assertEquals(fullName, user.getFullName());
    }

    @Test
    public void shouldSetAndGetCreationDate() {
        LocalDate creationDate = LocalDate.now();
        user.setCreationDate(creationDate);
        assertEquals(creationDate, user.getCreationDate());
    }

    @Test
    public void shouldSetAndGetLastLogin() {
        LocalDate lastLogin = LocalDate.now();
        user.setLastLogin(lastLogin);
        assertEquals(lastLogin, user.getLastLogin());
    }
    

    @Test
    public void shouldCreateUserWithValidFields() {
        User user = new User("1", "username", "email@example.com", "password", "Full Name", LocalDate.now(), LocalDate.now());
        assertNotNull(user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("email@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("Full Name", user.getFullName());
        assertNotNull(user.getCreationDate());
        assertNotNull(user.getLastLogin());
    }

    @Test
    public void shouldUpdateUserEmailSuccessfully() {
        User user = new User("1", "username", "email@example.com", "password", "Full Name", LocalDate.now(), LocalDate.now());
        user.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmail());
    }


    @Test
    public void shouldReturnCorrectFullName() {
        User user = new User("1", "username", "email@example.com", "password", "Full Name", LocalDate.now(), LocalDate.now());
        assertEquals("Full Name", user.getFullName());
    }

}
