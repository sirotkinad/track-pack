package com.trackpack.app.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GeoLocation {

    private final UUID uuid = UUID.randomUUID();
    private String name;
    private String type;
    private ArrayList<GeoPoint> geographicPoint;

}
