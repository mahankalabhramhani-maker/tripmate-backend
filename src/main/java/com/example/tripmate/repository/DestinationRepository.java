package com.example.tripmate.repository;

import com.example.tripmate.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByNameContainingIgnoreCase(String name);
    Optional<Destination> findByNameIgnoreCase(String name);
}
