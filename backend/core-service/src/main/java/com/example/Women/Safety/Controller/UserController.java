package com.example.Women.Safety.Controller;

import com.example.Women.Safety.Entity.Incident;
import com.example.Women.Safety.Services.IncidentService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final IncidentService incidentService;

    public UserController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    /**
     * Get all unique users from incidents
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<Incident> incidents = incidentService.getAllIncidents();
        
        // Extract unique users
        Set<UserDTO> uniqueUsers = incidents.stream()
                .map(incident -> new UserDTO(
                        incident.getUserId(),
                        incident.getUsername(),
                        incident.getLatitude(),
                        incident.getLongitude(),
                        incident.getRiskScore()
                ))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(uniqueUsers);
    }

    /**
     * Get incidents for a specific user
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserIncidents(@PathVariable String userId) {
        List<Incident> incidents = incidentService.getByUserId(userId);
        return ResponseEntity.ok(incidents);
    }

    /**
     * DTO for User response
     */
    public static class UserDTO {
        private String userId;
        private String username;
        private double latitude;
        private double longitude;
        private Integer riskScore;

        public UserDTO(String userId, String username, double latitude, double longitude, Integer riskScore) {
            this.userId = userId;
            this.username = username;
            this.latitude = latitude;
            this.longitude = longitude;
            this.riskScore = riskScore;
        }

        // Getters
        public String getUserId() { return userId; }
        public String getUsername() { return username; }
        public double getLatitude() { return latitude; }
        public double getLongitude() { return longitude; }
        public Integer getRiskScore() { return riskScore; }

        @Override
        public int hashCode() {
            return userId != null ? userId.hashCode() : 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            UserDTO other = (UserDTO) obj;
            return userId != null && userId.equals(other.userId);
        }
    }
}
