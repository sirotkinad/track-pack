package com.trackpack.app.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoPoint {

    private String accuracy;
    private String x;
    private String y;
    private String z;

}
