// user-profile-service/src/main/java/com/cloud/userservice/controller/UserProfileController.java

package com.cloud.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;    
import com.cloud.userservice.model.UserProfile;
import com.cloud.userservice.service.UserProfileService;

import java.util.List;

@RestController
@RequestMapping("/api/user-profiles")
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserProfile> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserProfile create(@Valid @RequestBody UserProfile userProfile) {
        return service.save(userProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}