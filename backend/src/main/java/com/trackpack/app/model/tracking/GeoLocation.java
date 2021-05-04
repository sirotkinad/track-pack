package com.trackpack.app.model.tracking;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "geo_location", schema = "\"track-pack-db\"")
@Data
@NoArgsConstructor
public class GeoLocation {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "GeoLocation's name should not be a null value")
    private String name;

    private String type;

    @OneToOne
    @JoinColumn(name = "geographic_point", referencedColumnName = "id")
    private GeoPoint geographicPoint;

    public GeoLocation(String name, String type, GeoPoint geographicPoint) {
        this.name = name;
        this.type = type;
        this.geographicPoint = geographicPoint;
    }

}
