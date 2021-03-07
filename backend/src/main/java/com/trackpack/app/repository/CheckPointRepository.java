package com.trackpack.app.repository;

import com.trackpack.app.model.tracking.CheckPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CheckPointRepository extends JpaRepository<CheckPoint, UUID>  {

}
