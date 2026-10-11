package com.example.tripmate.dto;

import com.example.tripmate.entity.Destination;
import com.example.tripmate.entity.Place;
import java.util.List;
import java.util.Map;

public class RecommendationResponse {

    private Destination destination;
    private List<Place> recommendedPlaces;
    private List<String> recommendedFoods;
    private List<String> recommendedActivities;
    private List<String> accommodationSuggestions;
    private List<String> travelTips;
    private BudgetBreakdown budgetBreakdown;
    private Map<Integer, List<Place>> suggestedDayPlan;

    public RecommendationResponse() {
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public List<Place> getRecommendedPlaces() {
        return recommendedPlaces;
    }

    public void setRecommendedPlaces(List<Place> recommendedPlaces) {
        this.recommendedPlaces = recommendedPlaces;
    }

    public List<String> getRecommendedFoods() {
        return recommendedFoods;
    }

    public void setRecommendedFoods(List<String> recommendedFoods) {
        this.recommendedFoods = recommendedFoods;
    }

    public List<String> getRecommendedActivities() {
        return recommendedActivities;
    }

    public void setRecommendedActivities(List<String> recommendedActivities) {
        this.recommendedActivities = recommendedActivities;
    }

    public List<String> getAccommodationSuggestions() {
        return accommodationSuggestions;
    }

    public void setAccommodationSuggestions(List<String> accommodationSuggestions) {
        this.accommodationSuggestions = accommodationSuggestions;
    }

    public List<String> getTravelTips() {
        return travelTips;
    }

    public void setTravelTips(List<String> travelTips) {
        this.travelTips = travelTips;
    }

    public BudgetBreakdown getBudgetBreakdown() {
        return budgetBreakdown;
    }

    public void setBudgetBreakdown(BudgetBreakdown budgetBreakdown) {
        this.budgetBreakdown = budgetBreakdown;
    }

    public Map<Integer, List<Place>> getSuggestedDayPlan() {
        return suggestedDayPlan;
    }

    public void setSuggestedDayPlan(Map<Integer, List<Place>> suggestedDayPlan) {
        this.suggestedDayPlan = suggestedDayPlan;
    }
}
