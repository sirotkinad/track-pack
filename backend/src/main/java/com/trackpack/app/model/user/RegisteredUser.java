package com.trackpack.app.model.user;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class RegisteredUser {

    private UUID id;

    @NotNull
    private String email;
    @NotNull
    private String password;

    private String token;

}
