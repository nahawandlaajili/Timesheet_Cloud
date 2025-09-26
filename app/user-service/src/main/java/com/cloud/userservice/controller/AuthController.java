package com.cloud.userservice.controller;

import com.cloud.userservice.dto.AuthResponses.JwtResponse;
import com.cloud.userservice.dto.IntrospectionRequest;
import com.cloud.userservice.dto.IntrospectionResponse;
import com.cloud.userservice.dto.LoginRequest;
import com.cloud.userservice.dto.RegisterRequest;
import com.cloud.userservice.model.UserProfile;
import com.cloud.userservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateAndGenerateToken(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserProfile> signup(@RequestBody RegisterRequest request) {
        UserProfile created = authService.register(request);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/introspect")
    public ResponseEntity<IntrospectionResponse> introspect(@RequestBody IntrospectionRequest request) {
        IntrospectionResponse response = authService.introspectToken(request.getToken());
        return ResponseEntity.ok(response);
    }
}
