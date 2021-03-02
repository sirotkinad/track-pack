package com.trackpack.app.model.user;

import com.trackpack.app.model.tracking.Address;
import com.trackpack.app.model.tracking.ShipmentTracking;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
public class User {

    private final UUID uuid = UUID.randomUUID();
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<Address> addresses;
    private ArrayList<ShipmentTracking> parcels;


    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}

