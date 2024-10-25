package edu.eci.cvds.pattens.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import edu.eci.cvds.pattens.model.Role;

import java.time.LocalDate;


/**
 * This class is in charge of representing the user entity.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "User")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private LocalDate creationDate;
    private LocalDate lastLogin;
    private Role role;
}
