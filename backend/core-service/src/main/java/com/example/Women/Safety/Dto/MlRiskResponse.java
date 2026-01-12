package com.example.Women.Safety.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MlRiskResponse {

    @JsonProperty("risk_score")
    private Integer riskScore;

    @JsonProperty("risk_level")
    private String riskLevel;

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
