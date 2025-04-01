package com.okjk.smartSaltern.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TbEnvDto {
    private BigDecimal sensorType;
    private BigDecimal sensorVal;
    private LocalDateTime measureDatetime;

    public TbEnvDto() {
    }

    public TbEnvDto(BigDecimal sensorType, BigDecimal sensorVal, LocalDateTime measureDatetime) {
        this.sensorType = sensorType;
        this.sensorVal = sensorVal;
        this.measureDatetime = measureDatetime;
    }

    public BigDecimal getSensorType() {
        return sensorType;
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

    public LocalDateTime getMeasureDatetime() {
        return measureDatetime;
    }

    public void setMeasureDatetime(LocalDateTime measureDatetime) {
        this.measureDatetime = measureDatetime;
    }
}
