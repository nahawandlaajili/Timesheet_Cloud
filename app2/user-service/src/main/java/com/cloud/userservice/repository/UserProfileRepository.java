// user-profile-service/src/main/java/com/cloud/userservice/repository/UserProfileRepository.java
package com.cloud.userservice.repository;

import com.cloud.userservice.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByName(String name);  // lookup by username
}