package com.trackpack.app.model.trackingmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GeoLocation {

    private String id;
    private String name;
    private String type;
    private ArrayList<GeoPoint> geographicPoint;

}
