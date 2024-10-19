package edu.eci.cvds.pattens.controllerTest;
import edu.eci.cvds.pattens.controller.UserController;
import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateUser() {
        User usuario1 = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja", "User Test 1", LocalDate.now(), LocalDate.now(), null);
        when(userService.save(usuario1)).thenReturn(usuario1);
        ResponseEntity<?> response = userController.saveUser(usuario1);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService).save(usuario1);
        assertEquals(usuario1, response.getBody());
    }

    @Test
    public void shouldGetById() {
        User usuario1 = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja", "User Test 1", LocalDate.now(), LocalDate.now(), null);
        when(userService.getUserById("123")).thenReturn(usuario1);
        ResponseEntity<?> response = userController.getUserById("123");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).getUserById("123");
        assertEquals(usuario1, response.getBody());
    }

    @Test
    public void deleteUser() {
        User usuario1 = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja", "User Test 1", LocalDate.now(), LocalDate.now(), null);
        when(userService.getUserById("123")).thenReturn(usuario1);
        ResponseEntity<?> response = userController.deleteUser("123");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userService.getAllUser()).thenReturn(users);
        assertEquals(users, userController.getAllUsers());
    }

    @Test
    public void shouldReturnBadRequestWhenCreateUserWithExistingId() {
        User usuario1 = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja", "User Test 1", LocalDate.now(), LocalDate.now(), null);
        when(userService.save(usuario1)).thenThrow(new RuntimeException());
        ResponseEntity<?> response = userController.saveUser(usuario1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService).save(usuario1);
    }

    @Test
    public void shouldReturnBadRequestWhenGetUserById() {
        when(userService.getUserById("123")).thenThrow(new RuntimeException());
        ResponseEntity<?> response = userController.getUserById("123");
    }

    @Test
    public void shouldReturnBadRequestWhenDeleteUser() {
        when(userService.getUserById("123")).thenThrow(new RuntimeException());
        ResponseEntity<?> response = userController.deleteUser("123");
    }
}
