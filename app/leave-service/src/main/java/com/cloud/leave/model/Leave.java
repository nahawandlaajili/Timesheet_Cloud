package com.cloud.userservice.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDate startDate;
    private LocalDate endDate;

    private int daysRequested;

    private String status = "PENDING"; // PENDING / APPROVED / REJECTED

    public LeaveRequest() {}

    // Getters and setters...
}
