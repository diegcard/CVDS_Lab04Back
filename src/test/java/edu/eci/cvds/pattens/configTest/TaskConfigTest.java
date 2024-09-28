package edu.eci.cvds.pattens.configTest;

import edu.eci.cvds.pattens.config.TaskConfig;
import edu.eci.cvds.pattens.repository.TaskMongoRepository;
import edu.eci.cvds.pattens.repository.TaskRepository;
import edu.eci.cvds.pattens.repository.TaskTextRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class TaskConfigTest {

    @Mock
    private TaskMongoRepository taskMongoRepository;

    @Mock
    private TaskTextRepository taskTextRepository;

    @InjectMocks
    private TaskConfig taskConfig;

    private String repositoryType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTaskRepositoryMongo() {
        // Configurar el tipo de repositorio a "mongo"
        repositoryType = "mongo";
        taskConfig = new TaskConfig(taskMongoRepository, taskTextRepository);

        // Aquí se usa reflexión para establecer el valor de la propiedad
        ReflectionTestUtils.setField(taskConfig, "repositoryType", repositoryType);

        TaskRepository repository = taskConfig.taskRepository();

        assertNotNull(repository);
        assertTrue(repository instanceof TaskMongoRepository);
    }

    @Test
    public void testTaskRepositoryText() {
        // Configurar el tipo de repositorio a "text"
        repositoryType = "text";
        taskConfig = new TaskConfig(taskMongoRepository, taskTextRepository);

        // Aquí se usa reflexión para establecer el valor de la propiedad
        ReflectionTestUtils.setField(taskConfig, "repositoryType", repositoryType);

        TaskRepository repository = taskConfig.taskRepository();

        assertNotNull(repository);
        assertTrue(repository instanceof TaskTextRepository);
    }

    @Test
    public void testTaskRepositoryUnsupportedType() {
        // Configurar el tipo de repositorio a un valor no soportado
        repositoryType = "unsupported";
        taskConfig = new TaskConfig(taskMongoRepository, taskTextRepository);

        // Aquí se usa reflexión para establecer el valor de la propiedad
        ReflectionTestUtils.setField(taskConfig, "repositoryType", repositoryType);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskConfig.taskRepository();
        });

        assertEquals("Unsupported repository type", exception.getMessage());
    }
}