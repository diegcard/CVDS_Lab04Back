package edu.eci.cvds.pattens.repository;

import edu.eci.cvds.pattens.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This interface is in charge of managing the tasks repository,
 * its allow to save, find, delete, update and check if a task exists.
 */
@Component
public interface TaskRepository{
    Task saveTask(Task task);
    Task findTaskById(String id);
    List<Task> findAllTasks();
    void deleteTask(Task task);
    Task updateTask(Task task);
    boolean existsById(String id);
}
