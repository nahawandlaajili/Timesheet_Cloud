// user-profile-service/src/main/java/com/cloud/userservice/service/UserProfileService.java
package com.cloud.userservice.service;

import com.cloud.userservice.model.UserProfile;
import com.cloud.userservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository userRepo;

    public UserProfileService(UserProfileRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserProfile> getAll() {
        return userRepo.findAll();
    }

    public Optional<UserProfile> getById(Long id) {
        return userRepo.findById(id);
    }

    public UserProfile save(UserProfile userProfile) {
        return userRepo.save(userProfile);
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    public Optional<UserProfile> getByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
