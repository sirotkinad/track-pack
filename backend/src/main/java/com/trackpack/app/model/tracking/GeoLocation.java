package com.trackpack.app.model.tracking;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "Geolocation", schema = "\"track-pack-db\"")
@Data
@NoArgsConstructor
public class GeoLocation {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private String name;

    private String type;

    @OneToOne
    @JoinColumn(name = "geographicpoint", referencedColumnName = "id")
    private GeoPoint geographicPoint;

    public GeoLocation(String name, String type, GeoPoint geographicPoint) {
        this.name = name;
        this.type = type;
        this.geographicPoint = geographicPoint;
    }

}
