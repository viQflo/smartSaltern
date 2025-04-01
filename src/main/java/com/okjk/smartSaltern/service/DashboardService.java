package com.okjk.smartSaltern.service;

import com.okjk.smartSaltern.dto.TbEnvDto;
import com.okjk.smartSaltern.repository.TbEnvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TbEnvRepository tbEnvRepository;
    
    public DashboardService(TbEnvRepository tbEnvRepository) {
        this.tbEnvRepository = tbEnvRepository;
    }

    public List<TbEnvDto> getChartData() {
        return tbEnvRepository.findAllForChart();
    }
}
