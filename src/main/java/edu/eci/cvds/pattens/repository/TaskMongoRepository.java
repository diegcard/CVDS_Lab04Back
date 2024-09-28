package edu.eci.cvds.pattens.repository;

import edu.eci.cvds.pattens.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface is in charge of managing the tasks repository,
 * when the repository is a mongo repository
 */
@Repository
public interface TaskMongoRepository extends TaskRepository,MongoRepository<Task,String>{

    /**
     * Save and create a new task
     * @param task the task to create
     * @return the created task
     */
    @Override
    public default Task saveTask(Task task){
        task.setIsCompleted(false);
        if(task.getId() == null){
            task.setId(java.util.UUID.randomUUID().toString());
        }
        save(task);
        return task;
    }

    /**
     * Find all existing tasks in the repository
     * if there are no tasks, return an empty list
     * @return a list of all tasks
     */
    @Override
    public default List<Task> findAllTasks(){
        return findAll();
    }

    /**
     * Delete a task from the repository
     * if the task does not exist, throw an exception
     * @param task the task to delete
     */
    @Override
    public default void deleteTask(Task task){
        if(!existsById(task.getId())){
            throw new RuntimeException("Task not found");
        }
        delete(task);
    }

    /**
     * Find a task by its ID
     * if the task does not exist, throw an exception
     * @param id the ID of the task
     * @return the task if found, otherwise null
     */
    @Override
    public default Task findTaskById(String id){
        return findById(id).orElse(null);
    }

    /**
     * Update an existing task
     * if the task does not exist, throw an exception
     * @param task the task to update
     */
    @Override
    public default Task updateTask(Task task){
        if(!existsById(task.getId())){
            throw new RuntimeException("Task not found");
        }
        save(task);
        return task;
    }


    /**
     * Check if a task exists in the repository
     * @param id the ID of the task
     * @return true if the task exists, otherwise false
     */
    @Override
    public default boolean existsById(String id){
        Task task = findById(id).orElse(null);
        return task != null;
    }
}
