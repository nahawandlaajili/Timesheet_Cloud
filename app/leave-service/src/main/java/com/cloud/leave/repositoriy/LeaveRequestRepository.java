package com.cloud.leaveservice.repository;
import com.cloud.leaveservice.model.LeaveRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByUserId(Long userId);
}