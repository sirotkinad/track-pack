package com.trackpack.app.model.notification;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue
    private UUID eventId;
    private LocalDateTime eventTime;
    private String eventType;

    public Notification(LocalDateTime eventTime, String eventType) {
        this.eventTime = eventTime;
        this.eventType = eventType;
    }
}
