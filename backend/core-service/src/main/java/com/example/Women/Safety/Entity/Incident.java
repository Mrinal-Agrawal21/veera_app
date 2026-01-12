package com.example.Women.Safety.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "LocDetails")
public class Incident {

    @Id
    private String id;

    private String userId;

    private String username;
    private double latitude;
    private double longitude;
    private int hour;

    private double crimeDensity;
    private int poiCount;

    private boolean night;
    private boolean isolated;

    private Integer riskScore;
    private RiskLevel riskLevel;

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getCrimeDensity() {
        return crimeDensity;
    }

    public void setCrimeDensity(double crimeDensity) {
        this.crimeDensity = crimeDensity;
    }

    public int getPoiCount() {
        return poiCount;
    }

    public void setPoiCount(int poiCount) {
        this.poiCount = poiCount;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public boolean isIsolated() {
        return isolated;
    }

    public void setIsolated(boolean isolated) {
        this.isolated = isolated;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }
}
