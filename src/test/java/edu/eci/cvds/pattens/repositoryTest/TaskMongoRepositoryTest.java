package edu.eci.cvds.pattens.repositoryTest;


import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.repository.task.TaskMongoRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskMongoRepositoryTest {

    @Mock
    private MongoRepository<Task, String> mongoRepository;

    private TaskMongoRepository taskMongoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        taskMongoRepository = new TaskMongoRepository() {
            @Override
            public List<Task> findAll(Sort sort) {
                return List.of();
            }

            @Override
            public Page<Task> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Task> S insert(S entity) {
                return null;
            }

            @Override
            public <S extends Task> List<S> insert(Iterable<S> entities) {
                return List.of();
            }

            @Override
            public <S extends Task> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Task> List<S> findAll(Example<S> example) {
                return List.of();
            }

            @Override
            public <S extends Task> List<S> findAll(Example<S> example, Sort sort) {
                return List.of();
            }

            @Override
            public <S extends Task> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Task> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Task> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Task, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }

            @Override
            public <S extends Task> S save(S entity) {
                return mongoRepository.save(entity);
            }

            @Override
            public Optional<Task> findById(String id) {
                return mongoRepository.findById(id);
            }

            @Override
            public boolean existsById(String id) {
                return mongoRepository.existsById(id);
            }

            @Override
            public <S extends Task> List<S> saveAll(Iterable<S> entities) {
                return List.of();
            }

            @Override
            public List<Task> findAll() {
                return mongoRepository.findAll();
            }

            @Override
            public List<Task> findAllById(Iterable<String> strings) {
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
            public void delete(Task entity) {
                mongoRepository.delete(entity);
            }

            @Override
            public void deleteAllById(Iterable<? extends String> strings) {

            }

            @Override
            public void deleteAll(Iterable<? extends Task> entities) {

            }

            @Override
            public void deleteAll() {

            }

        };
    }

    @Test
    public void shouldSaveAndCreateNewTask() {
        Task task = new Task();
        task.setNameTask("Test Task");

        when(mongoRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId("generatedId");
            return savedTask;
        });

        Task savedTask = taskMongoRepository.saveTask(task);

        assertNotNull(savedTask.getId());
        assertFalse(savedTask.getIsCompleted());
        assertEquals("Test Task", savedTask.getNameTask());
        verify(mongoRepository).save(task);
    }

    @Test
    public void shouldFindAllTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(mongoRepository.findAll()).thenReturn(tasks);

        List<Task> foundTasks = taskMongoRepository.findAllTasks();

        assertEquals(2, foundTasks.size());
        verify(mongoRepository).findAll();
    }

    @Test
    public void shouldDeleteTask() {
        Task task = new Task();
        task.setId("123");
        when(mongoRepository.existsById("123")).thenReturn(true);

        taskMongoRepository.deleteTask(task);

        verify(mongoRepository).delete(task);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingNonExistentTask() {
        Task task = new Task();
        task.setId("123");
        when(mongoRepository.existsById("123")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> taskMongoRepository.deleteTask(task));
        verify(mongoRepository, never()).delete(task);
    }

    @Test
    public void shouldUpdateTask() {
        Task task = new Task();
        task.setId("123");
        task.setNameTask("Test Task");

        when(mongoRepository.existsById("123")).thenReturn(true);
        when(mongoRepository.save(task)).thenReturn(task);

        Task updatedTask = taskMongoRepository.updateTask(task);

        assertEquals("123", updatedTask.getId());
        assertEquals("Test Task", updatedTask.getNameTask());
        verify(mongoRepository).save(task);
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingNonExistentTask() {
        Task task = new Task();
        task.setId("123");
        when(mongoRepository.existsById("123")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> taskMongoRepository.updateTask(task));
        verify(mongoRepository, never()).save(task);
    }

    @Test
    public void shouldCheckIfTaskExists() {
        when(mongoRepository.existsById("123")).thenReturn(true);

        assertTrue(taskMongoRepository.existsById("123"));
        verify(mongoRepository).existsById("123");
    }

    @Test
    public void shouldCheckIfTaskDoesNotExist() {
        when(mongoRepository.existsById("123")).thenReturn(false);

        assertFalse(taskMongoRepository.existsById("123"));
        verify(mongoRepository).existsById("123");
    }

    @Test
    public void shouldFindTaskById() {
        Task task = new Task();
        task.setId("123");
        when(mongoRepository.findById("123")).thenReturn(Optional.of(task));

        Task foundTask = taskMongoRepository.findTaskById("123");

        assertEquals("123", foundTask.getId());
        verify(mongoRepository).findById("123");
    }

    @Test
    public void shouldReturnNullWhenTaskDoesNotExist() {
        when(mongoRepository.findById("123")).thenReturn(Optional.empty());

        Task foundTask = taskMongoRepository.findTaskById("123");

        assertNull(foundTask);
        verify(mongoRepository).findById("123");
    }



}