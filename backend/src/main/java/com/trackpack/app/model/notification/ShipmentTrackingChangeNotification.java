package com.trackpack.app.model.notification;

import com.trackpack.app.model.tracking.ShipmentTracking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShipmentTrackingChangeNotification extends Notification {

    ShipmentTracking shipmentTracking;

}
