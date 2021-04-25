package com.trackpack.app.model.tracking;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "delivery_statistics", schema = "\"track-pack-db\"",
        uniqueConstraints= @UniqueConstraint(columnNames = {"city_from", "city_to"}))
@NoArgsConstructor
@Data
public class DeliveryStatistics {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "city_from")
    private String cityFrom;

    @NotNull
    @Column(name = "city_to")
    private String cityTo;

    @NotNull
    @Min(value = 0, message = "Amount should not be a negative value")
    private int parcelAmount;

    @NotNull
    private double averageDeliveryTime;

    @NotNull
    private OffsetDateTime lastUpdateDate;

    public DeliveryStatistics(String cityFrom, String cityTo, int parcelAmount, double averageDeliveryTime, OffsetDateTime lastUpdateDate) {
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.parcelAmount = parcelAmount;
        this.averageDeliveryTime = averageDeliveryTime;
        this.lastUpdateDate = lastUpdateDate;
    }

}
