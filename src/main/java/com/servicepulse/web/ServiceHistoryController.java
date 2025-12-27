package com.servicepulse.web;

import com.servicepulse.service.MonitoringService;
import com.servicepulse.web.dto.ServiceHistoryDTO;
import com.servicepulse.web.dto.ServiceHistoryResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ServiceHistoryController {

    private final MonitoringService monitoringService;

    public ServiceHistoryController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/services/{name}")
    public String serviceHistory(@PathVariable String name, Model model) {
        try {
            ServiceHistoryResponse response = monitoringService.getServiceHistory(name);
            model.addAttribute("serviceHistory", response);
            model.addAttribute("chartData", prepareChartData(response.getHistory()));
            return "service-history";
        } catch (IllegalArgumentException e) {
            return "redirect:/dashboard?error=Service+not+found";
        }
    }

    @GetMapping("/services/{name}/last-{hours}h")
    public String serviceHistoryByHours(
            @PathVariable String name,
            @PathVariable int hours,
            Model model
    ) {
        try {
            ServiceHistoryResponse response = monitoringService.getServiceHistory(name, hours);
            model.addAttribute("serviceHistory", response);
            model.addAttribute("chartData", prepareChartData(response.getHistory()));
            model.addAttribute("hours", hours);
            return "service-history";
        } catch (IllegalArgumentException e) {
            return "redirect:/dashboard?error=Service+not+found";
        }
    }

    private String prepareChartData(List<ServiceHistoryDTO> history) {
        if (history == null || history.isEmpty()) {
            return "[]";
        }

        StringBuilder chartData = new StringBuilder("[");

        // Ограничим для производительности
        int limit = Math.min(history.size(), 50);
        for (int i = 0; i < limit; i++) {
            ServiceHistoryDTO item = history.get(i);
            chartData.append(String.format(
                    "{time: '%s', latency: %d, status: '%s'},",
                    item.getFormattedTime(),
                    item.getLatencyMs(),
                    item.getStatus()
            ));
        }

        if (chartData.length() > 1) {
            chartData.deleteCharAt(chartData.length() - 1);
        }
        chartData.append("]");

        return chartData.toString();
    }
}