package com.trackpack.app.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Entity
@Table(name = "users", schema = "\"track-pack-db\"")
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "First name should not be a null value")
    private String firstName;

    @NotNull(message = "Last name should not be a null value")
    private String lastName;

    @NotNull(message = "Email should not be a null value")
    private String email;

    @NotNull(message = "Password should not be a null value")
    private String password;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}

