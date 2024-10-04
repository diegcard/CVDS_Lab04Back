package edu.eci.cvds.pattens.service;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

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
    public Task createTask(Task task) throws Exception, DataIntegrityViolationException, RuntimeException {
        try {
            if (taskRepository.existsById(task.getId())) {
                throw new DataIntegrityViolationException("Task already exists");
            }
            if (!(task.getDifficultyLevel().equalsIgnoreCase("low") || task.getDifficultyLevel().equalsIgnoreCase("medium" )|| task.getDifficultyLevel().equalsIgnoreCase("high"))) {
                throw new DataIntegrityViolationException("Invalid difficulty level");
            }
            if (task.getPriority() < 1 || task.getPriority() > 5) {
                throw new DataIntegrityViolationException("Invalid priority");
            }
            if(task.getEstimatedTime().isBefore(LocalDate.now())){
                throw new RuntimeException("Invalid time");
            }
            return taskRepository.saveTask(task);
        } catch (TransactionSystemException e) {
            throw new TransactionSystemException("Error creating task");
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
            if (!taskRepository.existsById(task.getId())) {
                throw new DataIntegrityViolationException("Task not found");
            }
            if (!(task.getDifficultyLevel().equalsIgnoreCase("low") || task.getDifficultyLevel().equalsIgnoreCase("medium" )|| task.getDifficultyLevel().equalsIgnoreCase("high"))) {
                throw new DataIntegrityViolationException("Invalid difficulty level");
            }
            if (task.getPriority() < 1 || task.getPriority() > 5) {
                throw new DataIntegrityViolationException("Invalid priority");
            }
            if(task.getEstimatedTime().isBefore(LocalDate.now())){
                throw new RuntimeException("Invalid time");
            }
            return taskRepository.updateTask(task);
        } catch (TransactionSystemException e) {
            throw new TransactionSystemException("Error creating task");
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

    /**
     * Generate a radom number of tasks between 100 and 1000
     *
     *
     * @return the number of tasks created
     */
    public int generateRandomTasks() {
        Random random = new Random();
        int numberTasks = random.nextInt(901) + 100;
        for (int i = 0; i < numberTasks; i++) {
            Task task = createRandomTask();
            try {
                taskRepository.saveTask(task);
            } catch (Exception e) {
                i--;
            }
        }
        return numberTasks;
    }

    /**
     * Create a random task
     *
     * @return the task created
     */
    private Task createRandomTask() {
        Random random = new Random();

        String[] levels = {"low", "medium", "high"};

        Task task = new Task();

        task.setNameTask("Random Task" + random.nextInt(1000));
        task.setDescriptionTask("This is a randomly generated task");
        task.setDifficultyLevel(levels[random.nextInt(3)]);
        task.setPriority(random.nextInt(5) + 1);
        task.setEstimatedTime(LocalDate.now().plusDays(random.nextInt(90) + 1));
        return task;
    }

    /**
     * This method is in charge to delete all existing tasks
     *
     * @return the number of tasks deleted
     */
    public int deleteAllTasks() {
        List<Task> tasks = getAllTasks();
        for (Task task : tasks) {
            deleteTask(task.getId());
        }
        return tasks.size();
    }

    /**
     * Method that return how many tasks are in the application
     *
     * @return the number of tasks
     */
    public int countTasks() {
        return getAllTasks().size();
    }
}