package com.trackpack.app.model.trackingmodel;

import com.trackpack.app.model.usermodel.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Data
@AllArgsConstructor
public class ShipmentTracking {

    private String id;
    private String href;
    private String carrier;
    private String trackingCode;
    private String carrierTrackingUrl;
    private LocalDateTime trackingDate;
    private String status;
    private LocalDateTime statusChangeDate;
    private String statusChangeReason;
    private int weight;
    private LocalDateTime estimatedDeliveryDate;
    private Address addressFrom;
    private Address addressTo;
    private ArrayList<CheckPoint> checkpoint;
    private User user;

    public ShipmentTracking(String id, String carrier, String trackingCode, LocalDateTime trackingDate,
                            Address addressTo, User user) {
        this.id = id;
        this.carrier = carrier;
        this.trackingCode = trackingCode;
        this.trackingDate = trackingDate;
        this.addressTo = addressTo;
        this.user = user;
    }

}
