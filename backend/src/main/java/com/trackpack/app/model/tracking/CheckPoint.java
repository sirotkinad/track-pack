package com.trackpack.app.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "Checkpoint", schema = "\"track-pack-db\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckPoint {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private String status;

    private String message;

    @NotNull
    private OffsetDateTime date;

    @NotNull
    @Column(name = "checkpost")
    private String checkPost;

    @NotNull
    private String city;

    @Column(name = "stateorprovince")
    private String stateOrProvince;

    @NotNull
    private String country;

    public CheckPoint(String status, OffsetDateTime date, String checkPost) {
        this.status = status;
        this.date = date;
        this.checkPost = checkPost;
    }

}
