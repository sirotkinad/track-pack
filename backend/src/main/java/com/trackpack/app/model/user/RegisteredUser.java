package com.trackpack.app.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class RegisteredUser {

    private UUID id;

    @NotNull(message = "Email should not be a null value")
    private String email;

    @NotNull(message = "Password should not be a null value")
    private String password;

    private String token;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}


