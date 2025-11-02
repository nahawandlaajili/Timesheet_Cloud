package com.cloud.leaveservice.dto;

public class UserLeaveSummaryDTO {
    private Long userId;
    private int remainingDaysOff;

    public UserLeaveSummaryDTO() {}

    public UserLeaveSummaryDTO(Long userId, int remainingDaysOff) {
        this.userId = userId;
        this.remainingDaysOff = remainingDaysOff;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public int getRemainingDaysOff() { return remainingDaysOff; }
    public void setRemainingDaysOff(int remainingDaysOff) { this.remainingDaysOff = remainingDaysOff; }
}