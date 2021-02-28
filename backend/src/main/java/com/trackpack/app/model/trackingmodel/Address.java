package com.trackpack.app.model.trackingmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Address {

    private String id;
    private String streetNr;
    private String streetNrSuffix;
    private String streetNrLast;
    private String streetNrLastSuffix;
    private String streetName;
    private String streetType;
    private String streetSuffix;
    private String postcode;
    private String locality;
    private String city;
    private String stateOrProvince;
    private String country;
    private GeoLocation geographicLocation;
    private ArrayList<SubAddress> subAddress;

    public Address(String id, String streetNr, String streetName, String postcode,
                   String city, String stateOrProvince, String country) {
        this.id = id;
        this.streetNr = streetNr;
        this.streetName = streetName;
        this.postcode = postcode;
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.country = country;
    }

}
