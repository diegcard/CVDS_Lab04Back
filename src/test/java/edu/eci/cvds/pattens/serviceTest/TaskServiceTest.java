package edu.eci.cvds.pattens.serviceTest;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.repository.TaskRepository;
import edu.eci.cvds.pattens.service.TaskService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateTask() throws Exception {
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false);
        when(taskRepository.saveTask(task)).thenReturn(task);
        Task createdTask = taskService.createTask(task);
        assertEquals(task, createdTask);
        verify(taskRepository, times(1)).saveTask(task);
    }

    @Test
    public void shouldThrowExceptionWhenCreateTaskWithExistingId() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false);
        when(taskRepository.existsById("123")).thenReturn(true);
        assertThrows(Exception.class, () -> taskService.createTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenThereAreTransactionSystemExceptions() throws Exception {
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false);
        when(taskRepository.saveTask(task)).thenThrow(new RuntimeException());
        assertThrows(Exception.class, () -> taskService.createTask(task));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.updateTask(task)).thenReturn(task);
        Task updatedTask = taskService.updateTask(task);
        assertEquals(task, updatedTask);
        verify(taskRepository, times(1)).updateTask(task);
    }

    @Test
    public void shouldThrowExceptionWhenUpdateTaskWithNonExistingId() throws Exception {
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false);
        when(taskRepository.existsById("125")).thenReturn(false);
        assertThrows(Exception.class, () -> taskService.updateTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenThereAreTransactionSystemExceptionsOnUpdateTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.updateTask(task)).thenThrow(new RuntimeException());
        assertThrows(Exception.class, () -> taskService.updateTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenThereAreDataIntegrityViolationExceptionOnUpdateTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.updateTask(task)).thenThrow(new RuntimeException());
        assertThrows(Exception.class, () -> taskService.updateTask(task));
    }

    @Test
    public void shouldDeleteTask() {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.findTaskById("123")).thenReturn(task);
        doNothing().when(taskRepository).deleteTask(task);
        taskService.deleteTask("123");
        verify(taskRepository, times(1)).deleteTask(task);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteTaskWithNonExistingId() {
        when(taskRepository.existsById("125")).thenReturn(false);
        assertThrows(RuntimeException.class, () -> taskService.deleteTask("125"));
    }

    @Test
    public void shouldMarkTaskAsCompleted() {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.findTaskById("123")).thenReturn(task);
        when(taskRepository.updateTask(task)).thenReturn(task);
        Task completedTask = taskService.doneTask("123");
        assertTrue(completedTask.getIsCompleted());
    }



    @Test
    public void shouldThrowExceptionWhenMarkTaskAsCompletedWithNonExistingId() {
        when(taskRepository.existsById("125")).thenReturn(false);
        assertThrows(RuntimeException.class, () -> taskService.doneTask("125"));
    }

    @Test
    public void shouldMarkTaskAsNotCompleted() {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", true);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.findTaskById("123")).thenReturn(task);
        when(taskRepository.updateTask(task)).thenReturn(task);
        Task notCompletedTask = taskService.undoneTask("123");
        assertFalse(notCompletedTask.getIsCompleted());
    }

    @Test
    public void shouldThrowExceptionWhenMarkTaskAsNotCompletedWithNonExistingId() {
        when(taskRepository.existsById("125")).thenReturn(false);
        assertThrows(RuntimeException.class, () -> taskService.undoneTask("125"));
    }


}
