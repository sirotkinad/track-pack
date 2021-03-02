package com.trackpack.app.model.tracking;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class GeoLocation {

    private UUID uuid = UUID.randomUUID();
    private String name;
    private String type;
    private List<GeoPoint> geographicPoint;

    public GeoLocation(String name, String type, List<GeoPoint> geographicPoint) {
        this.name = name;
        this.type = type;
        this.geographicPoint = geographicPoint;
    }

}
