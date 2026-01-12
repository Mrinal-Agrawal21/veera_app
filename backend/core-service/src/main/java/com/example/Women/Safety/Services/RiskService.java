package com.example.Women.Safety.Services;

import com.example.Women.Safety.Dto.FrontendRiskRequest;
import com.example.Women.Safety.Dto.MlRiskRequest;
import com.example.Women.Safety.Dto.MlRiskResponse;
import com.example.Women.Safety.Entity.Incident;
import com.example.Women.Safety.Entity.RiskLevel;
import com.example.Women.Safety.Repo.IncidentRepo;
import org.springframework.stereotype.Service;

@Service
public class RiskService {

    private final MlClientService mlClientService;
    private final IncidentRepo repository;

    public RiskService(MlClientService mlClientService,
                       IncidentRepo repository) {
        this.mlClientService = mlClientService;
        this.repository = repository;
    }

    public MlRiskResponse processRisk(FrontendRiskRequest req) {

        // ---------------------------
        // 1. MAP FRONTEND → ML DTO
        // ---------------------------
        MlRiskRequest mlReq = new MlRiskRequest();
        mlReq.setLatitude(req.getLatitude());
        mlReq.setLongitude(req.getLongitude());
        mlReq.setHour(req.getHour());
        mlReq.setCrimeDensity(req.getCrimeDensity());
        mlReq.setPoiCount(req.getPoiCount());
        mlReq.setIsNight(req.getNight());
        mlReq.setIsIsolated(req.getIsolated());

        // ---------------------------
        // 2. CALL ML SERVICE
        // ---------------------------
        MlRiskResponse mlRes = mlClientService.callMlModel(mlReq);

        // ---------------------------
        // 3. SAVE INCIDENT TO DB
        // ---------------------------
        Incident incident = new Incident();
        incident.setUserId(req.getUserId());
        incident.setUsername(req.getUsername());
        incident.setLatitude(req.getLatitude());
        incident.setLongitude(req.getLongitude());
        incident.setHour(req.getHour());
        incident.setCrimeDensity(req.getCrimeDensity());
        incident.setPoiCount(req.getPoiCount());
        incident.setNight(req.getNight());
        incident.setIsolated(req.getIsolated());

        // ---------------------------
        // 4. SAFE ML RESULT MAPPING
        // ---------------------------
        Integer riskScore = mlRes.getRiskScore();
        String riskLevelStr = mlRes.getRiskLevel();

        incident.setRiskScore(riskScore != null ? riskScore : 0);

        if (riskLevelStr != null) {
            incident.setRiskLevel(RiskLevel.valueOf(riskLevelStr.toUpperCase()));
        } else {
            incident.setRiskLevel(RiskLevel.LOW);
        }

        repository.save(incident);

        // ---------------------------
        // 5. RETURN ML RESPONSE
        // ---------------------------
        return mlRes;
    }
}
