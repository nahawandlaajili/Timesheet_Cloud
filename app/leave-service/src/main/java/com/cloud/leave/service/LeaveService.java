package com.cloud.leaveservice.service;

import com.cloud.leaveservice.model.LeaveRequest;
import com.cloud.leaveservice.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveService {

    private final LeaveRequestRepository leaveRepo;

    public LeaveService(LeaveRequestRepository leaveRepo) {
        this.leaveRepo = leaveRepo;
    }

    public LeaveRequest requestLeave(Long userId, LocalDate startDate, LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setUserId(userId);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setDaysRequested((int) days);
        leaveRequest.setStatus("PENDING");

        return leaveRepo.save(leaveRequest);
    }

    public LeaveRequest approveLeave(Long leaveId) {
        LeaveRequest leave = leaveRepo.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus("APPROVED");
        return leaveRepo.save(leave);
    }

    public LeaveRequest rejectLeave(Long leaveId) {
        LeaveRequest leave = leaveRepo.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus("REJECTED");
        return leaveRepo.save(leave);
    }

    public List<LeaveRequest> getAllLeaves() {
        return leaveRepo.findAll();
    }

    public List<LeaveRequest> getUserLeaves(Long userId) {
        return leaveRepo.findByUserId(userId);
    }
}