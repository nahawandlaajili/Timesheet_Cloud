package com.cloud.timesheet.dto;

public class IntrospectionRequest {
    private String token;
    
    public IntrospectionRequest() {}
    public IntrospectionRequest(String token) { 
        this.token = token; 
    }
    
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
