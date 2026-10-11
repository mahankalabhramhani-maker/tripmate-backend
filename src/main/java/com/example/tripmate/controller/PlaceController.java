package com.example.tripmate.controller;

import com.example.tripmate.entity.Place;
import com.example.tripmate.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<List<Place>> getAllPlaces() {
        return ResponseEntity.ok(placeService.getAllPlaces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable Long id) {
        return ResponseEntity.ok(placeService.getPlaceById(id));
    }

    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<List<Place>> getPlacesByDestination(@PathVariable Long destinationId) {
        return ResponseEntity.ok(placeService.getPlacesByDestination(destinationId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Place>> getPlacesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(placeService.getPlacesByCategory(category));
    }

    @PostMapping
    public ResponseEntity<Place> createPlace(@RequestBody Place place, @RequestParam Long destinationId) {
        Place created = placeService.createPlace(place, destinationId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Place> updatePlace(@PathVariable Long id,
                                            @RequestBody Place place,
                                            @RequestParam(required = false) Long destinationId) {
        Place updated = placeService.updatePlace(id, place, destinationId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePlace(@PathVariable Long id) {
        placeService.deletePlace(id);
        return ResponseEntity.ok(Map.of("message", "Place deleted successfully"));
    }
}
