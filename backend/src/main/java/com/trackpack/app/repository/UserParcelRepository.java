package com.trackpack.app.repository;

import com.trackpack.app.keys.UserParcelKey;
import com.trackpack.app.model.joining.UserParcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserParcelRepository extends JpaRepository<UserParcel, UserParcelKey> {

    @Query(value = "SELECT Cast(parcel_id AS VARCHAR) FROM \"track-pack-db\".user_parcel " +
            "WHERE user_id =:userId", nativeQuery = true)
    Optional<List<String>> getParcelsByUserId(@Param("userId") UUID userId);

}
