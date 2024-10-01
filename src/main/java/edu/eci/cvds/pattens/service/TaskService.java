package edu.eci.cvds.pattens.service;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        //If task is not found, throw an exception
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
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
            //if the id lready exists, throw an exception
            if (taskRepository.existsById(String.valueOf(task.getId()))) {
                throw new DataIntegrityViolationException("Task already exists");
            } else if (!(task.getDifficultyLevel().equalsIgnoreCase("low") || task.getDifficultyLevel().equalsIgnoreCase("medium" )|| task.getDifficultyLevel().equalsIgnoreCase("high"))) {
                throw new DataIntegrityViolationException("Invalid difficulty level");
            } else if (task.getPriority() < 1 || task.getPriority() > 5) {
                throw new DataIntegrityViolationException("Invalid priority");
            } else if (task.getEstimatedTime().before(new Date())) {
                throw new RuntimeException("Invalid estimated time");
            }
            return taskRepository.saveTask(task);
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
                return taskRepository.updateTask(task);
            } else {
                throw new Exception("Task not found");
            }
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
        if(task == null){
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteTask(task);
    }

    /**
     * Change to completed a task by its ID.
     * @param id the ID of the task to change to completed
     * @return the task changed to completed
     */
    public Task doneTask(String id) {
        Task task = getTaskById(id);
        if(task == null){
            throw new RuntimeException("Task not found");
        }
        if(task.getIsCompleted()){
            return task;
        }
        task.setIsCompleted(true);
        return taskRepository.updateTask(task);
    }

    /**
     * Change to not completed a task by its ID.
     * @param id the ID of the task to change to not completed
     * @return the task changed to not completed
     */
    public Task undoneTask(String id) {
        Task task = getTaskById(id);
        if(task == null){
            throw new RuntimeException("Task not found");
        }
        if (!task.getIsCompleted()) {
            return task;
        }
        task.setIsCompleted(false);
        return taskRepository.updateTask(task);
    }

    /**
     * Change the isCompleted of a task.
     * if the task is completed, change to not completed
     * if the task is not completed, change to completed
     * @param id the ID of the task to change
     * @return the task changed
     */
    public Task changeIsCompleted(String id) {
        Task task = getTaskById(id);
        if(task == null){
            throw new RuntimeException("Task not found");
        }
        task.setIsCompleted(!task.getIsCompleted());
        return taskRepository.updateTask(task);
    }
}