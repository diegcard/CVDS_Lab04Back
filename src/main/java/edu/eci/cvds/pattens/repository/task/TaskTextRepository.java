package edu.eci.cvds.pattens.repository.task;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.eci.cvds.pattens.model.Task;

import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is in charge of managing the tasks repository, when the repository is a text file
 */
@Repository
public class TaskTextRepository implements TaskRepository{

    private static String FILE_PATH = "src/main/resources/task.json";
    private final ObjectMapper objectMapper;

    /**
     * Constructor of the class
     */
    public TaskTextRepository() {

        this.objectMapper = new ObjectMapper();
        configureObjectMapper();
    }

    /**
     * Constructor of the class
     */
    public TaskTextRepository(String filePath) {
        this.objectMapper = new ObjectMapper();
        configureObjectMapper();
        this.FILE_PATH = filePath;
    }

    private void configureObjectMapper() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Generates a unique 5-character alphanumeric ID.
     * The ID is composed of uppercase letters, lowercase letters, and digits.
     * Ensures that the generated ID is unique by checking against a set of previously generated IDs.
     *
     * @returns string A unique 8-character alphanumeric ID.
     */
    private String generateId() {
        String id = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        do {
            for (int i = 0; i < 8; i++) {
                id += characters.charAt((int) Math.floor(Math.random() * characters.length()));
            }
        } while (existsById(id));
        return id;
    }

    /**
     * Save and create a new task
     * @param task the task to create
     * if the task does not have an ID, it is generated
     * @return the created task
     */
    @Override
    public Task saveTask(Task task) {
        List<Task> tasks = findAllTasks();
        task.setIsCompleted(false);
        task.setCreationDate(LocalDate.now());
        task.setFinishDate(null);
        if (task.getId() == null) {
            task.setId(generateId());
        }
        tasks.add(task);
        writeTasksToFile(tasks);
        return task;
    }

    /**
     * Find a task by its ID
     * if the task does not exist, throw an exception
     * @param id the ID of the task
     * @return the task if found, otherwise null
     */
    @Override
    public Task findTaskById(String id) {
        if(!existsById(id)){
            throw new RuntimeException("Task not found");
        }
        return findAllTasks().stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }


    /**
     * Find all existing tasks in the repository
     * @return a list of all tasks
     */
    @Override
    public List<Task> findAllTasks() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, Task.class));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Delete a task from the repository
     * if the task does not exist, throw an exception
     * @param task the task to delete
     */
    @Override
    public void deleteTask(Task task) {
        List<Task> tasks = findAllTasks();
        if (!tasks.removeIf(t -> t.getId().equals(task.getId()))) {
            throw new RuntimeException("Task not found");
        }
        tasks.removeIf(t -> t.getId().equals(task.getId()));
        writeTasksToFile(tasks);
    }

    /**
     * Update an existing task
     * @param task the task to update
     */
    @Override
    public Task updateTask(Task task) {
        List<Task> tasks = findAllTasks();
        for (Task t : tasks) {
            if (t.getId().equals(task.getId())) {
                t.setNameTask(task.getNameTask());
                t.setIsCompleted(task.getIsCompleted());
                t.setDescriptionTask(task.getDescriptionTask());
                t.setDifficultyLevel(task.getDifficultyLevel());
                t.setEstimatedTime(task.getEstimatedTime());
                t.setPriority(task.getPriority());
                t.setFinishDate(task.getFinishDate());
                t.setCreationDate(task.getCreationDate());
                break;
            }
        }
        writeTasksToFile(tasks);
        return task;
    }

    /**
     * Check if a task exists in the repository
     * @param id the ID of the task
     * @return true if the task exists, otherwise false
     */
    @Override
    public boolean existsById(String id) {
        return findAllTasks().stream().anyMatch(task -> task.getId().equals(id));
    }

    /**
     * Write the tasks to the file
     * @param tasks the tasks to write
     */
    public void writeTasksToFile(List<Task> tasks) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
