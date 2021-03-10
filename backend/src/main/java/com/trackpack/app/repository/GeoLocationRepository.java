package com.trackpack.app.repository;

import com.trackpack.app.model.tracking.GeoLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GeoLocationRepository extends JpaRepository<GeoLocation, UUID> {

}
