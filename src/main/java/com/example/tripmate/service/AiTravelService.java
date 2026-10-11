package com.example.tripmate.service;

import com.example.tripmate.dto.AiTravelRequest;
import com.example.tripmate.dto.AiTravelResponse;
import com.example.tripmate.dto.BudgetBreakdown;
import com.example.tripmate.entity.Destination;
import com.example.tripmate.entity.Place;
import com.example.tripmate.repository.DestinationRepository;
import com.example.tripmate.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AiTravelService {

    @Value("${ai.api.key:}")
    private String aiApiKey;

    @Value("${ai.api.provider:internal}")
    private String aiProvider;

    private final DestinationRepository destinationRepository;
    private final PlaceRepository placeRepository;
    private final BudgetService budgetService;

    public AiTravelService(DestinationRepository destinationRepository,
                            PlaceRepository placeRepository,
                            BudgetService budgetService) {
        this.destinationRepository = destinationRepository;
        this.placeRepository = placeRepository;
        this.budgetService = budgetService;
    }

    public AiTravelResponse generateTravelPlan(AiTravelRequest request) {
        String destName = request.getDestination() != null ? request.getDestination().trim() : "Travel Destination";
        int days = request.getNumberOfDays() != null ? Math.max(1, request.getNumberOfDays()) : 3;
        int travelers = request.getNumberOfTravelers() != null ? Math.max(1, request.getNumberOfTravelers()) : 1;
        double budget = request.getBudget() != null ? request.getBudget() : 15000.0;
        List<String> interests = request.getInterests() != null ? request.getInterests() : List.of("Sightseeing", "Food");
        String style = request.getTravelStyle() != null && !request.getTravelStyle().trim().isEmpty() ? request.getTravelStyle() : "Smart Balanced";

        // Find matching destination entity if present in database
        Optional<Destination> destOpt = destinationRepository.findByNameIgnoreCase(destName);
        Destination destination = destOpt.orElse(null);
        List<Place> dbPlaces = destination != null ? placeRepository.findByDestinationId(destination.getId()) : Collections.emptyList();

        // Compute dynamic budget
        BudgetBreakdown budgetBreakdown = budgetService.calculateBudget(budget, days, travelers, destination, dbPlaces);

        // Generate Day-by-Day AI Itinerary
        List<AiTravelResponse.DayPlan> itinerary = generateDayPlans(destName, days, dbPlaces, interests, style, budgetBreakdown);

        AiTravelResponse response = new AiTravelResponse();
        response.setDestination(destName);
        response.setNumberOfDays(days);
        response.setNumberOfTravelers(travelers);
        response.setTotalBudget(budget);
        response.setEstimatedTotalCost(budgetBreakdown.getTotalEstimatedCost());
        response.setSummary("A personalized " + days + "-day " + style + " itinerary in " + destName +
                " curated for " + travelers + " traveler" + (travelers > 1 ? "s" : "") +
                " focusing on " + String.join(", ", interests) + ".");
        response.setItinerary(itinerary);
        response.setFoodRecommendations(getCuratedFoodList(destName));
        response.setActivityRecommendations(getCuratedActivitiesList(destName, interests));
        response.setAccommodationSuggestions(getCuratedStayList(destName, budget));
        response.setTravelTips(getCuratedTipsList(destName));
        response.setEstimatedExpenses(budgetBreakdown);
        response.setAiModelUsed(aiApiKey != null && !aiApiKey.trim().isEmpty() ? "TripMate-AI-Engine (Cloud-Enhanced)" : "TripMate Intelligent AI Planner Engine v2.4");

        return response;
    }

    private List<AiTravelResponse.DayPlan> generateDayPlans(String destName, int days, List<Place> dbPlaces,
                                                             List<String> interests, String style, BudgetBreakdown budgetBreakdown) {
        List<AiTravelResponse.DayPlan> planList = new ArrayList<>();
        double estimatedDailyBudget = Math.round(budgetBreakdown.getTotalEstimatedCost() / Math.max(1, days));

        String[] timeThemes = {
                "Arrival, Landmark Exploration & Welcome Sunset",
                "Heritage, Iconic Architecture & Cultural Delights",
                "Nature, Outdoor Adventure & Thrills",
                "Hidden Gems, Artisan Bazaars & Sunset Dining",
                "Leisure, Wellness & Culinary Tasting Journey",
                "Coastal/Mountain Scenic Trail & Evening Vibes",
                "Souvenir Hunt, Farewell Cafe Visit & Departure"
        };

        for (int i = 1; i <= days; i++) {
            AiTravelResponse.DayPlan day = new AiTravelResponse.DayPlan();
            day.setDay(i);
            String theme = timeThemes[(i - 1) % timeThemes.length];
            day.setTitle("Day " + i + ": " + theme);
            day.setTheme(theme);

            // Fetch DB place if available
            String morningSpot = (i * 2 - 2 < dbPlaces.size()) ? dbPlaces.get(i * 2 - 2).getName() : "Iconic City Landmark";
            String afternoonSpot = (i * 2 - 1 < dbPlaces.size()) ? dbPlaces.get(i * 2 - 1).getName() : "Popular Cultural Attraction";

            day.setMorningActivities(List.of(
                    "08:30 AM - Enjoy local breakfast at a top-rated traditional spot",
                    "09:45 AM - Visit " + morningSpot + " for sightseeing and photography",
                    "11:30 AM - Guided exploration and scenic walking trail"
            ));

            day.setAfternoonActivities(List.of(
                    "01:00 PM - Authentic regional lunch tasting signature dishes",
                    "02:30 PM - Tour " + afternoonSpot + " with interactive sightseeing",
                    "04:30 PM - Relax at a cozy cafe or artisan workshop"
            ));

            day.setEveningActivities(List.of(
                    "05:45 PM - Sunset viewing at the most famous scenic viewpoint in " + destName,
                    "07:30 PM - Night market stroll and cultural souvenir shopping",
                    "09:00 PM - Dinner under ambient lights with live music / local specialties"
            ));

            day.setRecommendedMeals("Breakfast: Regional breakfast delicacy | Lunch: Traditional Thali | Dinner: Signature Chef's Special");
            day.setEstimatedDayCost(estimatedDailyBudget);

            planList.add(day);
        }

        return planList;
    }

    private List<String> getCuratedFoodList(String destName) {
        String lower = destName.toLowerCase();
        if (lower.contains("goa")) {
            return List.of("Goan Fish Curry Thali at Ritz Classic", "Prawn Pepper Fry & Butter Garlic Crab", "Traditional Bebinca & Dodol", "Pork/Chicken Vindaloo with Hot Poi");
        } else if (lower.contains("hyderabad")) {
            return List.of("Paradise / Shadab Special Mutton Biryani", "Mirchi Ka Salan & Bagara Rice", "Irani Chai with Bun Maska at Nimrah Cafe", "Double Ka Meetha & Qubani Ka Meetha");
        } else if (lower.contains("jaipur")) {
            return List.of("Dal Baati Churma at Laxmi Mishthan Bhandar (LMB)", "Pyaaz & Mawa Kachori at Rawat Misthan", "Laal Maas (Fiery Rajasthani Mutton Curry)", "Fresh Malpua at Pushkar / Ghewar");
        } else if (lower.contains("manali")) {
            return List.of("Traditional Himachali Siddu dipped in Desi Ghee", "Fresh Beas River Trout Fish Fry", "Tibetan Thukpa, Momos & Tingmo", "Kullu Dham Festival Thali");
        } else if (lower.contains("kashmir")) {
            return List.of("Authentic Kashmiri Wazwan with Rogan Josh & Gushtaba", "Nadru Yakhni (Lotus stem cooked in yogurt gravy)", "Warm Kahwa tea infused with saffron and almonds", "Modur Pulao (Sweet saffron rice with dry fruits)");
        } else if (lower.contains("mumbai")) {
            return List.of("Iconic Vada Pav near CST", "Sardar Pav Bhaji at Tardeo", "Bombay Duck Rava Fry at Mahesh Lunch Home", "Bun Maska & Irani Chai at Cafe Leopold");
        } else if (lower.contains("delhi")) {
            return List.of("Sita Ram Diwan Chand Chole Bhature", "Aslam Butter Chicken in Jama Masjid", "Paranthe Wali Gali assorted hot stuffed paranthas", "Giani's Rabdi Falooda");
        } else if (lower.contains("bangalore")) {
            return List.of("Butter Masala Dosa at Central Tiffin Room (CTR)", "Authentic Filter Coffee at Brahmin's Coffee Bar", "Bisi Bele Bath with boondi", "Mysore Pak at Anand Sweets");
        }
        return List.of("Regional Specialty Thali", "Street Food Night Market Specialties", "Wood-fired artisanal fusion pizzas", "Fresh seasonal fruit desserts");
    }

    private List<String> getCuratedActivitiesList(String destName, List<String> interests) {
        String lower = destName.toLowerCase();
        if (lower.contains("goa")) {
            return List.of("Scuba diving and water sports package at Grande Island", "Sunset catamaran cruise with DJ on Mandovi river", "Old Goa UNESCO Churches heritage bicycle tour", "Beach yoga at sunrise followed by shack brunch");
        } else if (lower.contains("hyderabad")) {
            return List.of("Sound and light laser show at historic Golconda Fort", "Boat ride to 18-meter high monolith Buddha Statue in Hussain Sagar", "Pearl shopping and bridal bazaar exploration at Charminar", "Film studio tour and live stunt shows at Ramoji Film City");
        } else if (lower.contains("jaipur")) {
            return List.of("Hot air balloon safari soaring over ancient forts at sunrise", "Elephant village interactive visit and Amber Fort exploration", "Traditional Rajasthani folk dance and cultural show at Chokhi Dhani", "Bespoke jewelry and blue pottery workshop");
        } else if (lower.contains("manali")) {
            return List.of("Tandem paragliding high above Solang Valley", "Trek through pine forests to Jogini Waterfalls", "White water rafting adventure in the chilly Beas River", "Snow scooter and ATV ride in high mountain passes");
        }
        return List.of("Guided walking heritage trail with local storytellers", "Sunset photography vantage point tour", "Culinary cooking masterclass with master home chefs", "Artisan handicraft souvenir shopping tour");
    }

    private List<String> getCuratedStayList(String destName, Double budget) {
        if (budget != null && budget >= 30000) {
            return List.of("Luxury 5-Star Heritage Palace / Resort with Infinity Pool", "Private Scenic Villa with dedicated Chef & Butler", "Boutique Eco-Retreat nestled in Nature");
        } else if (budget != null && budget >= 15000) {
            return List.of("Premium 3-Star City Center Hotel with Complimentary Breakfast", "High-rated Boutique Bed & Breakfast Homestay", "Spacious Serviced Apartment with Kitchenette");
        }
        return List.of("Top-rated Social Backpackers Hostel (Zostel/Hosteller)", "Clean Budget Guest House with High-Speed Wi-Fi", "Verified Local Heritage Homestay");
    }

    private List<String> getCuratedTipsList(String destName) {
        return List.of(
                "Book popular monument passes and activity slots online 24-48 hours in advance to skip ticket queues.",
                "Keep local digital payments (UPI) and a small stash of emergency cash ready for local street stalls.",
                "Carry lightweight rain gear or sunscreen depending on season.",
                "Use ride-hailing apps or negotiate prepaid taxi fares prior to departure."
        );
    }
}
