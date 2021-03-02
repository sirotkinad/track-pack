package com.trackpack.app.model.tracking;

import lombok.Data;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Data
public class Address {

    @Id
    @GeneratedValue
    private UUID id;
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
    private List<SubAddress> subAddress;

    public Address(String streetNr, String streetName, String postcode,
                   String city, String stateOrProvince, String country) {
        this.streetNr = streetNr;
        this.streetName = streetName;
        this.postcode = postcode;
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.country = country;
    }

}
