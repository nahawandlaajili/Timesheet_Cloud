package com.cloud.userservice.service;

import com.cloud.userservice.model.UserProfile;
import com.cloud.userservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserProfileRepository userRepo;

    public AuthService(UserProfileRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean login(String name, String password) {
        return userRepo.findByName(name)
                       .map(user -> user.getPassword().equals(password)) // TODO: switch to passwordEncoder
                       .orElse(false);
    }
}
