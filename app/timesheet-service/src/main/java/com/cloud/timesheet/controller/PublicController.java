package com.cloud.timesheet.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class PublicController {

    @GetMapping("/health")
    public String health() {
        System.out.println("Public health endpoint called");
        return "Public endpoint working";
    }

    @GetMapping("/test")
    public String test() {
        return "Public test endpoint working";
    }
}
