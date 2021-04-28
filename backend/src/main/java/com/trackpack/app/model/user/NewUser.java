package com.trackpack.app.model.user;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class NewUser {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;

}
