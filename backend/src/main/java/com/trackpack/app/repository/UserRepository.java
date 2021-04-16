package com.trackpack.app.repository;

import com.trackpack.app.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT * FROM \"track-pack-db\".users WHERE email =:email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

}
