package com.example.tripmate.service;

import com.example.tripmate.entity.Destination;
import com.example.tripmate.exception.ResourceNotFoundException;
import com.example.tripmate.repository.DestinationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + id));
    }

    public List<Destination> searchDestinations(String name) {
        if (name == null || name.trim().isEmpty()) {
            return destinationRepository.findAll();
        }
        return destinationRepository.findByNameContainingIgnoreCase(name.trim());
    }

    @Transactional
    public Destination createDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    @Transactional
    public Destination updateDestination(Long id, Destination updated) {
        Destination destination = getDestinationById(id);
        destination.setName(updated.getName());
        destination.setCountry(updated.getCountry());
        destination.setDescription(updated.getDescription());
        destination.setAverageBudget(updated.getAverageBudget());
        destination.setImageUrl(updated.getImageUrl());
        return destinationRepository.save(destination);
    }

    @Transactional
    public void deleteDestination(Long id) {
        Destination destination = getDestinationById(id);
        destinationRepository.delete(destination);
    }
}
