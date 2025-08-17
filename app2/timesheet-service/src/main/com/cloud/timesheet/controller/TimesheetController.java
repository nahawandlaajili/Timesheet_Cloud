package com.cloud.timesheet.controller;

import com.cloud.timesheet.model.Timesheet;
import com.cloud.timesheet.repository.TimesheetRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timesheets")
public class TimesheetController {
    private final TimesheetRepository repository;

    public TimesheetController(TimesheetRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Timesheet> getAllTimesheets() {
        return repository.findAll();
    }

    @PostMapping
    public Timesheet createTimesheet(@RequestBody Timesheet timesheet) {
        return repository.save(timesheet);
    }
}
