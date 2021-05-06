package com.trackpack.app.model.user;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class NewUser {

    @NotNull(message = "First name should not be a null value")
    private String firstName;

    @NotNull(message = "Last name should not be a null value")
    private String lastName;

    @NotNull(message = "Email should not be a null value")
    private String email;

    @NotNull(message = "Password should not be a null value")
    private String password;

}
