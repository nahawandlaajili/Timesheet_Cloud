// timesheet-service/src/main/java/com/cloud/timesheet/service/TimesheetService.java
package com.cloud.timesheet.service;

import com.cloud.timesheet.model.Timesheet;
import com.cloud.timesheet.repository.TimesheetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimesheetService {

    private final TimesheetRepository repository;

    public TimesheetService(TimesheetRepository repository) {
        this.repository = repository;
    }

    public List<Timesheet> getAll() {
        return repository.findAll();
    }

    public Optional<Timesheet> getById(Long id) {
        return repository.findById(id);
    }

    public List<Timesheet> getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Timesheet save(Timesheet timesheet) {
        return repository.save(timesheet);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}


