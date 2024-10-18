package edu.eci.cvds.pattens.serviceTest;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.model.User;
import edu.eci.cvds.pattens.repository.task.TaskRepository;
import edu.eci.cvds.pattens.service.TaskService;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    User usuario1 = new User("123", "Test User 1", "testuser1@mail.escuelaing.com", "jaja","User Test 1", LocalDate.now(), LocalDate.now(), null);
    User usuario2 = new User("124", "Test User 2", "testuser2@mail.com", "jaja","User Test 2", LocalDate.now(), LocalDate.now(), null);
    
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
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1);
        when(taskRepository.saveTask(task)).thenReturn(task);
        Task createdTask = taskService.createTask(task);
        assertEquals(task, createdTask);
        verify(taskRepository, times(1)).saveTask(task);
    }

    @Test
    public void shouldThrowExceptionWhenCreateTaskWithExistingId() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
        when(taskRepository.existsById("123")).thenReturn(true);
        assertThrows(Exception.class, () -> taskService.createTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenThereAreDataIntegrityViolationExceptions() throws Exception {
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1);
        when(taskRepository.saveTask(task)).thenThrow(new RuntimeException());
        assertThrows(Exception.class, () -> taskService.createTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenCreateTaskWithInvalidPriority() throws Exception{
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false,"low", 41, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1);
        assertThrows(Exception.class, () -> taskService.createTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenCreateTaskWithInvalidDifficultLevel() throws Exception{
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false,"f", 4, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
        assertThrows(Exception.class, () -> taskService.createTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenThereAreTransactionSystemExceptions() throws Exception {
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1);
        when(taskRepository.saveTask(task)).thenThrow(new RuntimeException());
        assertThrows(Exception.class, () -> taskService.createTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenCreateTaskWithInvalidEstimatedTime() throws Exception{
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false,"high", 1, LocalDate.now(), LocalDate.now().minusDays(1), LocalDate.now(), usuario2);
        assertThrows(Exception.class, () -> taskService.createTask(task));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.updateTask(task)).thenReturn(task);
        Task updatedTask = taskService.updateTask(task);
        assertEquals(task, updatedTask);
        verify(taskRepository, times(1)).updateTask(task);
    }

    @Test
    public void shouldThrowExceptionWhenUpdateTaskWithNonExistingId() throws Exception {
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1);
        when(taskRepository.existsById("125")).thenReturn(false);
        assertThrows(Exception.class, () -> taskService.updateTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateTaskWithInvalidDifficultLevel() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "f", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
        when(taskRepository.existsById("123")).thenReturn(true);
        assertThrows(Exception.class, () -> taskService.updateTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateTaskWithInvalidriority() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "low", -1, LocalDate.now(), LocalDate.now(), LocalDate.now(),usuario1);
        when(taskRepository.existsById("123")).thenReturn(true);
        assertThrows(Exception.class, () -> taskService.updateTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenThereAreTransactionSystemExceptionsOnUpdateTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.updateTask(task)).thenThrow(new RuntimeException());
        assertThrows(Exception.class, () -> taskService.updateTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenThereAreDataIntegrityViolationExceptionOnUpdateTask() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.updateTask(task)).thenThrow(new RuntimeException());
        assertThrows(Exception.class, () -> taskService.updateTask(task));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateTaskWithInvalidEstimatedTime() throws Exception {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "low", 1, LocalDate.now(), LocalDate.now().minusDays(1), LocalDate.now(), usuario2);
        when(taskRepository.existsById("123")).thenReturn(true);
        assertThrows(Exception.class, () -> taskService.updateTask(task));
    }

    @Test
    public void shouldDeleteTask() {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
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
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
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
        Task task = new Task("123", "Test Task 1", "This is a test task 1", true, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1);
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

    @Test
    public void shouldChangeTaskToNotCompletedWhenCompleted() {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", true, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.findTaskById("123")).thenReturn(task);
        when(taskRepository.updateTask(task)).thenReturn(task);
        Task changedTask = taskService.changeIsCompleted("123");
        assertFalse(changedTask.getIsCompleted());
    }

    @Test
    public void shouldThrowExceptionWhenChangeIsCompletedWithNonExistingId() {
        when(taskRepository.existsById("125")).thenReturn(false);
        assertThrows(RuntimeException.class, () -> taskService.changeIsCompleted("125"));
    }

    @Test
    public void shouldToggleTaskCompletionStatusWhenTaskExists() {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.findTaskById("123")).thenReturn(task);
        when(taskRepository.updateTask(task)).thenReturn(task);
        Task toggledTask = taskService.changeIsCompleted("123");
        assertTrue(toggledTask.getIsCompleted());
    }

    @Test
    public void shouldThrowExceptionWhenToggleCompletionStatusOfNonExistingTask() {
        when(taskRepository.existsById("125")).thenReturn(false);
        assertThrows(RuntimeException.class, () -> taskService.changeIsCompleted("125"));
    }

    @Test
    public void shouldSetFinishDateWhenTaskIsCompleted() {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.findTaskById("123")).thenReturn(task);
        when(taskRepository.updateTask(task)).thenReturn(task);
        Task toggledTask = taskService.changeIsCompleted("123");
        Task toggledTask1 = taskService.changeIsCompleted("123");
        assertNotNull(toggledTask1.getFinishDate());
    }

    @Test
    public void shouldRemoveFinishDateWhenTaskIsNotCompleted() {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", true, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.findTaskById("123")).thenReturn(task);
        when(taskRepository.updateTask(task)).thenReturn(task);
        Task toggledTask = taskService.changeIsCompleted("123");
        assertNull(toggledTask.getFinishDate());
    }


    @Test
    public void shouldGenerateRandomTasksWithinRange() {
        int generatedTasks = taskService.generateRandomTasks();
        assertTrue(generatedTasks >= 100 && generatedTasks <= 1000);
    }

    @Test
    public void shouldNotDeleteTasksWhenNoTasksExist() {
        when(taskRepository.findAllTasks()).thenReturn(Collections.emptyList());
        int deletedTasks = taskService.deleteAllTasks();
        assertEquals(0, deletedTasks);
        verify(taskRepository, never()).deleteTask(any(Task.class));
    }

    @Test
    public void shouldReturnCorrectTaskCountWhenTasksExist() {
        List<Task> tasks = Arrays.asList(
            new Task("123", "Test Task 1", "This is a test task 1", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1),
            new Task("124", "Test Task 2", "This is a test task 2", false, "medium", 2, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2)
        );
        when(taskRepository.findAllTasks()).thenReturn(tasks);
        int taskCount = taskService.countTasks();
        assertEquals(tasks.size(), taskCount);
    }

    @Test
    public void shouldReturnZeroTaskCountWhenNoTasksExist() {
        when(taskRepository.findAllTasks()).thenReturn(Collections.emptyList());
        int taskCount = taskService.countTasks();
        assertEquals(0, taskCount);
    }

    //Dado que tengo 1 tarea registrada,
    // Cuando lo consulto a nivel de servicio, Entonces la consulta será exitosa validando el campo id.
    @Test
    public void shouldFindTaskById() {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.findTaskById("123")).thenReturn(task);
        Task foundTask = taskService.getTaskById("123");
        assertEquals("123", foundTask.getId());
    }

    @Test
    public void shouldRetrieveTaskWhenIdExists() {
        String taskId = "123";
        Task task = new Task(taskId, "Test Task", "This is a test task", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1);
        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskRepository.findTaskById(taskId)).thenReturn(task);
        Task retrievedTask = taskService.getTaskById(taskId);
        assertEquals(task, retrievedTask);
    }

    // Dado que no hay ninguna tarea registrada, Cuándo la consulto a nivel de servicio,
    // Entonces la consulta no retornará ningún resultado.
    @Test
    public void shouldThrowExceptionWhenTaskIdDoesNotExist() {
        String nonExistentTaskId = "999";
        when(taskRepository.existsById(nonExistentTaskId)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> taskService.getTaskById(nonExistentTaskId));
    }

    // Dado que no hay ninguna tarea registrada, Cuándo lo creo a nivel de servicio,
    // Entonces la creación será exitosa.
    @Test
    public void shouldReturnTaskWhenServiceReturnsTask() throws Exception {
        Task task = new Task("125", "Test Task 3", "This is a test task 3", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1);
        when(taskRepository.saveTask(task)).thenReturn(task);
        Task createdTask = taskService.createTask(task);
        assertEquals(task, createdTask);
        verify(taskRepository, times(1)).saveTask(task);
    }

    // Dado que tengo 1 tarea registrada, Cuándo la elimino a nivel de servicio,
    // Entonces la eliminación será exitosa.
    @Test
    public void shouldDeleteTask1() {
        Task task = new Task("123", "Test Task 1", "This is a test task 1", false, "high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario2);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.findTaskById("123")).thenReturn(task);
        doNothing().when(taskRepository).deleteTask(task);
        taskService.deleteTask("123");
        verify(taskRepository, times(1)).deleteTask(task);
    }

    // Dado que tengo 1 tarea registrada, Cuándo la elimino y consulto a nivel de servicio, Entonces el resultado de la consulta no retornará ningún resultado.
    @Test
    public void shouldDeleteTask2() throws Exception{
        Task task = new Task("123", "Test Task 3", "This is a test task 3", false,"high", 1, LocalDate.now(), LocalDate.now(), LocalDate.now(), usuario1);
        when(taskRepository.saveTask(task)).thenReturn(task);
        Task createdTask = taskService.createTask(task);
        assertEquals(task, createdTask);
        when(taskRepository.existsById("123")).thenReturn(true);
        when(taskRepository.findTaskById("123")).thenReturn(task);
        doNothing().when(taskRepository).deleteTask(task);
        taskService.deleteTask("123");
        verify(taskRepository, times(1)).deleteTask(task);
        String nonExistentTaskId = "123";
        when(taskRepository.existsById(nonExistentTaskId)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> taskService.getTaskById(nonExistentTaskId));
    }

}
