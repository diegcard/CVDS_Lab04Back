package edu.eci.cvds.pattens.repository;

import edu.eci.cvds.pattens.model.Task;
import org.apache.tomcat.util.json.JSONParser;

import edu.eci.cvds.pattens.model.Task;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TaskTextRepository implements TaskRepository{

    private static final String FILE_PATH = "src/main/resources/task.json";
    private final ObjectMapper objectMapper;

    public TaskTextRepository() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Task saveTask(Task task) {
        List<Task> tasks = findAllTasks();
        if (task.getId() == null) {
            task.setId(UUID.randomUUID().toString());
        }
        tasks.add(task);
        writeTasksToFile(tasks);
        return task;
    }

    @Override
    public Task findTaskById(String id) {
        return findAllTasks().stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

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

    @Override
    public void deleteTask(Task task) {
        List<Task> tasks = findAllTasks();
        tasks.removeIf(t -> t.getId().equals(task.getId()));
        writeTasksToFile(tasks);
    }

    @Override
    public void updateTask(Task task) {
        List<Task> tasks = findAllTasks();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(task.getId())) {
                tasks.set(i, task);
                break;
            }
        }
        writeTasksToFile(tasks);
    }

    @Override
    public boolean existsById(String id) {
        return findAllTasks().stream().anyMatch(task -> task.getId().equals(id));
    }

    private void writeTasksToFile(List<Task> tasks) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
