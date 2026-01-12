package com.example.Women.Safety.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MlRiskRequest {

    private Double latitude;
    private Double longitude;
    private Integer hour;

    @JsonProperty("crime_density")
    private Double crimeDensity;

    @JsonProperty("poi_count")
    private Integer poiCount;

    @JsonProperty("isNight")
    private Boolean isNight;

    @JsonProperty("isIsolated")
    private Boolean isIsolated;


    // -------- GETTERS & SETTERS --------

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

    public Boolean getIsNight() {
        return isNight;
    }

    public void setIsNight(Boolean isNight) {
        this.isNight = isNight;
    }

    public Boolean getIsIsolated() {
        return isIsolated;
    }

    public void setIsIsolated(Boolean isIsolated) {
        this.isIsolated = isIsolated;
    }
}
