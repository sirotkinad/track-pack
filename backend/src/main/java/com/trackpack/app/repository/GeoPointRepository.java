package com.trackpack.app.repository;

import com.trackpack.app.model.tracking.GeoPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GeoPointRepository extends JpaRepository<GeoPoint, UUID> {

}
