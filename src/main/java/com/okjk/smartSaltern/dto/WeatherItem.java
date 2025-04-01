package com.okjk.smartSaltern.dto;

public class WeatherItem {
    private String baseDate;
    private String baseTime;
    private String category;
    private String fcstDate;
    private String fcstTime;
    private String fcstValue;
    private int nx;
    private int ny;
	public WeatherItem(String baseDate, String baseTime, String category, String fcstDate, String fcstTime,
			String fcstValue, int nx, int ny) {
		super();
		this.baseDate = baseDate;
		this.baseTime = baseTime;
		this.category = category;
		this.fcstDate = fcstDate;
		this.fcstTime = fcstTime;
		this.fcstValue = fcstValue;
		this.nx = nx;
		this.ny = ny;
	}
	public WeatherItem() {
		
	}
	public String getBaseDate() {
		return baseDate;
	}
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}
	public String getBaseTime() {
		return baseTime;
	}
	public void setBaseTime(String baseTime) {
		this.baseTime = baseTime;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFcstDate() {
		return fcstDate;
	}
	public void setFcstDate(String fcstDate) {
		this.fcstDate = fcstDate;
	}
	public String getFcstTime() {
		return fcstTime;
	}
	public void setFcstTime(String fcstTime) {
		this.fcstTime = fcstTime;
	}
	public String getFcstValue() {
		return fcstValue;
	}
	public void setFcstValue(String fcstValue) {
		this.fcstValue = fcstValue;
	}
	public int getNx() {
		return nx;
	}
	public void setNx(int nx) {
		this.nx = nx;
	}
	public int getNy() {
		return ny;
	}
	public void setNy(int ny) {
		this.ny = ny;
	}
	
	
	
	

    // Getters & Setters
    // (생성기, toString 등 필요 시 추가)
    
}
