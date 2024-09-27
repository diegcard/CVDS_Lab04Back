package edu.eci.cvds.pattens.controller;

import edu.eci.cvds.pattens.model.Task;
import edu.eci.cvds.pattens.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error getting tasks");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.createTask(task));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating task");
        }
    }
}
