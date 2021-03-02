package com.trackpack.app.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;


@Data
@AllArgsConstructor
public class ShipmentTracking {

    private final UUID uuid = UUID.randomUUID();
    private String href;
    private String carrier;
    private String trackingCode;
    private String carrierTrackingUrl;
    private OffsetDateTime trackingDate;
    private String status;
    private OffsetDateTime statusChangeDate;
    private String statusChangeReason;
    private int weight;
    private OffsetDateTime estimatedDeliveryDate;
    private Address addressFrom;
    private Address addressTo;
    private ArrayList<CheckPoint> checkpoint;

    public ShipmentTracking(String carrier, String trackingCode, OffsetDateTime trackingDate,
                            Address addressTo) {
        this.carrier = carrier;
        this.trackingCode = trackingCode;
        this.trackingDate = trackingDate;
        this.addressTo = addressTo;
    }

}
