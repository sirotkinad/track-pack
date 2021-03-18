package com.trackpack.app.model.tracking;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shipment_tracking", schema = "\"track-pack-db\"")
@NoArgsConstructor
@Data
public class ShipmentTracking {

    @Id
    @GeneratedValue
    private UUID id;

    private String href;
    private String carrier;

    @NotNull
    @Column(unique = true)
    private String trackingCode;

    private String carrierTrackingUrl;

    @NotNull
    private OffsetDateTime trackingDate;

    @NotNull
    private String status;

    private OffsetDateTime statusChangeDate;
    private String statusChangeReason;

    @Min(value = 0, message = "Weight should not be a negative value")
    private double weight;

    private OffsetDateTime estimatedDeliveryDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="address_from", nullable=false)
    private Address addressFrom;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="address_to", nullable=false)
    private Address addressTo;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "check_point_parcel", schema = "\"track-pack-db\"",
            joinColumns = @JoinColumn(name = "parcel_id"),
            inverseJoinColumns = @JoinColumn(name = "check_point_id"))
    private List<CheckPoint> checkPoints;

    public ShipmentTracking(String carrier, String trackingCode, OffsetDateTime trackingDate,
                            Address addressTo) {
        this.carrier = carrier;
        this.trackingCode = trackingCode;
        this.trackingDate = trackingDate;
        this.addressTo = addressTo;
    }

}
