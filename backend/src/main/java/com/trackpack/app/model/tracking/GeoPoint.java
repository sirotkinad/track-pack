package com.trackpack.app.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "Geopoint", schema = "\"track-pack-db\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoPoint {

    @Id
    @GeneratedValue
    private UUID id;
    private String accuracy;

    @NotNull
    private String latitude;

    @NotNull
    private String longitude;

}
