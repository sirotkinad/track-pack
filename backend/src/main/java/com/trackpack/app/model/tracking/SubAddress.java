package com.trackpack.app.model.tracking;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Subaddress", schema = "\"track-pack-db\"")
@Data
@NoArgsConstructor
public class SubAddress {

    @Id
    @GeneratedValue
    private UUID id;
    private String type;
    private String name;

    @Column(name = "subunittype")
    private String subUnitType;
    @Column(name = "subunitnumber")
    private String subUnitNumber;
    @Column(name = "levelnumber")
    private String levelNumber;
    @Column(name = "buildingname")
    private String buildingName;
    @Column(name = "privatestreetname")
    private String privateStreetName;
    @Column(name = "privatestreetnumber")
    private String privateStreetNumber;

    @ManyToOne
    @JoinColumn(name="address", nullable=false)
    private Address address;

    public SubAddress(String name, String subUnitType,
                      String subUnitNumber, String levelNumber, String buildingName) {
        this.name = name;
        this.subUnitType = subUnitType;
        this.subUnitNumber = subUnitNumber;
        this.levelNumber = levelNumber;
        this.buildingName = buildingName;
    }

}
