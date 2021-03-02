package com.trackpack.app.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.OffsetDateTime;


@Data
@AllArgsConstructor
public class CheckPoint {

    private String status;
    private String message;
    private OffsetDateTime date;
    private String checkPost;
    private String city;
    private String stateOrProvince;
    private String country;

    public CheckPoint(String status, OffsetDateTime date, String checkPost) {
        this.status = status;
        this.date = date;
        this.checkPost = checkPost;
    }

}
