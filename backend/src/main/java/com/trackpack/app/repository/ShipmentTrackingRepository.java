package com.trackpack.app.repository;

import com.trackpack.app.model.tracking.ShipmentTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentTrackingRepository extends JpaRepository<ShipmentTracking, UUID> {

    @Query(value = "SELECT * FROM \"track-pack-db\".shipment_tracking WHERE tracking_code =:trackingCode", nativeQuery = true)
    Optional<ShipmentTracking> findByTrackingCode(@Param("trackingCode") String trackingCode);

}
