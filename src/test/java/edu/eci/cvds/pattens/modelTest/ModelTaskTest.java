package edu.eci.cvds.pattens.modelTest;

import edu.eci.cvds.pattens.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}