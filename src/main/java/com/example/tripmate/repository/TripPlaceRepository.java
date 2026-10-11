package com.example.tripmate.repository;

import com.example.tripmate.entity.TripPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripPlaceRepository extends JpaRepository<TripPlace, Long> {
    List<TripPlace> findByTripIdOrderByVisitDayAsc(Long tripId);
    void deleteByTripId(Long tripId);
}
