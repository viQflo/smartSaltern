package com.okjk.smartSaltern.dto;

import java.util.Arrays;

public enum SensorType {
    T1H(1.00),
    REH(2.00),
    WSD(3.00),
    RN1(4.00),
    SUN(5.00);

    private final double code;

    SensorType(double code) {
        this.code = code;
    }

    public double getCode() {
        return code;
    }

    public static double fromCategory(String category) {
        return Arrays.stream(values())
                .filter(v -> v.name().equals(category))
                .findFirst()
                .map(SensorType::getCode)
                .orElseThrow(() -> new IllegalArgumentException("Unknown category: " + category));
    }
}
