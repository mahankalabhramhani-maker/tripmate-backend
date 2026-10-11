package com.example.tripmate.controller;

import com.example.tripmate.dto.TripPlaceRequest;
import com.example.tripmate.dto.TripRequest;
import com.example.tripmate.dto.TripResponse;
import com.example.tripmate.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(@Valid @RequestBody TripRequest request,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        TripResponse response = tripService.createTrip(request, userDetails.getUsername());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getUserTrips(@AuthenticationPrincipal UserDetails userDetails) {
        List<TripResponse> trips = tripService.getUserTrips(userDetails.getUsername());
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponse> getTripById(@PathVariable Long id,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        TripResponse trip = tripService.getTripById(id, userDetails.getUsername());
        return ResponseEntity.ok(trip);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponse> updateTrip(@PathVariable Long id,
                                                   @RequestBody TripRequest request,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        TripResponse updated = tripService.updateTrip(id, request, userDetails.getUsername());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTrip(@PathVariable Long id,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        tripService.deleteTrip(id, userDetails.getUsername());
        return ResponseEntity.ok(Map.of("message", "Trip deleted successfully"));
    }

    @PostMapping("/{id}/places")
    public ResponseEntity<TripResponse> addPlaceToTrip(@PathVariable Long id,
                                                       @Valid @RequestBody TripPlaceRequest placeRequest,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        TripResponse updated = tripService.addPlaceToTrip(id, placeRequest, userDetails.getUsername());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}/places/{tripPlaceId}")
    public ResponseEntity<TripResponse> removePlaceFromTrip(@PathVariable Long id,
                                                            @PathVariable Long tripPlaceId,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        TripResponse updated = tripService.removePlaceFromTrip(id, tripPlaceId, userDetails.getUsername());
        return ResponseEntity.ok(updated);
    }
}
