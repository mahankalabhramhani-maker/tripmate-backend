package com.example.tripmate.dto;

import com.example.tripmate.entity.Destination;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TripResponse {

    private Long id;
    private Destination destination;
    private Integer numberOfDays;
    private Double budget;
    private Integer numberOfTravelers;
    private LocalDate travelDate;
    private String interests;
    private Double estimatedCost;
    private Double remainingBudget;
    private Boolean isWithinBudget;
    private String status;
    private LocalDateTime createdAt;
    private List<TripPlaceItem> places;
    private BudgetBreakdown budgetBreakdown;

    public static class TripPlaceItem {
        private Long id;
        private Long placeId;
        private String placeName;
        private String category;
        private String location;
        private Double estimatedCost;
        private String imageUrl;
        private Integer visitDay;
        private String notes;

        public TripPlaceItem() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getPlaceId() {
            return placeId;
        }

        public void setPlaceId(Long placeId) {
            this.placeId = placeId;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Double getEstimatedCost() {
            return estimatedCost;
        }

        public void setEstimatedCost(Double estimatedCost) {
            this.estimatedCost = estimatedCost;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Integer getVisitDay() {
            return visitDay;
        }

        public void setVisitDay(Integer visitDay) {
            this.visitDay = visitDay;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }

    public TripResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(Double remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public Boolean getIsWithinBudget() {
        return isWithinBudget;
    }

    public void setIsWithinBudget(Boolean withinBudget) {
        isWithinBudget = withinBudget;
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

    public List<TripPlaceItem> getPlaces() {
        return places;
    }

    public void setPlaces(List<TripPlaceItem> places) {
        this.places = places;
    }

    public BudgetBreakdown getBudgetBreakdown() {
        return budgetBreakdown;
    }

    public void setBudgetBreakdown(BudgetBreakdown budgetBreakdown) {
        this.budgetBreakdown = budgetBreakdown;
    }
}
