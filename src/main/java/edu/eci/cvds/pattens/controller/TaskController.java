package edu.eci.cvds.pattens.controller;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * this class is the controller for the tasks of the application
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /**
     * this method is in charge of returning all the tasks in the application
     *
     * @return a list of all tasks
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * this method is in charge of returning a task by its id
     * @param id
     * @return the task if found, otherwise null
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * this method is in charge of create a new task in the application
     * calling the service
     * @param task the task to create
     * @return the created task
     */
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.createTask(task));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * this method is in charge of update a task in the application
     * calling the service
     * @param task
     * @return the updated task
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody Task task) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(task));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * this method is in charge of delete a task in the application
     * calling the service
     * @param id the ID of the task
     * @return the updated task
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.status(HttpStatus.OK).body("Task deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * this method is in charge of chanhe to
     * done a task in the application calling the service
     * @param id the ID of the task
     * @return the updated task
     */
    @PutMapping("/done/{id}")
    public ResponseEntity<?> doneTask(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.doneTask(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * this method is in charge of chanhe to
     * undone a task in the application calling the service
     * @param id the ID of the task
     * @return the updated task
     */
    @PutMapping("/undone/{id}")
    public ResponseEntity<?> undoneTask(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.undoneTask(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
