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

    @NotNull(message = "Street number should not be a null value")
    private String streetNr;

    private String streetNrSuffix;
    private String streetNrLast;
    private String streetNrLastSuffix;

    @NotNull(message = "Street name should not be a null value")
    private String streetName;

    private String streetType;
    private String streetSuffix;

    @NotNull(message = "Postcode should not be a null value")
    private String postcode;

    private String locality;

    @NotNull(message = "City should not be a null value")
    private String city;

    @NotNull(message = "State or province should not be a null value")
    private String stateOrProvince;

    @NotNull(message = "Country number should not be a null value")
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
