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
     * this method is in charge of returning a task by its id
     *
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error getting tasks");
        }
    }

    /**
     * this method is in charge of create a new task in the application
     * calling the service
     * @param task
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.createTask(task));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating task");
        }
    }
}
