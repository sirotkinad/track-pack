package com.trackpack.app.model.tracking;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "address", schema = "\"track-pack-db\"")
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private String streetNr;

    private String streetNrSuffix;
    private String streetNrLast;
    private String streetNrLastSuffix;

    @NotNull
    private String streetName;

    private String streetType;
    private String streetSuffix;

    @NotNull
    private String postcode;

    private String locality;

    @NotNull
    private String city;

    @NotNull
    private String stateOrProvince;

    @NotNull
    private String country;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "geographic_location", referencedColumnName = "id")
    private GeoLocation geographicLocation;

    @OneToMany(mappedBy = "address", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
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
