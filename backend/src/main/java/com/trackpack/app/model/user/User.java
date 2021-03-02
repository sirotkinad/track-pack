package com.trackpack.app.model.user;

import com.trackpack.app.model.tracking.Address;
import com.trackpack.app.model.tracking.ShipmentTracking;
import lombok.Data;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Data
public class User {

    @Id
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Address> addresses;
    private List<ShipmentTracking> parcels;


    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}

