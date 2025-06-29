// user-profile-service/src/main/java/com/cloud/userprofile/service/UserProfileService.java
package com.cloud.userprofile.service;

import com.cloud.userprofile.model.UserProfile;
import com.cloud.userprofile.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    public List<UserProfile> getAll() {
        return repository.findAll();
    }

    public Optional<UserProfile> getById(Long id) {
        return repository.findById(id);
    }

    public UserProfile save(UserProfile userProfile) {
        return repository.save(userProfile);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
