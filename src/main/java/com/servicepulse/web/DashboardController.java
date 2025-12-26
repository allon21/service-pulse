package com.servicepulse.web;

import com.servicepulse.service.MonitoringService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final MonitoringService monitoringService;

    public DashboardController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("services", monitoringService.getServices());
        return "dashboard";
    }
}