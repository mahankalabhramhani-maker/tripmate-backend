package com.example.tripmate.dto;

import java.util.List;

public class AiTravelResponse {

    private String destination;
    private Integer numberOfDays;
    private Integer numberOfTravelers;
    private Double totalBudget;
    private Double estimatedTotalCost;
    private String summary;
    private List<DayPlan> itinerary;
    private List<String> foodRecommendations;
    private List<String> activityRecommendations;
    private List<String> accommodationSuggestions;
    private List<String> travelTips;
    private BudgetBreakdown estimatedExpenses;
    private String aiModelUsed;

    public static class DayPlan {
        private Integer day;
        private String title;
        private String theme;
        private List<String> morningActivities;
        private List<String> afternoonActivities;
        private List<String> eveningActivities;
        private String recommendedMeals;
        private Double estimatedDayCost;

        public DayPlan() {
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public List<String> getMorningActivities() {
            return morningActivities;
        }

        public void setMorningActivities(List<String> morningActivities) {
            this.morningActivities = morningActivities;
        }

        public List<String> getAfternoonActivities() {
            return afternoonActivities;
        }

        public void setAfternoonActivities(List<String> afternoonActivities) {
            this.afternoonActivities = afternoonActivities;
        }

        public List<String> getEveningActivities() {
            return eveningActivities;
        }

        public void setEveningActivities(List<String> eveningActivities) {
            this.eveningActivities = eveningActivities;
        }

        public String getRecommendedMeals() {
            return recommendedMeals;
        }

        public void setRecommendedMeals(String recommendedMeals) {
            this.recommendedMeals = recommendedMeals;
        }

        public Double getEstimatedDayCost() {
            return estimatedDayCost;
        }

        public void setEstimatedDayCost(Double estimatedDayCost) {
            this.estimatedDayCost = estimatedDayCost;
        }
    }

    public AiTravelResponse() {
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

    public Integer getNumberOfTravelers() {
        return numberOfTravelers;
    }

    public void setNumberOfTravelers(Integer numberOfTravelers) {
        this.numberOfTravelers = numberOfTravelers;
    }

    public Double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Double getEstimatedTotalCost() {
        return estimatedTotalCost;
    }

    public void setEstimatedTotalCost(Double estimatedTotalCost) {
        this.estimatedTotalCost = estimatedTotalCost;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<DayPlan> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<DayPlan> itinerary) {
        this.itinerary = itinerary;
    }

    public List<String> getFoodRecommendations() {
        return foodRecommendations;
    }

    public void setFoodRecommendations(List<String> foodRecommendations) {
        this.foodRecommendations = foodRecommendations;
    }

    public List<String> getActivityRecommendations() {
        return activityRecommendations;
    }

    public void setActivityRecommendations(List<String> activityRecommendations) {
        this.activityRecommendations = activityRecommendations;
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

    public BudgetBreakdown getEstimatedExpenses() {
        return estimatedExpenses;
    }

    public void setEstimatedExpenses(BudgetBreakdown estimatedExpenses) {
        this.estimatedExpenses = estimatedExpenses;
    }

    public String getAiModelUsed() {
        return aiModelUsed;
    }

    public void setAiModelUsed(String aiModelUsed) {
        this.aiModelUsed = aiModelUsed;
    }
}
