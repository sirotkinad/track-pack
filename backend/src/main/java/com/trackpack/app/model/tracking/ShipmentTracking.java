package com.trackpack.app.model.tracking;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ShipmentTracking")
@NoArgsConstructor
@Data
public class ShipmentTracking {

    @Id
    @GeneratedValue
    private UUID id;
    private String href;
    private String carrier;

    @NotNull
    private String trackingCode;

    private String carrierTrackingUrl;

    @NotNull
    private OffsetDateTime trackingDate;

    @NotNull
    private String status;

    private OffsetDateTime statusChangeDate;
    private String statusChangeReason;
    private int weight;
    private OffsetDateTime estimatedDeliveryDate;

    @ManyToOne
    @JoinColumn(name="addressFrom", nullable=false)
    private Address addressFrom;

    @ManyToOne
    @JoinColumn(name="addressTo", nullable=false)
    private Address addressTo;

    @ManyToMany
    private List<CheckPoint> checkpoints;

    public ShipmentTracking(String carrier, String trackingCode, OffsetDateTime trackingDate,
                            Address addressTo) {
        this.carrier = carrier;
        this.trackingCode = trackingCode;
        this.trackingDate = trackingDate;
        this.addressTo = addressTo;
    }

}
