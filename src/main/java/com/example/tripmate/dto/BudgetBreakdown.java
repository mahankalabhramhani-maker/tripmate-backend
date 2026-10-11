package com.example.tripmate.dto;

public class BudgetBreakdown {

    private Double totalBudget;
    private Double accommodationCost;
    private Double foodCost;
    private Double transportationCost;
    private Double activitiesCost;
    private Double touristPlacesCost;
    private Double otherExpenses;
    private Double totalEstimatedCost;
    private Double remainingBudget;
    private Boolean isWithinBudget;
    private String statusMessage;

    public BudgetBreakdown() {
    }

    public Double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Double getAccommodationCost() {
        return accommodationCost;
    }

    public void setAccommodationCost(Double accommodationCost) {
        this.accommodationCost = accommodationCost;
    }

    public Double getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(Double foodCost) {
        this.foodCost = foodCost;
    }

    public Double getTransportationCost() {
        return transportationCost;
    }

    public void setTransportationCost(Double transportationCost) {
        this.transportationCost = transportationCost;
    }

    public Double getActivitiesCost() {
        return activitiesCost;
    }

    public void setActivitiesCost(Double activitiesCost) {
        this.activitiesCost = activitiesCost;
    }

    public Double getTouristPlacesCost() {
        return touristPlacesCost;
    }

    public void setTouristPlacesCost(Double touristPlacesCost) {
        this.touristPlacesCost = touristPlacesCost;
    }

    public Double getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(Double otherExpenses) {
        this.otherExpenses = otherExpenses;
    }

    public Double getTotalEstimatedCost() {
        return totalEstimatedCost;
    }

    public void setTotalEstimatedCost(Double totalEstimatedCost) {
        this.totalEstimatedCost = totalEstimatedCost;
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

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
