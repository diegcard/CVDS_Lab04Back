package edu.eci.cvds.pattens.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * This class is in charge of representing the task entity.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "Task")
public class Task {
    @Id
    private String id;
    private String nameTask;
    private String descriptionTask;
    private Boolean isCompleted;
    private String difficultyLevel; //high, medium, low
    private int priority; //1, 2, 3, 4, 5
    private LocalDate creationDate; //dd-mm-yyyy
    private LocalDate estimatedTime; //dd-mm-yyyy
    private LocalDate finishDate; //dd-mm-yyyy
    private String user;
}