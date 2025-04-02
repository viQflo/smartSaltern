package com.okjk.smartSaltern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.okjk.smartSaltern.dto.TbEnvDto;
import com.okjk.smartSaltern.entity.TbEnv;

@Repository
public interface TbEnvRepository extends JpaRepository<TbEnv, Long> {
	
	List<TbEnv> findTop20BySensorIdOrderByMeasureDatetimeDesc(Long sensorId);
	
	List<TbEnv> findBySensorIdOrderByMeasureDatetimeAsc(Long sensorId);

	
	 @Query("SELECT new com.okjk.smartSaltern.dto.TbEnvDto(e.sensorType, e.sensorVal, e.measureDatetime) " +
	           "FROM TbEnv e ORDER BY e.measureDatetime DESC")
	    List<TbEnvDto> findAllForChart();
}

