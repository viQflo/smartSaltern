package com.okjk.smartSaltern.controller;

import com.okjk.smartSaltern.dto.TbEnvDto;
import com.okjk.smartSaltern.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DashboardApiController {

    private final DashboardService dashboardService;
    
    public DashboardApiController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/api/dashboard-data")
    public List<TbEnvDto> getEnvData() {
        return dashboardService.getChartData();
    }
}
