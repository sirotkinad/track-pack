package com.trackpack.app.model.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private final UUID eventUuid = UUID.randomUUID();
    private LocalDateTime eventTime;
    private String eventType;

}
