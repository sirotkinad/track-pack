package com.trackpack.app.model.tracking;

import lombok.Data;
import java.util.UUID;

@Data
public class SubAddress {

    private UUID uuid = UUID.randomUUID();
    private String type;
    private String name;
    private String subUnitType;
    private String subUnitNumber;
    private String levelNumber;
    private String buildingName;
    private String privateStreetName;
    private String privateStreetNumber;

    public SubAddress(String name, String subUnitType,
                      String subUnitNumber, String levelNumber, String buildingName) {
        this.name = name;
        this.subUnitType = subUnitType;
        this.subUnitNumber = subUnitNumber;
        this.levelNumber = levelNumber;
        this.buildingName = buildingName;
    }

}
