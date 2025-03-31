package com.okjk.smartSaltern.dto;

public class WeatherItem {
    private String category;   // T1H, REH, etc
    private String obsrValue;  // 값
	
    public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getObsrValue() {
		return obsrValue;
	}
	public void setObsrValue(String obsrValue) {
		this.obsrValue = obsrValue;
	}

    // Getters and Setters
    
}
