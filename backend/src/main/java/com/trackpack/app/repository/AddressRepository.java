package com.trackpack.app.repository;

import com.trackpack.app.model.tracking.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}
