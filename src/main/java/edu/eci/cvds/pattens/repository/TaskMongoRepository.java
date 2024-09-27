package edu.eci.cvds.pattens.repository;

import edu.eci.cvds.pattens.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMongoRepository extends TaskRepository,MongoRepository<Task,String>{

    @Override
    public default Task saveTask(Task task){
        save(task);
        return task;
    }
    @Override
    public default List<Task> findAllTasks(){
        return findAll();
    }
    @Override
    public default void deleteTask(Task task){
        delete(task);
    }
    @Override
    public default Task findTaskById(String id){
        return findById(id).orElse(null);
    }
    @Override
    public default void updateTask(Task task){
        save(task);
    }

    @Override
    public default boolean existsById(String id){
        return existsById(id);
    }
}
