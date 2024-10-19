package edu.eci.cvds.pattens.controllerTest;

import edu.eci.cvds.pattens.controller.LoginController;
import edu.eci.cvds.pattens.exception.UserExcepion;
import edu.eci.cvds.pattens.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private LoginController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void shouldReturnNotFoundWhenUserDoesNotExist() throws UserExcepion.UserNotFoundException, UserExcepion.UserIncorrectPasswordException {
        // Arrange: Mock the behavior of AuthService to throw UserNotFoundException
        when(authService.loginUser("nonexistent", "password"))
                .thenThrow(new UserExcepion.UserNotFoundException("User not found"));

        // Prepare the request body
        HashMap<String, String> body = new HashMap<>();
        body.put("username", "nonexistent");
        body.put("password", "password");

        // Act: Call the login method
        ResponseEntity<?> response = authController.login(body, mock(HttpServletResponse.class));

        // Assert: Check if the response has the expected status and body
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User not found", response.getBody());
    }
}