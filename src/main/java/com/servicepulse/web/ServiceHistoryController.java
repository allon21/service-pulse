package com.servicepulse.web;

import com.servicepulse.service.MonitoringService;
import com.servicepulse.web.dto.ServiceHistoryDTO;
import com.servicepulse.web.dto.ServiceHistoryResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ServiceHistoryController {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 50;
    private static final int CHART_POINTS_LIMIT = 50;

    private final MonitoringService monitoringService;

    public ServiceHistoryController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/services/{id}")
    public String serviceHistory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            Model model
    ) {
        ServiceHistoryResponse response =
                monitoringService.getServiceHistory(id, page, size);


        model.addAttribute("service", response.getService());
        model.addAttribute("history", response.getHistory());
        model.addAttribute("total", response.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute(
                "chartData",
                prepareChartData(response.getHistory())
        );

        return "service-history";
    }

    private String prepareChartData(List<ServiceHistoryDTO> history) {

        if (history.isEmpty()) {
            return "[]";
        }

        int fromIndex = Math.max(0, history.size() - CHART_POINTS_LIMIT);
        List<ServiceHistoryDTO> lastPoints =
                history.subList(fromIndex, history.size());

        StringBuilder json = new StringBuilder("[");
        for (ServiceHistoryDTO item : lastPoints) {
            json.append(String.format(
                    "{ \"time\": \"%s\", \"latency\": %s },",
                    item.getFormattedTime(),
                    item.getLatencyMs()
            ));
        }

        json.deleteCharAt(json.length() - 1);
        json.append("]");

        return json.toString();
    }
}
