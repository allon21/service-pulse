package com.servicepulse.web;

import com.servicepulse.service.MonitoredServiceService;
import com.servicepulse.web.dto.CreateServiceForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ServiceManagementController {

    private final MonitoredServiceService serviceService;

    public ServiceManagementController(MonitoredServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/services/new")
    public String newServiceForm(Model model) {
        model.addAttribute("form", new CreateServiceForm());
        return "service-create";
    }

    @PostMapping("/services")
    public String createService(
            @Valid CreateServiceForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "service-create";
        }

        try {
            serviceService.create(form.getName(), form.getUrl());
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "service-create";
        }

        return "redirect:/dashboard";
    }
}
