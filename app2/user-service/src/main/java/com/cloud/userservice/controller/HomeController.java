package com.cloud.userservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Forward all frontend routes ("/", "/login", etc.) to React index.html
    @GetMapping({"/", "/login", "/{path:^(?!api$).*$}"})
    public String index() {
        return "forward:/index.html";
    }
}