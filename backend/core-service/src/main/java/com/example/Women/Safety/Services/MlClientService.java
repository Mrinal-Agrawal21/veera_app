package com.example.Women.Safety.Services;

import com.example.Women.Safety.Dto.MlRiskRequest;
import com.example.Women.Safety.Dto.MlRiskResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class MlClientService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ml.service.url}")
    private String mlServiceUrl;

    private String predictUrl;

    // -------------------------------
    // INIT (URL + warm-up)
    // -------------------------------
    @PostConstruct
    public void init() {
        if (mlServiceUrl == null || mlServiceUrl.isBlank()) {
            throw new RuntimeException("ml.service.url is not set");
        }

        // 🔒 Trim avoids hidden newline bugs
        this.predictUrl = mlServiceUrl.trim() + "/predict";

        System.out.println("✅ ML SERVICE URL = [" + predictUrl + "]");

        // 🔥 WARM-UP CALL (prevents cold start failure)
        try {
            restTemplate.getForObject(mlServiceUrl.trim() + "/docs", String.class);
            System.out.println("🔥 ML warm-up successful");
        } catch (Exception e) {
            System.out.println("⚠️ ML warm-up failed (will retry on first request)");
        }
    }

    // -------------------------------
    // ML CALL WITH RETRY
    // -------------------------------
    public MlRiskResponse callMlModel(MlRiskRequest request) {

        System.out.println("ML REQUEST = " + request);

        try {
            return callOnce(request);

        } catch (HttpServerErrorException ex) {

            // 🚨 Render cold-start = 5xx (502 / 503)
            if (ex.getStatusCode().is5xxServerError()) {
                System.out.println("⚠️ ML cold start detected, retrying in 2 seconds...");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}

                return callOnce(request);
            }

            throw ex;

        } catch (Exception ex) {
            throw new RuntimeException("Error calling ML service", ex);
        }
    }

    // -------------------------------
    // SINGLE ATTEMPT
    // -------------------------------
    private MlRiskResponse callOnce(MlRiskRequest request) {

        MlRiskResponse response = restTemplate.postForObject(
                predictUrl,
                request,
                MlRiskResponse.class
        );

        if (response == null) {
            throw new RuntimeException("ML service returned null response");
        }

        System.out.println("ML RESPONSE SCORE = " + response.getRiskScore());
        System.out.println("ML RESPONSE LEVEL = " + response.getRiskLevel());

        return response;
    }
}
