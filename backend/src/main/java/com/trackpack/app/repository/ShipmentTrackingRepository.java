package com.trackpack.app.repository;

import com.trackpack.app.model.tracking.ShipmentTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ShipmentTrackingRepository extends JpaRepository<ShipmentTracking, UUID> {

}
