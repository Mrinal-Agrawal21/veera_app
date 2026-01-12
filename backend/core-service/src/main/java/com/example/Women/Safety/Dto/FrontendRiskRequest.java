package com.example.Women.Safety.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FrontendRiskRequest {

    private String userId;
    private String username;

    private Double latitude;
    private Double longitude;
    private Integer hour;

    @JsonProperty("crime_density")
    private Double crimeDensity;

    @JsonProperty("poi_count")
    private Integer poiCount;

    // 🔥 FIX: explicitly map frontend boolean fields
    @JsonProperty("isNight")
    private Boolean night;

    @JsonProperty("isIsolated")
    private Boolean isolated;

    // -------- GETTERS & SETTERS --------

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Double getCrimeDensity() {
        return crimeDensity;
    }

    public void setCrimeDensity(Double crimeDensity) {
        this.crimeDensity = crimeDensity;
    }

    public Integer getPoiCount() {
        return poiCount;
    }

    public void setPoiCount(Integer poiCount) {
        this.poiCount = poiCount;
    }

    public Boolean getNight() {
        return night;
    }

    public void setNight(Boolean night) {
        this.night = night;
    }

    public Boolean getIsolated() {
        return isolated;
    }

    public void setIsolated(Boolean isolated) {
        this.isolated = isolated;
    }
}
