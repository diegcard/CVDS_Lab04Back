package edu.eci.cvds.pattens.modelTest;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTaskTest {

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task();
    }

    @Test
    public void shouldSetAndGetId() {
        String id = "123";
        task.setId(id);
        assertEquals(id, task.getId());
    }

    @Test
    public void shouldSetAndGetNameTask() {
        String nameTask = "Test Task";
        task.setNameTask(nameTask);
        assertEquals(nameTask, task.getNameTask());
    }

    @Test
    public void shouldSetAndGetDescriptionTask() {
        String descriptionTask = "This is a test task";
        task.setDescriptionTask(descriptionTask);
        assertEquals(descriptionTask, task.getDescriptionTask());
    }

    @Test
    public void shouldSetAndGetIsCompleted() {
        task.setIsCompleted(true);
        assertTrue(task.getIsCompleted());

        task.setIsCompleted(false);
        assertFalse(task.getIsCompleted());
    }

    @Test
    public void shouldSetAndGetDifficultyLevel() {
        String difficultyLevel = "high";
        task.setDifficultyLevel(difficultyLevel);
        assertEquals(difficultyLevel, task.getDifficultyLevel());
    }

    @Test
    public void shouldSetAndGetPriority() {
        int priority = 1;
        task.setPriority(priority);
        assertEquals(priority, task.getPriority());
    }

    @Test
    public void shouldSetAndGetCreationDate() {
        task.setCreationDate(LocalDate.now());
        assertEquals(LocalDate.now(), task.getCreationDate());
    }

    @Test
    public void shouldSetAndGetEstimatedTime() {
        task.setEstimatedTime(LocalDate.now());
        assertEquals(LocalDate.now(), task.getEstimatedTime());
    }

    @Test
    public void shouldSetAndGetFinishDate() {
        task.setFinishDate(LocalDate.now());
        assertEquals(LocalDate.now(), task.getFinishDate());
    }

    @Test
    public void shouldSetAndGetUser() {
        User user = new User();
        user.setId("123");
        task.setUser("123");
        assertEquals(user.getId(), task.getUser());
    }
}