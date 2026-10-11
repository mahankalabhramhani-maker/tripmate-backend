package com.example.tripmate.controller;

import com.example.tripmate.dto.RecommendationRequest;
import com.example.tripmate.dto.RecommendationResponse;
import com.example.tripmate.service.RecommendationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "*")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public ResponseEntity<RecommendationResponse> getRecommendations(@Valid @RequestBody RecommendationRequest request) {
        RecommendationResponse response = recommendationService.getRecommendations(request);
        return ResponseEntity.ok(response);
    }
}
