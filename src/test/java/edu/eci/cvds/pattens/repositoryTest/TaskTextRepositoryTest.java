package edu.eci.cvds.pattens.repositoryTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.repository.TaskTextRepository;

class TaskTextRepositoryTest {

    private TaskTextRepository repository;
    private ObjectMapper objectMapper;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        // Crear un archivo temporal para las pruebas
        File tempFile = tempDir.resolve("test-tasks.json").toFile();

        // Modificar la constante FILE_PATH en TaskTextRepository para usar el archivo temporal
        // Esto requiere que modifiques la clase TaskTextRepository para permitir inyección del path del archivo
        repository = new TaskTextRepository(tempFile.getAbsolutePath());

        objectMapper = new ObjectMapper();
    }

    @Test
    void testSaveTask() {
        Task task = new Task();
        task.setNameTask("Test Task");
        task.setDescriptionTask("This is a test task");

        Task savedTask = repository.saveTask(task);

        assertNotNull(savedTask.getId());
        assertFalse(savedTask.getIsCompleted());
        assertEquals("Test Task", savedTask.getNameTask());
        assertEquals("This is a test task", savedTask.getDescriptionTask());
    }

    @Test
    void testFindTaskById() {
        Task task = new Task("30","Task 1", "Description 1",false);
        Task savedTask = repository.saveTask(task);
        Task foundTask = repository.findTaskById(task.getId());

        assertNotNull(foundTask);
        assertEquals(savedTask.getId(), task.getId());
        assertEquals("Task 1", task.getNameTask());
    }

    @Test
    void testFindAllTasks() {
        repository.saveTask(new Task("1","Task 1", "Description 1",false));
        repository.saveTask(new Task("2","Task 1", "Description 1",false));

        List<Task> tasks = repository.findAllTasks();

        assertEquals(2, tasks.size());
    }

    @Test
    void testDeleteTask() {
        Task task = repository.saveTask(new Task("3","Task 1", "Description 1",false));

        repository.deleteTask(task);

        assertThrows(RuntimeException.class, () -> repository.findTaskById(task.getId()));
    }

    @Test
    void testUpdateTask() {
        Task task = repository.saveTask(new Task("4","Task 1", "Description 1",false));

        task.setNameTask("Updated");
        task.setDescriptionTask("Updated description");
        task.setIsCompleted(true);

        Task updatedTask = repository.updateTask(task);

        assertEquals("Updated", updatedTask.getNameTask());
        assertEquals("Updated description", updatedTask.getDescriptionTask());
        assertTrue(updatedTask.getIsCompleted());
    }

    @Test
    void testExistsById() {
        Task task = repository.saveTask(new Task("5","Task 1", "Description 1",false));

        assertTrue(repository.existsById(task.getId()));
        assertFalse(repository.existsById("non-existent-id"));
    }
}
