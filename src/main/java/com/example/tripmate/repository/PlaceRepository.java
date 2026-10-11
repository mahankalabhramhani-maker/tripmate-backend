package com.example.tripmate.repository;

import com.example.tripmate.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByDestinationId(Long destinationId);
    List<Place> findByCategoryIgnoreCase(String category);
    List<Place> findByDestinationIdAndCategoryIgnoreCase(Long destinationId, String category);
    List<Place> findByDestinationIdAndCategoryIn(Long destinationId, List<String> categories);
}
