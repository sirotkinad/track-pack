package com.trackpack.app.model.tracking;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Address", schema = "\"track-pack-db\"")
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "streetnr")
    private String streetNr;

    @Column(name = "streetnrsuffix")
    private String streetNrSuffix;
    @Column(name = "streetnrlast")
    private String streetNrLast;
    @Column(name = "streetnrlastsuffix")
    private String streetNrLastSuffix;

    @NotNull
    @Column(name = "streetname")
    private String streetName;

    @Column(name = "streettype")
    private String streetType;
    @Column(name = "streetsuffix")
    private String streetSuffix;

    @NotNull
    private String postcode;

    private String locality;

    @NotNull
    private String city;

    @NotNull
    @Column(name = "stateorprovince")
    private String stateOrProvince;

    @NotNull
    private String country;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "geographiclocation", referencedColumnName = "id")
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
