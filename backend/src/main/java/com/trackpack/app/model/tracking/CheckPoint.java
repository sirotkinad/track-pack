package com.trackpack.app.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "check_point", schema = "\"track-pack-db\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckPoint {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "CheckPoint's status should not be a null value")
    private String status;

    private String message;

    @NotNull(message = "Date should not be a null value")
    private OffsetDateTime date;

    @NotNull(message = "Check post should not be a null value")
    private String checkPost;

    @NotNull(message = "CheckPoint's city should not be a null value")
    private String city;

    private String stateOrProvince;

    @NotNull(message = "CheckPoint's country should not be a null value")
    private String country;

    public CheckPoint(String status, OffsetDateTime date, String checkPost) {
        this.status = status;
        this.date = date;
        this.checkPost = checkPost;
    }

}
