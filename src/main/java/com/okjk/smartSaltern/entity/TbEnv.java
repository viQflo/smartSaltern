package com.okjk.smartSaltern.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ENV")
public class TbEnv {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENV_ID")
    private Long id;

    @Column(name = "SENSOR_ID", nullable = false)
    private Long sensorId;

    @Column(name = "MEASURE_DATETIME", nullable = false)
    private LocalDateTime measureDatetime;

    @Column(name = "SENSOR_TYPE", nullable = false)
    private BigDecimal sensorType;

    @Column(name = "SENSOR_VAL", nullable = false)
    private BigDecimal sensorVal;

    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "SP_IDX", nullable = false)
    private Long saltPondId;
    
    

	public TbEnv(Long id, Long sensorId, LocalDateTime measureDatetime, BigDecimal sensorType, BigDecimal sensorVal,
			LocalDateTime createDate, Long saltPondId) {
		super();
		this.id = id;
		this.sensorId = sensorId;
		this.measureDatetime = measureDatetime;
		this.sensorType = sensorType;
		this.sensorVal = sensorVal;
		this.createDate = createDate;
		this.saltPondId = saltPondId;
	}
	
	

	public TbEnv() {
		
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSensorId() {
		return sensorId;
	}

	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
	}

	public LocalDateTime getMeasureDatetime() {
		return measureDatetime;
	}

	public void setMeasureDatetime(LocalDateTime measureDatetime) {
		this.measureDatetime = measureDatetime;
	}

	private BigDecimal getSensorType(String category) {
	    return switch (category) {
	        case "TMP" -> BigDecimal.valueOf(1); // 기온
	        case "REH" -> BigDecimal.valueOf(2); // 습도
	        case "WSD" -> BigDecimal.valueOf(3); // 풍속
	        case "PCP" -> BigDecimal.valueOf(4); // 강수
	        case "SUN" -> BigDecimal.valueOf(5); // 일조/일사
	        default -> BigDecimal.ZERO;
	    };
	}

	public void setSensorType(BigDecimal sensorType) {
		this.sensorType = sensorType;
	}

	public BigDecimal getSensorVal() {
		return sensorVal;
	}

	public void setSensorVal(BigDecimal sensorVal) {
		this.sensorVal = sensorVal;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public Long getSaltPondId() {
		return saltPondId;
	}

	public void setSaltPondId(Long saltPondId) {
		this.saltPondId = saltPondId;
	}

    // Constructors, Getters, Setters
    
}