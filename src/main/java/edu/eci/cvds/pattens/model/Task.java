package edu.eci.cvds.pattens.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String descripcionTarea;
}