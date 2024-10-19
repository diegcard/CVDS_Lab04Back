package edu.eci.cvds.pattens.serviceTest;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.repository.user.UserRepository;
import edu.eci.cvds.pattens.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateUser() {
        User user = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja","User Test 1", LocalDate.now(), LocalDate.now());
        when(userRepository.saveUser(user)).thenReturn(user);
        User saveUser = userService.save(user);
        assertEquals(user, saveUser);
        verify(userRepository).saveUser(user);
    }

    @Test
    public void shouldThrowExceptionWhenCreateUserWithExistingId() {
        User user = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja","User Test 1", LocalDate.now(), LocalDate.now());
        when(userRepository.saveUser(user)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> userService.save(user));
    }

    @Test
    public void shouldThrowExceptionWhenThereAreDataIntegrityViolationExceptions(){
        User user = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja","User Test 1", LocalDate.now(), LocalDate.now());
        when(userRepository.saveUser(user)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> userService.save(user));
    }

    @Test
    public void shouldGetAllTasks() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAllUsers()).thenReturn(users);
        assertEquals(users, userService.getAllUser());
    }

    @Test
    public void shouldGetUserById() {
        User user = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja", "User Test 1", LocalDate.now(), LocalDate.now());
        when(userRepository.findUserById("123")).thenReturn(user);
        assertEquals(user, userService.getUserById("123"));
    }

    @Test
    public void shouldGetUserByUsername() {
        User user = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja", "User Test 1", LocalDate.now(), LocalDate.now());
        when(userRepository.findUserByUsername("Test User 1")).thenReturn(user);
        assertEquals(user, userService.getUserByUsername("Test User 1"));
    }

    @Test
    public void shouldDeleteUser() {
        userService.deleteUser("123");
        verify(userRepository).deleteUserById("123");
    }

    @Test
    public void shouldUdateUser() {
        User user = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja","User Test 1", LocalDate.now(), LocalDate.now());
        when(userRepository.existsById("123")).thenReturn(true);
        when(userRepository.saveUser(user)).thenReturn(user);
        assertEquals(user, userService.save(user));
        verify(userRepository).saveUser(user);
    }

    @Test
    public void shouldThrowExceptionWhenThereAreTransactionSystemExceptionsOnUpdateTask() throws Exception {
        User user = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja","User Test 1", LocalDate.now(), LocalDate.now());
        when(userRepository.existsById("123")).thenReturn(true);
        when(userRepository.saveUser(user)).thenThrow(new RuntimeException());
        assertThrows(Exception.class, () -> userService.save(user));
    }

    @Test
    public void shouldThrowExceptionwhenSaveAnUserWithAnExistingUsername() {
        User user = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja", "User Test 1", LocalDate.now(), LocalDate.now());
        when(userRepository.existsByUsername("Test User 1")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> userService.save(user));
    }

    @Test
    public void shouldThrowExceptionwhenSaveAnUserWithAnExistingEmail() {
        User user = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja", "User Test 1", LocalDate.now(), LocalDate.now());
        when(userRepository.existsByEmail("testuser1@mail.escuelaing.com")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> userService.save(user));
    }
}
