package com.cloud.userservice.service;

import com.cloud.userservice.model.LeaveRequest;
import com.cloud.userservice.model.UserProfile;
import com.cloud.userservice.repository.LeaveRequestRepository;
import com.cloud.userservice.repository.UserProfileRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveService {

    private final LeaveRequestRepository leaveRepo;
    private final UserProfileRepository userRepo;

    public LeaveService(LeaveRequestRepository leaveRepo, UserProfileRepository userRepo) {
        this.leaveRepo = leaveRepo;
        this.userRepo = userRepo;
    }

    // =====================
    // Employee Requests
    // =====================
    public LeaveRequest requestLeave(Long userId, String startDate, String endDate) {
        UserProfile user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        long days = ChronoUnit.DAYS.between(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        ) + 1;

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setUserId(userId);
        leaveRequest.setStartDate(LocalDate.parse(startDate));
        leaveRequest.setEndDate(LocalDate.parse(endDate));
        leaveRequest.setDaysRequested((int) days);
        leaveRequest.setStatus("PENDING"); // wait for admin approval

        return leaveRepo.save(leaveRequest);
    }

    // =====================
    // Admin Approval
    // =====================
    public LeaveRequest approveLeave(Long leaveId) {
        LeaveRequest leave = leaveRepo.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        UserProfile user = userRepo.findById(leave.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (leave.getDaysRequested() > user.getRemainingDaysOff()) {
            throw new RuntimeException("Not enough leave days available");
        }

        leave.setStatus("APPROVED");
        user.setRemainingDaysOff(user.getRemainingDaysOff() - leave.getDaysRequested());

        userRepo.save(user);
        return leaveRepo.save(leave);
    }

    public LeaveRequest rejectLeave(Long leaveId) {
        LeaveRequest leave = leaveRepo.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus("REJECTED");
        return leaveRepo.save(leave);
    }

    // =====================
    // Yearly Reset Job
    // =====================
    @Scheduled(cron = "0 0 0 1 1 *") // Runs every Jan 1st at 00:00
    public void resetLeaveBalances() {
        List<UserProfile> users = userRepo.findAll();
        for (UserProfile user : users) {
            user.setRemainingDaysOff(30);
        }
        userRepo.saveAll(users);
        System.out.println("✅ Leave balances reset for all users on " + LocalDate.now());
    }

    public List<LeaveRequest> getAllLeaves() {
        return leaveRepo.findAll();
    }

    public List<LeaveRequest> getLeavesByUser(Long userId) {
        return leaveRepo.findByUserId(userId);
    }

}
