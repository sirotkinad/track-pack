package com.trackpack.app.repository;

import com.trackpack.app.model.tracking.ShipmentTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentTrackingRepository extends JpaRepository<ShipmentTracking, UUID> {

    @Query(value = "SELECT * FROM \"track-pack-db\".shipment_tracking WHERE tracking_code =:trackingCode", nativeQuery = true)
    Optional<ShipmentTracking> findByTrackingCode(@Param("trackingCode") String trackingCode);

    @Query(value = "SELECT * FROM \"track-pack-db\".shipment_tracking " +
            "WHERE TIMESTAMP 'today' > DATE_TRUNC('day', estimated_delivery_date)", nativeQuery = true)
    List<ShipmentTracking> getDeliveredParcels();

    @Query(value  = "SELECT * FROM \"track-pack-db\".shipment_tracking " +
            "WHERE DATE_TRUNC('day', estimated_delivery_date) >= :lastUpdateDate AND DATE_TRUNC('day', estimated_delivery_date) < TIMESTAMP 'today'", nativeQuery = true)
    List<ShipmentTracking> getNewDeliveredParcels(@Param("lastUpdateDate") OffsetDateTime lastUpdateDate);

    @Query(value = "SELECT a.city FROM \"track-pack-db\".shipment_tracking st, \"track-pack-db\".address a " +
            "WHERE st.address_from = a.id AND st.id =:id", nativeQuery = true)
    String findCityFromById(@Param("id") UUID id);

    @Query(value = "SELECT a.city FROM \"track-pack-db\".shipment_tracking st, \"track-pack-db\".address a\n" +
            "WHERE st.address_to = a.id AND st.id =:id", nativeQuery = true)
    String findCityToById(@Param("id") UUID id);

    @Query(value = "SELECT *FROM \"track-pack-db\".shipment_tracking WHERE TIMESTAMP 'today' < DATE_TRUNC('day', estimated_delivery_date) OR estimated_delivery_date IS NULL",
            nativeQuery = true)
    List<ShipmentTracking> getCurrentParcels();

}
