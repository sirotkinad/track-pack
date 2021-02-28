package com.trackpack.app.model.trackingmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class CheckPoint {

    private String status;
    private String message;
    private LocalDateTime date;
    private String checkPost;
    private String city;
    private String stateOrProvince;
    private String country;

    public CheckPoint(String status,LocalDateTime date, String checkPost) {
        this.status = status;
        this.date = date;
        this.checkPost = checkPost;
    }

}
