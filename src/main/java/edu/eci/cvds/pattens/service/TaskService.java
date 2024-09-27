package edu.eci.cvds.pattens.service;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing tasks.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task
     * @return the task if found, otherwise null
     */
    public Task getTaskById(String id) {
        return taskRepository.findTaskById(id);
    }

    /**
     * Retrieves all tasks.
     *
     * @return a list of all tasks
     */
    public List<Task> getAllTasks() {
        
        return taskRepository.findAllTasks();

    }

    /**
     * Creates a new task.
     *
     * @param task the task to create
     * @return the created task
     * @throws Exception if an error occurs during creation
     */
    @Transactional
    public Task createTask(Task task) throws Exception {
        try {
            return taskRepository.saveTask(task);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Task already exists");
        } catch (TransactionSystemException e) {
            throw new TransactionSystemException("Error creating task");
        } catch (Exception e) {
            throw new Exception("Error creating task");
        }
    }

    /**
     * Updates an existing task.
     *
     * @param task the task to update
     * @return the updated task
     * @throws Exception if an error occurs during update or if the task is not found
     */
    @Transactional
    public Task updateTask(Task task) throws Exception {
        try {
            if (taskRepository.existsById(String.valueOf(task.getId()))) {
                return taskRepository.saveTask(task);
            } else {
                throw new Exception("Task not found");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation", e);
        } catch (TransactionSystemException e) {
            throw new TransactionSystemException("Transaction system error", e);
        } catch (Exception e) {
            throw new Exception("An unexpected error occurred", e);
        }
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     */
    public void deleteTask(String id) {
        Task task = getTaskById(id);
        taskRepository.deleteTask(task);
    }
}