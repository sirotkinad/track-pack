package com.trackpack.app.model.notificationmodel;

import com.trackpack.app.model.trackingmodel.ShipmentTracking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShipmentTrackingChangeNotification extends Notification{

    ShipmentTracking resource;

}
