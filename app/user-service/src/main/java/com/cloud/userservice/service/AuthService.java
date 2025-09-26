package com.cloud.userservice.service;

import com.cloud.userservice.dto.IntrospectionResponse;
import com.cloud.userservice.dto.RegisterRequest;
import com.cloud.userservice.model.UserProfile;
import com.cloud.userservice.repository.UserProfileRepository;
import com.cloud.userservice.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserProfileRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                       UserProfileRepository userRepo, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticateAndGenerateToken(String email, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException ex) {
            throw ex;
        }
        
        // Get user details for token generation
        UserProfile user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));
        
        return jwtUtil.generateTokenForUser(user.getEmail(), user.getId(), user.getName());
    }

    public UserProfile register(RegisterRequest request) {
        UserProfile user = UserProfile.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return userRepo.save(user);
    }

    public IntrospectionResponse introspectToken(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            if (jwtUtil.isTokenValid(token, username)) {
                Long userId = jwtUtil.extractUserId(token);
                String email = jwtUtil.extractEmail(token);
                return new IntrospectionResponse(true, username, userId, email);
            }
        } catch (Exception e) {
            // Token is invalid (expired, malformed, etc.)
        }
        return new IntrospectionResponse(false, null, null, null);
    }
}
