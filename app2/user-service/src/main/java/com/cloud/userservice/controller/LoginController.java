// Corrected LoginController
package com.cloud.userservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class LoginController {

    // Handles frontend routes like "/login" and others.
    // The regex `/{path:^(?!api$).*$}` means it will forward any path
    // that does not start with "/api" to index.html.
    @GetMapping({"/login", "/{path:^(?!api$).*$}"})
    public String index() {


        return "this is login page";
    }
}