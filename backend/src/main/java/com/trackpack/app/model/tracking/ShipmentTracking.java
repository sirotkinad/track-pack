package com.trackpack.app.model.tracking;

import lombok.Data;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;


@Data
public class ShipmentTracking {

    @Id
    @GeneratedValue
    private UUID id;
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
    private List<CheckPoint> checkpoint;

    public ShipmentTracking(String carrier, String trackingCode, OffsetDateTime trackingDate,
                            Address addressTo) {
        this.carrier = carrier;
        this.trackingCode = trackingCode;
        this.trackingDate = trackingDate;
        this.addressTo = addressTo;
    }

}
