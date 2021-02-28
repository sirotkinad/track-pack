package com.trackpack.app.model.trackingmodel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubAddress {

    private String id;
    private String type;
    private String name;
    private String subUnitType;
    private String subUnitNumber;
    private String levelNumber;
    private String buildingName;
    private String privateStreetName;
    private String privateStreetNumber;

    public SubAddress(String id, String name, String subUnitType,
                      String subUnitNumber, String levelNumber, String buildingName) {
        this.id = id;
        this.name = name;
        this.subUnitType = subUnitType;
        this.subUnitNumber = subUnitNumber;
        this.levelNumber = levelNumber;
        this.buildingName = buildingName;
    }

}
