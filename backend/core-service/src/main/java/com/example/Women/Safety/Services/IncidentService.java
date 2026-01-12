package com.example.Women.Safety.Services;

import com.example.Women.Safety.Entity.Incident;
import com.example.Women.Safety.Repo.IncidentRepo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class IncidentService {

    private final IncidentRepo repository;

    public IncidentService(IncidentRepo repository) {
        this.repository = repository;
    }

    public Incident createIncident(Incident incident) {
        return repository.save(incident);
    }

    public List<Incident> getByUserId(String userId) {
        return repository.findByUserId(userId);
    }
}
