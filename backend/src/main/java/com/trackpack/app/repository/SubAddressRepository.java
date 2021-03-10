package com.trackpack.app.repository;

import com.trackpack.app.model.tracking.SubAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SubAddressRepository extends JpaRepository<SubAddress, UUID> {

}
