package com.example.tripmate.service;

import com.example.tripmate.entity.Destination;
import com.example.tripmate.entity.Place;
import com.example.tripmate.exception.ResourceNotFoundException;
import com.example.tripmate.repository.DestinationRepository;
import com.example.tripmate.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final DestinationRepository destinationRepository;

    public PlaceService(PlaceRepository placeRepository, DestinationRepository destinationRepository) {
        this.placeRepository = placeRepository;
        this.destinationRepository = destinationRepository;
    }

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public Place getPlaceById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found with id: " + id));
    }

    public List<Place> getPlacesByDestination(Long destinationId) {
        return placeRepository.findByDestinationId(destinationId);
    }

    public List<Place> getPlacesByCategory(String category) {
        return placeRepository.findByCategoryIgnoreCase(category);
    }

    public List<Place> getPlacesByDestinationAndCategory(Long destinationId, String category) {
        return placeRepository.findByDestinationIdAndCategoryIgnoreCase(destinationId, category);
    }

    @Transactional
    public Place createPlace(Place place, Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + destinationId));
        place.setDestination(destination);
        return placeRepository.save(place);
    }

    @Transactional
    public Place updatePlace(Long id, Place updated, Long destinationId) {
        Place place = getPlaceById(id);
        place.setName(updated.getName());
        place.setDescription(updated.getDescription());
        place.setCategory(updated.getCategory());
        place.setLocation(updated.getLocation());
        place.setEstimatedCost(updated.getEstimatedCost());
        place.setImageUrl(updated.getImageUrl());

        if (destinationId != null) {
            Destination destination = destinationRepository.findById(destinationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + destinationId));
            place.setDestination(destination);
        }

        return placeRepository.save(place);
    }

    @Transactional
    public void deletePlace(Long id) {
        Place place = getPlaceById(id);
        placeRepository.delete(place);
    }
}
