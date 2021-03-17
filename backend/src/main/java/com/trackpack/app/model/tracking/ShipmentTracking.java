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
@Table(name = "Shipmenttracking", schema = "\"track-pack-db\"")
@NoArgsConstructor
@Data
public class ShipmentTracking {

    @Id
    @GeneratedValue
    private UUID id;
    private String href;
    private String carrier;

    @NotNull
    @Column(name = "trackingcode", unique = true)
    private String trackingCode;

    @Column(name = "carriertrackingurl")
    private String carrierTrackingUrl;

    @NotNull
    @Column(name = "trackingdate")
    private OffsetDateTime trackingDate;

    @NotNull
    private String status;

    @Column(name = "statuschangedate")
    private OffsetDateTime statusChangeDate;
    @Column(name = "statuschangereason")
    private String statusChangeReason;

    @Min(value = 0, message = "Weight should not be a negative value")
    private double weight;

    @Column(name = "estimateddeliverydate")
    private OffsetDateTime estimatedDeliveryDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="addressfrom", nullable=false)
    private Address addressFrom;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="addressto", nullable=false)
    private Address addressTo;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "Checkpointparcel", schema = "\"track-pack-db\"",
            joinColumns = @JoinColumn(name = "parcelid"),
            inverseJoinColumns = @JoinColumn(name = "checkpointid"))
    private List<CheckPoint> checkPoints;

    public ShipmentTracking(String carrier, String trackingCode, OffsetDateTime trackingDate,
                            Address addressTo) {
        this.carrier = carrier;
        this.trackingCode = trackingCode;
        this.trackingDate = trackingDate;
        this.addressTo = addressTo;
    }

}
