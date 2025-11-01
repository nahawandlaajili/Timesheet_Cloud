package com.cloud.leaveservice.dto;

import java.time.LocalDate;

public class LeaveRequestDTO {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private int daysRequested;

    // Getters & Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getDaysRequested() { return daysRequested; }
    public void setDaysRequested(int daysRequested) { this.daysRequested = daysRequested; }
}
