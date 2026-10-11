package com.example.tripmate.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @Column(name = "number_of_days", nullable = false)
    private Integer numberOfDays;

    @Column(nullable = false)
    private Double budget;

    @Column(name = "number_of_travelers", nullable = false)
    private Integer numberOfTravelers;

    @Column(name = "travel_date", nullable = false)
    private LocalDate travelDate;

    @Column(columnDefinition = "TEXT")
    private String interests; // comma-separated e.g. "Beach, Food, Adventure"

    @Column(name = "estimated_cost", nullable = false)
    private Double estimatedCost;

    @Column(nullable = false, length = 30)
    private String status; // PLANNED, ONGOING, COMPLETED

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TripPlace> tripPlaces = new ArrayList<>();

    public Trip() {
    }

    public Trip(User user, Destination destination, Integer numberOfDays, Double budget, Integer numberOfTravelers,
                LocalDate travelDate, String interests, Double estimatedCost, String status) {
        this.user = user;
        this.destination = destination;
        this.numberOfDays = numberOfDays;
        this.budget = budget;
        this.numberOfTravelers = numberOfTravelers;
        this.travelDate = travelDate;
        this.interests = interests;
        this.estimatedCost = estimatedCost;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = "PLANNED";
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Integer getNumberOfTravelers() {
        return numberOfTravelers;
    }

    public void setNumberOfTravelers(Integer numberOfTravelers) {
        this.numberOfTravelers = numberOfTravelers;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<TripPlace> getTripPlaces() {
        return tripPlaces;
    }

    public void setTripPlaces(List<TripPlace> tripPlaces) {
        this.tripPlaces = tripPlaces;
    }
}
