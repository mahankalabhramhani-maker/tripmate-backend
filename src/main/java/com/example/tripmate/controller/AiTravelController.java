package com.example.tripmate.controller;

import com.example.tripmate.dto.AiTravelRequest;
import com.example.tripmate.dto.AiTravelResponse;
import com.example.tripmate.service.AiTravelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiTravelController {

    private final AiTravelService aiTravelService;

    public AiTravelController(AiTravelService aiTravelService) {
        this.aiTravelService = aiTravelService;
    }

    @PostMapping("/travel-plan")
    public ResponseEntity<AiTravelResponse> generateTravelPlan(@Valid @RequestBody AiTravelRequest request) {
        AiTravelResponse response = aiTravelService.generateTravelPlan(request);
        return ResponseEntity.ok(response);
    }
}
