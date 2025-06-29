// user-profile-service/src/main/java/com/cloud/userprofile/repository/UserProfileRepository.java
package com.cloud.userprofile.repository;

import com.cloud.userprofile.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}