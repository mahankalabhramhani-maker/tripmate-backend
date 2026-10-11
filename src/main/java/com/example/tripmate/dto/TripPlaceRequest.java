package com.example.tripmate.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TripPlaceRequest {

    @NotNull(message = "Place ID is required")
    private Long placeId;

    @NotNull(message = "Visit day is required")
    @Min(value = 1, message = "Visit day must be at least 1")
    private Integer visitDay;

    private String notes;

    public TripPlaceRequest() {
    }

    public TripPlaceRequest(Long placeId, Integer visitDay, String notes) {
        this.placeId = placeId;
        this.visitDay = visitDay;
        this.notes = notes;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
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
