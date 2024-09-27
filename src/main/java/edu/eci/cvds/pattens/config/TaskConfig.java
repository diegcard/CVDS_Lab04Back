package edu.eci.cvds.pattens.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import edu.eci.cvds.pattens.repository.TaskRepository;
import edu.eci.cvds.pattens.repository.TaskMongoRepository;
import edu.eci.cvds.pattens.repository.TaskTextRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class TaskConfig {
    @Value("${task.repository.type}")
    private String repositoryType;

    private final TaskMongoRepository taskMongoRepository;
    private final TaskTextRepository taskTextRepository;

    @Autowired
    public TaskConfig(TaskMongoRepository taskMongoRepository, TaskTextRepository taskTextRepository) {
        this.taskMongoRepository = taskMongoRepository;
        this.taskTextRepository = taskTextRepository;
    }

    @Bean
    public TaskRepository taskRepository() {
        if ("mongo".equalsIgnoreCase(repositoryType)) {
            return taskMongoRepository;
        } else if ("text".equalsIgnoreCase(repositoryType)) {
            return taskTextRepository;
        } else {
            throw new IllegalArgumentException("Tipo de repositorio no soportado");
        }
    }


}

