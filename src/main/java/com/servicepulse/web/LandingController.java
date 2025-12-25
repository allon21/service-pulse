package com.servicepulse.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {

    @GetMapping("/")
    public String landing() {
        return "landing.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
}
