package com.trackpack.app.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "geo_point", schema = "\"track-pack-db\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoPoint {

    @Id
    @GeneratedValue
    private UUID id;
    private String accuracy;

    @NotNull(message = "Latitude should not be a null value")
    private String latitude;

    @NotNull(message = "Longitude should not be a null value")
    private String longitude;

}
