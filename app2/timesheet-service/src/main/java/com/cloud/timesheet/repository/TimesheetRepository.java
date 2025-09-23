// timesheet-service/src/main/java/com/cloud/timesheet/repository/TimesheetRepository.java
package com.cloud.timesheet.repository;

import com.cloud.timesheet.model.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    List<Timesheet> findByUserId(Long userId);
}


