package com.trackpack.app.model.tracking;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "SubAddress")
@Data
@NoArgsConstructor
public class SubAddress {

    @Id
    @GeneratedValue
    private UUID id;
    private String type;
    private String name;
    private String subUnitType;
    private String subUnitNumber;
    private String levelNumber;
    private String buildingName;
    private String privateStreetName;
    private String privateStreetNumber;

    @ManyToOne(fetch = FetchType.LAZY)
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
