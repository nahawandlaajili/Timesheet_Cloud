package com.cloud.leaveservice.controller;

import com.cloud.leaveservice.model.LeaveRequest;
import com.cloud.leaveservice.service.LeaveService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class LeaveController {

    private final LeaveService leaveService;
    private static final Logger logger = LoggerFactory.getLogger(LeaveController.class);

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestLeave(@RequestParam Long userId,
                                          @RequestParam String startDate,
                                          @RequestParam String endDate) {
        try {
            LeaveRequest leaveRequest = leaveService.requestLeave(userId,
                    LocalDate.parse(startDate),
                    LocalDate.parse(endDate));
            return ResponseEntity.ok(leaveRequest);
        } catch (Exception e) {
            logger.error("Failed to submit leave request", e); // <-- log the stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error submitting leave request.");
        }
    }


    @PutMapping("/{leaveId}/approve")
    public LeaveRequest approveLeave(@PathVariable Long leaveId) {
        return leaveService.approveLeave(leaveId);
    }

    @PutMapping("/{leaveId}/reject")
    public LeaveRequest rejectLeave(@PathVariable Long leaveId) {
        return leaveService.rejectLeave(leaveId);
    }

    @GetMapping
    public List<LeaveRequest> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    @GetMapping("/user/{userId}")
    public List<LeaveRequest> getUserLeaves(@PathVariable Long userId) {
        return leaveService.getUserLeaves(userId);
    }
}