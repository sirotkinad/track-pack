package com.trackpack.app.repository;

import com.trackpack.app.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}


