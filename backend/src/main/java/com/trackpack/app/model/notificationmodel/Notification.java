package com.trackpack.app.model.notificationmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private String eventId;
    private LocalDateTime eventTime;
    private String eventType;


}
