package com.cloud.timesheet.controller;

import com.cloud.timesheet.model.Timesheet;
import com.cloud.timesheet.service.TimesheetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timesheets")
public class TimesheetController {
    private final TimesheetService service;

    public TimesheetController(TimesheetService service) {
        this.service = service;
    }

    @GetMapping
    public List<Timesheet> getAllTimesheets() {
        return service.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<Timesheet> getByUser(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PostMapping
    public Timesheet createTimesheet(@RequestBody Timesheet timesheet) {
        return service.save(timesheet);
    }
}


