package com.trackpack.app.model.usermodel;

import com.trackpack.app.model.trackingmodel.Address;
import com.trackpack.app.model.trackingmodel.ShipmentTracking;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<Address> addresses;
    private ArrayList<ShipmentTracking> parcels;


    public User(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}

