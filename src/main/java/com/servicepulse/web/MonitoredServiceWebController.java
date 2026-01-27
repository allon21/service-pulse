package com.servicepulse.web;

import com.servicepulse.persistence.entity.MonitoredServiceEntity;
import com.servicepulse.persistence.repository.MonitoredServiceJpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MonitoredServiceWebController {

    private final MonitoredServiceJpaRepository repository;

    public MonitoredServiceWebController(
            MonitoredServiceJpaRepository repository
    ) {
        this.repository = repository;
    }

    @PostMapping("/services/{id}/toggle")
    public String toggleService(@PathVariable Long id) {

        MonitoredServiceEntity service =
                repository.findById(id)
                        .orElseThrow();

        if (service.isEnabled()) {
            service.disable();
        } else {
            service.enable();
        }

        repository.save(service);

        return "redirect:/dashboard";
    }
}
