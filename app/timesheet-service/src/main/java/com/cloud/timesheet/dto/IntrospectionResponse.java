package com.cloud.timesheet.dto;

public class IntrospectionResponse {
    private boolean active;
    private String username;
    private Long userId;
    private String email;
    
    public IntrospectionResponse() {}
    public IntrospectionResponse(boolean active, String username, Long userId, String email) {
        this.active = active;
        this.username = username;
        this.userId = userId;
        this.email = email;
    }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
