package com.example.tripmate.service;

import com.example.tripmate.dto.BudgetBreakdown;
import com.example.tripmate.entity.Destination;
import com.example.tripmate.entity.Place;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    public BudgetBreakdown calculateBudget(Double userBudget, Integer numberOfDays, Integer numberOfTravelers,
                                           Destination destination, List<Place> selectedPlaces) {
        if (numberOfDays == null || numberOfDays < 1) {
            numberOfDays = 1;
        }
        if (numberOfTravelers == null || numberOfTravelers < 1) {
            numberOfTravelers = 1;
        }
        if (userBudget == null) {
            userBudget = 0.0;
        }

        // Base daily rates depending on destination or average defaults
        double baseDestinationDaily = (destination != null && destination.getAverageBudget() != null)
                ? (destination.getAverageBudget() / 3.0)
                : 2500.0;

        // 1. Accommodation: approx 35% of daily travel cost per day * rooms needed
        int roomsNeeded = (int) Math.ceil(numberOfTravelers / 2.0);
        double dailyAccommodation = Math.max(1200.0, baseDestinationDaily * 0.38) * roomsNeeded;
        double totalAccommodation = Math.round(dailyAccommodation * numberOfDays);

        // 2. Food: approx ₹600 - ₹900 per traveler per day
        double dailyFoodPerPerson = 700.0;
        double totalFood = Math.round(dailyFoodPerPerson * numberOfTravelers * numberOfDays);

        // 3. Local Transportation: approx ₹500 - ₹800 per group per day
        double dailyTransport = 600.0 + (numberOfTravelers > 2 ? (numberOfTravelers - 2) * 150.0 : 0.0);
        double totalTransportation = Math.round(dailyTransport * numberOfDays);

        // 4. Tourist Places Entry Tickets
        double totalTouristPlaces = 0.0;
        if (selectedPlaces != null && !selectedPlaces.isEmpty()) {
            for (Place p : selectedPlaces) {
                if (p.getEstimatedCost() != null) {
                    totalTouristPlaces += (p.getEstimatedCost() * numberOfTravelers);
                }
            }
        } else {
            // Default tourist places cost estimate if no specific places selected yet
            totalTouristPlaces = Math.round(350.0 * numberOfTravelers * numberOfDays);
        }

        // 5. Activities (water sports, sightseeing guides, safari, etc.)
        double totalActivities = Math.round(500.0 * numberOfTravelers * Math.max(1, numberOfDays - 1));

        // 6. Other / Miscellaneous expenses (souvenirs, snacks, emergencies)
        double totalOther = Math.round(250.0 * numberOfTravelers * numberOfDays);

        double totalEstimatedCost = totalAccommodation + totalFood + totalTransportation + totalActivities + totalTouristPlaces + totalOther;
        double remainingBudget = userBudget - totalEstimatedCost;
        boolean isWithinBudget = remainingBudget >= 0;

        BudgetBreakdown breakdown = new BudgetBreakdown();
        breakdown.setTotalBudget(userBudget);
        breakdown.setAccommodationCost(totalAccommodation);
        breakdown.setFoodCost(totalFood);
        breakdown.setTransportationCost(totalTransportation);
        breakdown.setActivitiesCost(totalActivities);
        breakdown.setTouristPlacesCost(totalTouristPlaces);
        breakdown.setOtherExpenses(totalOther);
        breakdown.setTotalEstimatedCost(totalEstimatedCost);
        breakdown.setRemainingBudget(remainingBudget);
        breakdown.setIsWithinBudget(isWithinBudget);
        breakdown.setStatusMessage(isWithinBudget
                ? "WITHIN BUDGET (₹" + String.format("%.0f", remainingBudget) + " surplus)"
                : "OVER BUDGET (Exceeds by ₹" + String.format("%.0f", Math.abs(remainingBudget)) + ")");

        return breakdown;
    }
}
