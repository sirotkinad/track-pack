package com.trackpack.app.model.notification;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Notification {

    private UUID eventUuid = UUID.randomUUID();
    private LocalDateTime eventTime;
    private String eventType;

    public Notification(LocalDateTime eventTime, String eventType) {
        this.eventTime = eventTime;
        this.eventType = eventType;
    }
}
