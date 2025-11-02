package com.cloud.leave.service;


import com.cloud.leave.dto.IntrospectionRequest;
import com.cloud.leave.dto.IntrospectionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenIntrospectionService {

    private final RestTemplate restTemplate;
    
    @Value("${auth.user-service.url:http://localhost:7070}")
    private String userServiceUrl;

    public TokenIntrospectionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public IntrospectionResponse introspectToken(String token) {
        try {
            String url = userServiceUrl + "/auth/introspect";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            IntrospectionRequest request = new IntrospectionRequest(token);
            HttpEntity<IntrospectionRequest> entity = new HttpEntity<>(request, headers);
            
            return restTemplate.postForObject(url, entity, IntrospectionResponse.class);
        } catch (Exception e) {
            // If user-service is down or token is invalid, return inactive
            return new IntrospectionResponse(false, null, null, null);
        }
    }
}