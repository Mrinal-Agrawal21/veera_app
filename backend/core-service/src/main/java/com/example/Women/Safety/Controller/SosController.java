package com.example.Women.Safety.Controller;

import com.example.Women.Safety.Dto.FrontendRiskRequest;
import com.example.Women.Safety.Dto.MlRiskResponse;
import com.example.Women.Safety.Dto.SosResponse;
import com.example.Women.Safety.Entity.Incident;
import com.example.Women.Safety.Services.IncidentService;
import com.example.Women.Safety.Services.RiskService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SosController {

    private final RiskService riskService;
    private final IncidentService incidentService;

    public SosController(RiskService riskService, IncidentService incidentService) {
        this.riskService = riskService;
        this.incidentService = incidentService; 
    }

    @PostMapping("/sos")
    public ResponseEntity<SosResponse> sos(@RequestBody FrontendRiskRequest req) {

        MlRiskResponse mlRes = riskService.processRisk(req);

        SosResponse response = new SosResponse();
        response.setRiskScore(mlRes.getRiskScore());
        response.setRiskLevel(mlRes.getRiskLevel());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sos")
    public ResponseEntity<List<Incident>> getAllIncidents() {
        List<Incident> incidents = incidentService.getAllIncidents();
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllSosByUserId(@PathVariable String userId) {
        List<Incident> incidents = incidentService.getByUserId(userId);
        return ResponseEntity.ok(incidents);
    }
}
