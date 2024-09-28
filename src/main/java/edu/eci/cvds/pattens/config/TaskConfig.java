package edu.eci.cvds.pattens.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import edu.eci.cvds.pattens.repository.TaskRepository;
import edu.eci.cvds.pattens.repository.TaskMongoRepository;
import edu.eci.cvds.pattens.repository.TaskTextRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is in charge of configuring the task repository, if it is mongo or text.
 */
@Configuration
public class TaskConfig {
    // The value of the repository type is obtained from the application.properties file.
    @Value("${task.repository.type}")
    private String repositoryType;

    private final TaskMongoRepository taskMongoRepository;
    private final TaskRepository taskTextRepository;

    /**
     * Constructor of the class. It receives the taskMongoRepository and taskTextRepository as parameters.
     * @param taskMongoRepository
     * @param taskTextRepository
     */
    @Autowired
    public TaskConfig(TaskMongoRepository taskMongoRepository, TaskRepository taskTextRepository) {
        this.taskMongoRepository = taskMongoRepository;
        this.taskTextRepository = taskTextRepository;
    }

    /**
     * This method is in charge of returning the task repository according to the repository type.
     * @return TaskRepository
     */
    @Bean
    public TaskRepository taskRepository() {
        if ("mongo".equalsIgnoreCase(repositoryType)) {
            return taskMongoRepository;
        } else if ("text".equalsIgnoreCase(repositoryType)) {
            return taskTextRepository;
        } else {
            throw new IllegalArgumentException("Unsupported repository type");
        }
    }


}

