package com.example.tripmate.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class AiTravelRequest {

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Number of days is required")
    @Min(value = 1, message = "Number of days must be at least 1")
    private Integer numberOfDays;

    @NotNull(message = "Budget is required")
    @Min(value = 1, message = "Budget must be greater than 0")
    private Double budget;

    @NotNull(message = "Number of travelers is required")
    @Min(value = 1, message = "Number of travelers must be at least 1")
    private Integer numberOfTravelers;

    private List<String> interests;

    private String travelStyle; // e.g. "Budget", "Relaxed", "Adventure", "Luxury"

    public AiTravelRequest() {
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
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

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public String getTravelStyle() {
        return travelStyle;
    }

    public void setTravelStyle(String travelStyle) {
        this.travelStyle = travelStyle;
    }
}
