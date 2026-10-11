package com.example.tripmate.service;

import com.example.tripmate.dto.BudgetBreakdown;
import com.example.tripmate.dto.RecommendationRequest;
import com.example.tripmate.dto.RecommendationResponse;
import com.example.tripmate.entity.Destination;
import com.example.tripmate.entity.Place;
import com.example.tripmate.exception.ResourceNotFoundException;
import com.example.tripmate.repository.DestinationRepository;
import com.example.tripmate.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final DestinationRepository destinationRepository;
    private final PlaceRepository placeRepository;
    private final BudgetService budgetService;

    public RecommendationService(DestinationRepository destinationRepository,
                                  PlaceRepository placeRepository,
                                  BudgetService budgetService) {
        this.destinationRepository = destinationRepository;
        this.placeRepository = placeRepository;
        this.budgetService = budgetService;
    }

    public RecommendationResponse getRecommendations(RecommendationRequest request) {
        Destination destination = destinationRepository.findById(request.getDestinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + request.getDestinationId()));

        List<Place> allPlacesForDest = placeRepository.findByDestinationId(destination.getId());

        List<String> interests = request.getInterests();
        List<Place> matchingPlaces = new ArrayList<>();

        if (interests != null && !interests.isEmpty()) {
            Set<String> interestSet = interests.stream()
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            // Score & filter places matching user interests
            for (Place place : allPlacesForDest) {
                if (interestSet.contains(place.getCategory().toLowerCase()) ||
                    interestSet.contains("all") ||
                    interests.stream().anyMatch(i -> place.getDescription().toLowerCase().contains(i.toLowerCase()))) {
                    matchingPlaces.add(place);
                }
            }
        }

        // If no matching places found by interest or none specified, return all places for destination
        if (matchingPlaces.isEmpty()) {
            matchingPlaces.addAll(allPlacesForDest);
        }

        // Budget feasibility check: sort by cost and prioritize
        matchingPlaces.sort(Comparator.comparing(Place::getEstimatedCost));

        // Limit the places to fit the number of days (roughly 2-3 places per day)
        int maxPlacesToRecommend = Math.min(matchingPlaces.size(), request.getNumberOfDays() * 3);
        List<Place> selectedRecommendations = matchingPlaces.subList(0, maxPlacesToRecommend);

        // Calculate budget breakdown
        BudgetBreakdown budgetBreakdown = budgetService.calculateBudget(
                request.getBudget(),
                request.getNumberOfDays(),
                request.getNumberOfTravelers(),
                destination,
                selectedRecommendations
        );

        // Generate day-by-day distribution
        Map<Integer, List<Place>> dayPlan = new HashMap<>();
        int day = 1;
        for (Place place : selectedRecommendations) {
            dayPlan.computeIfAbsent(day, k -> new ArrayList<>()).add(place);
            if (dayPlan.get(day).size() >= 2 && day < request.getNumberOfDays()) {
                day++;
            }
        }

        // Generate curated foods, activities, accommodations & tips based on destination name
        List<String> foods = getLocalFoodRecommendations(destination.getName());
        List<String> activities = getActivitiesRecommendations(destination.getName(), interests);
        List<String> accommodations = getAccommodationSuggestions(destination.getName(), request.getBudget());
        List<String> tips = getTravelTips(destination.getName());

        RecommendationResponse response = new RecommendationResponse();
        response.setDestination(destination);
        response.setRecommendedPlaces(selectedRecommendations);
        response.setRecommendedFoods(foods);
        response.setRecommendedActivities(activities);
        response.setAccommodationSuggestions(accommodations);
        response.setTravelTips(tips);
        response.setBudgetBreakdown(budgetBreakdown);
        response.setSuggestedDayPlan(dayPlan);

        return response;
    }

    private List<String> getLocalFoodRecommendations(String destName) {
        String lower = destName.toLowerCase();
        if (lower.contains("goa")) {
            return List.of("Goan Fish Curry & Rice", "Prawn Balchão", "Bebinca Dessert", "Goan Poi Bread with Vindaloo", "Feni Cocktail");
        } else if (lower.contains("hyderabad")) {
            return List.of("Hyderabadi Dum Biryani", "Mirchi Ka Salan", "Double Ka Meetha", "Irani Chai with Osmania Biscuits", "Haleem");
        } else if (lower.contains("manali")) {
            return List.of("Himachali Siddu with Ghee", "Trout Fish Fry", "Thukpa & Steamed Momos", "Kullu Dham Thali", "Hot Apple Cider");
        } else if (lower.contains("jaipur")) {
            return List.of("Dal Baati Churma", "Pyaaz Kachori (Rawat Misthan)", "Laal Maas (Mutton Curry)", "Ghewar sweet", "Mawa Kachori");
        } else if (lower.contains("kashmir")) {
            return List.of("Kashmiri Wazwan & Rogan Josh", "Yakhni Mutton", "Kahwa Saffron Green Tea", "Modur Pulao", "Nadru Yakhni (Lotus Stem)");
        } else if (lower.contains("mumbai")) {
            return List.of("Vada Pav & Pav Bhaji", "Bombay Duck Fry", "Misal Pav", "Bun Maska & Chai", "Crab Masala");
        } else if (lower.contains("delhi")) {
            return List.of("Chole Bhature", "Butter Chicken with Butter Naan", "Chandni Chowk Paranthas", "Dahi Bhalla", "Kulfi Falooda");
        } else if (lower.contains("bangalore")) {
            return List.of("Crispy Benne Masala Dosa (CTR)", "Filter Coffee", "Bisi Bele Bath", "Mangalore Buns", "Mysore Pak");
        }
        return List.of("Authentic Local Thali", "Street Food Specials", "Regional Signature Curries", "Fresh Artisan Sweets", "Handcrafted Local Beverages");
    }

    private List<String> getActivitiesRecommendations(String destName, List<String> interests) {
        String lower = destName.toLowerCase();
        if (lower.contains("goa")) {
            return List.of("Scuba Diving & Parasailing at Grand Island", "Sunset Cruise on Mandovi River", "Beach Shack Nightlife at Anjuna", "Spice Plantation Guided Tour");
        } else if (lower.contains("hyderabad")) {
            return List.of("Sound & Light Show at Golconda Fort", "Boating to Buddha Statue in Hussain Sagar", "Shopping for Pearls & Bangles at Laad Bazaar", "Heritage Walk around Old City");
        } else if (lower.contains("manali")) {
            return List.of("Paragliding & Zorbing in Solang Valley", "Trekking to Jogini Waterfalls", "White Water Rafting in Beas River", "Bathing in Vashisht Natural Hot Springs");
        } else if (lower.contains("jaipur")) {
            return List.of("Hot Air Balloon Safari over Amber Fort", "Traditional Puppet Show & Dance at Chokhi Dhani", "Shopping at Johari & Bapu Bazaars", "Sunset from Nahargarh Fort");
        } else if (lower.contains("kashmir")) {
            return List.of("Shikara Ride & Floating Market on Dal Lake", "Gondola Cable Car Ride in Gulmarg", "Pony ride in Betaab Valley Pahalgam", "Apple Orchard walk & Saffron shopping");
        } else if (lower.contains("mumbai")) {
            return List.of("Sunset walk along Marine Drive Queen's Necklace", "Ferry Ride to Elephanta Caves", "Art Walk at Kala Ghoda", "Bandra Street Art & Cafe hopping");
        } else if (lower.contains("delhi")) {
            return List.of("Rickshaw ride through Chandni Chowk alleys", "Sound & Light show at Red Fort", "Picnic at Lodhi Gardens", "Shopping handicrafts at Dilli Haat");
        }
        return List.of("Guided Heritage Sightseeing Walk", "Sunset viewpoint exploration", "Local Handicraft & Cultural Market Shopping", "Regional Culinary Workshop & Food Walk");
    }

    private List<String> getAccommodationSuggestions(String destName, Double budget) {
        if (budget != null && budget > 35000) {
            return List.of(
                    "Luxury 5-Star Heritage Resort (₹6,000 - ₹9,000/night)",
                    "Boutique Sea/Mountain View Villa with Breakfast (₹4,500 - ₹7,000/night)",
                    "Premium Spa & Wellness Hotel (₹5,000/night)"
            );
        } else if (budget != null && budget >= 15000) {
            return List.of(
                    "Mid-Range 3-Star Comfort Hotel with Breakfast (₹2,200 - ₹3,500/night)",
                    "Charming Boutique Homestay / Guest House (₹1,800 - ₹2,800/night)",
                    "Private Room in Central Serviced Apartments (₹2,000/night)"
            );
        } else {
            return List.of(
                    "Trendy Social Backpackers Hostel (Zostel/Hosteller) (₹600 - ₹1,200/bed)",
                    "Budget Friendly Travel Lodge / Eco Cottage (₹1,000 - ₹1,800/night)",
                    "Certified Local Guesthouse near Public Transit (₹900 - ₹1,500/night)"
            );
        }
    }

    private List<String> getTravelTips(String destName) {
        return List.of(
                "Book monument tickets and activity slots online to bypass long queues.",
                "Keep local cash/UPI handy as some local markets and street food vendors prefer it.",
                "Use verified local cabs or bike rentals for cost-effective transportation.",
                "Carry a refillable water bottle and sunscreen during day sightseeing."
        );
    }
}
