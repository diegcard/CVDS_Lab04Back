package edu.eci.cvds.pattens.controllerTest;

import edu.eci.cvds.pattens.controller.TaskController;
import edu.eci.cvds.pattens.model.Role;
import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.service.TaskService;
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

public class TaskControllerTest {

    User usuario1 = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja","User Test 1", LocalDate.now(), LocalDate.now(), Role.ADMIN);
    User usuario2 = new User("124", "Test User 2", "testuser2@mail.com", "jaja","User Test 2", LocalDate.now(), LocalDate.now(),Role.USER);

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnAllTasks() throws Exception {
        List<Task> tasks = Arrays.asList(
                new Task("123", "Test Task 1", "This is a test task 1", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "123"),
                new Task("124", "Test Task 2", "This is a test task 2", true,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "124")
        );
        when(taskService.getAllTasks()).thenReturn(tasks);
        ResponseEntity<?> response = taskController.getAllTasks();
        assertEquals(tasks, response.getBody());
    }

    @Test
    public void shouldReturnAllTasksWhenServiceReturnsTasks() throws Exception {
        List<Task> tasks = Arrays.asList(
                new Task("123", "Test Task 1", "This is a test task 1", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "123"),
                new Task("124", "Test Task 2", "This is a test task 2", true,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "124")
        );
        when(taskService.getAllTasks()).thenReturn(tasks);
        ResponseEntity<?> response = taskController.getAllTasks();
        assertEquals(tasks, response.getBody());
    }

    @Test
    public void shouldReturnBadRequestWhenServiceThrowsException() {
        when(taskService.getAllTasks()).thenThrow(new RuntimeException());
        ResponseEntity<?> response = taskController.getAllTasks();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void shouldReturnTaskById() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "123");
        when(taskService.getTaskById("123")).thenReturn(task);
        ResponseEntity<?> response = taskController.getTaskById("123");
        assertEquals(task, response.getBody());
    }


    @Test
    public void shouldReturnTaskWhenServiceReturnsTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "123");
        when(taskService.getTaskById("123")).thenReturn(task);
        ResponseEntity<?> response = taskController.getTaskById("123");
        assertEquals(task, response.getBody());
    }

    @Test
    public void shouldReturnBadRequestWhenServiceThrowsExceptionOnGetTaskById() {
        when(taskService.getTaskById("123")).thenThrow(new RuntimeException());
        ResponseEntity<?> response = taskController.getTaskById("123");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void shouldCreateTask() throws Exception {
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "124");
        when(taskService.createTask(task)).thenReturn(task);
        ResponseEntity<?> response = taskController.createTask(task);
        assertEquals(task, response.getBody());
    }

    @Test
    public void shouldCreateTaskWhenServiceReturnsTask() throws Exception {
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "124");
        when(taskService.createTask(task)).thenReturn(task);
        ResponseEntity<?> response = taskController.createTask(task);
        assertEquals(task, response.getBody());
    }

    @Test
    public void shouldReturnBadRequestWhenServiceThrowsExceptionOnCreateTask() throws Exception {
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "124");
        when(taskService.createTask(task)).thenThrow(new RuntimeException());
        ResponseEntity<?> response = taskController.createTask(task);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "123");
        when(taskService.updateTask(task)).thenReturn(task);
        ResponseEntity<?> response = taskController.updateTask(task);
        assertEquals(task, response.getBody());
    }

    @Test
    public void shouldUpdateTaskWhenServiceReturnsTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "123");
        when(taskService.updateTask(task)).thenReturn(task);
        ResponseEntity<?> response = taskController.updateTask(task);
        assertEquals(task, response.getBody());
    }

    @Test
    public void shouldReturnBadRequestWhenServiceThrowsExceptionOnUpdateTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "123");
        when(taskService.updateTask(task)).thenThrow(new RuntimeException());
        ResponseEntity<?> response = taskController.updateTask(task);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldDeleteTaskSuccessfully() throws Exception {
        doNothing().when(taskService).deleteTask("123");
        ResponseEntity<?> response = taskController.deleteTask("123");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Task deleted", ((HashMap) response.getBody()).get("message"));
    }

    @Test
    public void shouldReturnBadRequestWhenServiceThrowsExceptionOnDeleteTask() {
        doThrow(new RuntimeException()).when(taskService).deleteTask("123");
        ResponseEntity<?> response = taskController.deleteTask("123");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldMarkTaskAsDone() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", true,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "124");
        when(taskService.doneTask("123")).thenReturn(task);
        ResponseEntity<?> response = taskController.doneTask("123");
        assertEquals(task, response.getBody());
    }

    @Test
    public void shouldMarkTaskAsDoneWhenServiceReturnsTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", true,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "124");
        when(taskService.doneTask("123")).thenReturn(task);
        ResponseEntity<?> response = taskController.doneTask("123");
        assertEquals(task, response.getBody());
    }

    @Test
    public void shouldReturnBadRequestWhenServiceThrowsExceptionOnDoneTask() {
        when(taskService.doneTask("123")).thenThrow(new RuntimeException());
        ResponseEntity<?> response = taskController.doneTask("123");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldMarkTaskAsUndoneWhenServiceReturnsTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "123");
        when(taskService.undoneTask("123")).thenReturn(task);
        ResponseEntity<?> response = taskController.undoneTask("123");
        assertEquals(task, response.getBody());
    }

    @Test
    public void shouldReturnBadRequestWhenServiceThrowsExceptionOnUndoneTask() {
        when(taskService.undoneTask("123")).thenThrow(new RuntimeException());
        ResponseEntity<?> response = taskController.undoneTask("123");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldMarkTaskAsUndone() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "123");
        when(taskService.undoneTask("123")).thenReturn(task);
        ResponseEntity<?> response = taskController.undoneTask("123");
        assertEquals(task, response.getBody());
    }

    @Test
    public void shouldChangeIsCompletedSuccessfully() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), "123");
        when(taskService.changeIsCompleted("123")).thenReturn(task);
        ResponseEntity<?> response = taskController.changeIsCompleted("123");
        assertEquals(task, response.getBody());
    }

    @Test
    public void shouldReturnBadRequestWhenServiceThrowsExceptionOnChangeIsCompleted() {
        when(taskService.changeIsCompleted("123")).thenThrow(new RuntimeException());
        ResponseEntity<?> response = taskController.changeIsCompleted("123");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldGenerateRandomTasksWithinRange() {
        ResponseEntity<?> response = taskController.generateRandomTasks();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenExceptionOccursInGenerateRandomTasks() {
        doThrow(new RuntimeException()).when(taskService).generateRandomTasks();
        ResponseEntity<?> response = taskController.generateRandomTasks();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenExceptionOccursInDeleteAllTasks() {
        doThrow(new RuntimeException()).when(taskService).deleteAllTasks();
        ResponseEntity<?> response = taskController.deleteAllTasks();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturnZeroTaskCountWhenNoTasksExist() {
        when(taskService.getAllTasks()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = taskController.countTasks();
        assertEquals(0, response.getBody());
    }

    @Test
    public void shouldReturnBadRequestWhenExceptionOccursInCountTasks() {
        doThrow(new RuntimeException()).when(taskService).countTasks();
        ResponseEntity<?> response = taskController.countTasks();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}