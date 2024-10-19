package edu.eci.cvds.pattens.repositoryTest;

import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.repository.user.UserMongoRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;


import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserMongoRepositoryTest {

    @Mock
    private MongoRepository<User, String> mongoRepository;

    private UserMongoRepository userMongoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userMongoRepository = new UserMongoRepository() {
            @Override
            public List<User> findAll(Sort sort) {
                return List.of();
            }

            @Override
            public Page<User> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends User> S insert(S entity) {
                return null;
            }

            @Override
            public <S extends User> List<S> insert(Iterable<S> entities) {
                return List.of();
            }

            @Override
            public <S extends User> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends User> List<S> findAll(Example<S> example) {
                return List.of();
            }

            @Override
            public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends User> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends User> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }

            @Override
            public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
                return List.of();
            }

            @Override
            public Optional<User> findById(String id) {
                return mongoRepository.findById(id);
            }

            @Override
            public boolean existsById(String s) {
                return mongoRepository.existsById(s);
            }

            @Override
            public boolean existsByUsername(String username) {
                return false;
            }

            @Override
            public boolean existsByEmail(String email) {
                return false;
            }

            @Override
            public User findUserByUsername(String username) {
                return null;
            }

            @Override
            public List<User> findAll() {
                return mongoRepository.findAll();
            }

            @Override
            public List<User> findAllById(Iterable<String> strings) {
                return List.of();
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(String s) {

            }

            @Override
            public void delete(User user) {
                mongoRepository.delete(user);
            }

            @Override
            public void deleteAllById(Iterable<? extends String> strings) {

            }

            @Override
            public void deleteAll(Iterable<? extends User> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends User> S save(S entity) {
                return null;
            }

            @Override
            public <S extends User> List<S> saveAll(Iterable<S> entities) {
                return List.of();
            }

        };
    }

    @Test
    public void shouldSaveUser() {
        User user = new User();
        when(mongoRepository.save(user)).thenReturn(user);
        User savedUser = userMongoRepository.saveUser(user);
        assertEquals(user, savedUser);
    }

    @Test
    public void shouldFindUserById() {
        User user = new User();
        user.setId("generatedId");
        when(mongoRepository.findById("generatedId")).thenReturn(Optional.of(user));
        User foundUser = userMongoRepository.findUserById("generatedId");
        assertEquals(user, foundUser);
    }

    @Test
    public void shouldFindAllUsers() {
        User user = new User();
        when(mongoRepository.findAll()).thenReturn(List.of(user));
        List<User> users = userMongoRepository.findAllUsers();
        assertEquals(List.of(user), users);
    }

    @Test
    public void shouldUpdateUser() {
        User user = new User();
        user.setId("generatedId");
        when(mongoRepository.existsById("generatedId")).thenReturn(true);
        when(mongoRepository.save(user)).thenReturn(user);
        User updatedUser = userMongoRepository.updateUser(user);
        assertEquals(user, updatedUser);
    }

    @Test
    public void shouldNotUpdateUser() {
        User user = new User();
        user.setId("generatedId");
        when(mongoRepository.existsById("generatedId")).thenReturn(false);
        assertThrows(RuntimeException.class, () -> userMongoRepository.updateUser(user));
    }

    @Test
    public void shouldCheckIfUserExists() {
        when(mongoRepository.existsById("generatedId")).thenReturn(true);
        assertTrue(userMongoRepository.existsById("generatedId"));
    }

    @Test
    public void shouldCheckIfUserDoesNotExist() {
        when(mongoRepository.existsById("generatedId")).thenReturn(false);
        assertFalse(userMongoRepository.existsById("generatedId"));
    }


    @Test
    public void shouldThrowExceptionWhenDeletingNonExistentUser(){
    User user = new User();
        user.setId("123");
        when(mongoRepository.existsById("123")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userMongoRepository.deleteUserById("123"));
        verify(mongoRepository, never()).deleteById("123");
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingNonExistentUser(){
        User user = new User();
        user.setId("123");
        when(mongoRepository.existsById("123")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userMongoRepository.updateUser(user));
        verify(mongoRepository, never()).save(user);
    }


}
