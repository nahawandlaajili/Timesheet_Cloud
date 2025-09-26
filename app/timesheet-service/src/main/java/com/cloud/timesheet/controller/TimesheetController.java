package com.cloud.timesheet.controller;

import com.cloud.timesheet.model.Timesheet;
import com.cloud.timesheet.service.TimesheetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timesheets")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class TimesheetController {
    private final TimesheetService service;

    public TimesheetController(TimesheetService service) {
        this.service = service;
    }

    @GetMapping
    public List<Timesheet> getAllTimesheets() {
        System.out.println("GET /timesheets called");
        return service.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<Timesheet> getByUser(@PathVariable Long userId) {
        System.out.println("GET /timesheets/user/" + userId + " called");
        return service.getByUserId(userId);
    }

    @PostMapping
    public Timesheet createTimesheet(@RequestBody Timesheet timesheet) {
        System.out.println("POST /timesheets called with: " + timesheet);
        return service.save(timesheet);
    }

    // Health check endpoint
    @GetMapping("/health")
    public String health() {
        System.out.println("Health endpoint called");
        return "Timesheet service is running";
    }
}