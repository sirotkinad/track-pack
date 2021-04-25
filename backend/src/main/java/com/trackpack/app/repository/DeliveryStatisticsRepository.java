package com.trackpack.app.repository;

import com.trackpack.app.model.tracking.DeliveryStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryStatisticsRepository extends JpaRepository<DeliveryStatistics, UUID> {

    @Query(value = "SELECT MAX(last_update_date) FROM \"track-pack-db\".delivery_statistics", nativeQuery = true)
    Timestamp findMaxLastUpdateDate();

    @Query(value = "SELECT * FROM \"track-pack-db\".delivery_statistics WHERE city_from =:cityFrom AND city_to =:cityTo", nativeQuery = true)
    Optional<DeliveryStatistics> findByCities(@Param("cityFrom") String cityFrom, @Param("cityTo") String cityTo);

}
