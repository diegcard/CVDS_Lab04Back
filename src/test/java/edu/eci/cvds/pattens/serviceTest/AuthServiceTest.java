package edu.eci.cvds.pattens.serviceTest;

import edu.eci.cvds.pattens.exception.UserExcepion;
import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.service.AuthService;
import edu.eci.cvds.pattens.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void shouldLoginUserSuccessfully() throws UserExcepion.UserNotFoundException, UserExcepion.UserIncorrectPasswordException {
        User user = new User("1", "username", "email@example.com", "password", "Full Name", LocalDate.now(), LocalDate.now());
        when(userService.getUserByUsername("username")).thenReturn(user);
        String token = authService.loginUser("username", "password");
        assertNotNull(token);
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        when(userService.getUserByUsername("nonexistent")).thenReturn(null);
        assertThrows(UserExcepion.UserNotFoundException.class, () -> authService.loginUser("nonexistent", "password"));
    }

    @Test
    public void shouldThrowUserIncorrectPasswordExceptionWhenPasswordIsIncorrect() {
        User user = new User("1", "username", "email@example.com", "password", "Full Name", LocalDate.now(), LocalDate.now());
        when(userService.getUserByUsername("username")).thenReturn(user);
        assertThrows(UserExcepion.UserIncorrectPasswordException.class, () -> authService.loginUser("username", "wrongpassword"));
    }
}
