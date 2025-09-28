package com.cloud.userservice.controller;

import com.cloud.userservice.model.LeaveRequest;
import com.cloud.userservice.service.LeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController {

    private final LeaveService leaveService;

    public LeaveRequestController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    // Employee submits leave request
    @PostMapping("/request")
    public ResponseEntity<LeaveRequest> requestLeave(
            @RequestParam Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LeaveRequest leave = leaveService.requestLeave(userId, startDate, endDate);
        return ResponseEntity.ok(leave);
    }

    // Admin approves leave
    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<LeaveRequest> approveLeave(@PathVariable Long leaveId) {
        LeaveRequest leave = leaveService.approveLeave(leaveId);
        return ResponseEntity.ok(leave);
    }

    // Admin rejects leave
    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<LeaveRequest> rejectLeave(@PathVariable Long leaveId) {
        LeaveRequest leave = leaveService.rejectLeave(leaveId);
        return ResponseEntity.ok(leave);
    }

    // Get all leave requests (admin can view)
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }

    // Get leave requests by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LeaveRequest>> getLeavesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(leaveService.getLeavesByUser(userId));
    }
}
