package com.cloud.userservice.dto;

public class AuthResponses {
    public static class JwtResponse {
        private String token;

        public JwtResponse() {}
        public JwtResponse(String token) { this.token = token; }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}


