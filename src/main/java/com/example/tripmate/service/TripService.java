package com.example.tripmate.service;

import com.example.tripmate.dto.BudgetBreakdown;
import com.example.tripmate.dto.TripPlaceRequest;
import com.example.tripmate.dto.TripRequest;
import com.example.tripmate.dto.TripResponse;
import com.example.tripmate.entity.*;
import com.example.tripmate.exception.BadRequestException;
import com.example.tripmate.exception.ResourceNotFoundException;
import com.example.tripmate.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;
    private final PlaceRepository placeRepository;
    private final TripPlaceRepository tripPlaceRepository;
    private final BudgetService budgetService;

    public TripService(TripRepository tripRepository, UserRepository userRepository,
                       DestinationRepository destinationRepository, PlaceRepository placeRepository,
                       TripPlaceRepository tripPlaceRepository, BudgetService budgetService) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.destinationRepository = destinationRepository;
        this.placeRepository = placeRepository;
        this.tripPlaceRepository = tripPlaceRepository;
        this.budgetService = budgetService;
    }

    @Transactional
    public TripResponse createTrip(TripRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        Destination destination = destinationRepository.findById(request.getDestinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + request.getDestinationId()));

        // Collect places if provided
        List<Place> selectedPlaces = new ArrayList<>();
        if (request.getPlaces() != null && !request.getPlaces().isEmpty()) {
            for (TripPlaceRequest pr : request.getPlaces()) {
                placeRepository.findById(pr.getPlaceId()).ifPresent(selectedPlaces::add);
            }
        }

        // Calculate dynamic estimated budget
        BudgetBreakdown budgetBreakdown = budgetService.calculateBudget(
                request.getBudget(),
                request.getNumberOfDays(),
                request.getNumberOfTravelers(),
                destination,
                selectedPlaces
        );

        Trip trip = new Trip();
        trip.setUser(user);
        trip.setDestination(destination);
        trip.setNumberOfDays(request.getNumberOfDays());
        trip.setBudget(request.getBudget());
        trip.setNumberOfTravelers(request.getNumberOfTravelers());
        trip.setTravelDate(request.getTravelDate());
        trip.setInterests(request.getInterests());
        trip.setEstimatedCost(budgetBreakdown.getTotalEstimatedCost());
        trip.setStatus(request.getStatus() != null ? request.getStatus().toUpperCase() : "PLANNED");

        Trip savedTrip = tripRepository.save(trip);

        // Save trip places if any
        if (request.getPlaces() != null && !request.getPlaces().isEmpty()) {
            for (TripPlaceRequest pr : request.getPlaces()) {
                Place place = placeRepository.findById(pr.getPlaceId()).orElse(null);
                if (place != null) {
                    TripPlace tp = new TripPlace(savedTrip, place, pr.getVisitDay(), pr.getNotes());
                    tripPlaceRepository.save(tp);
                }
            }
        }

        return mapToTripResponse(savedTrip);
    }

    public List<TripResponse> getUserTrips(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        List<Trip> trips = tripRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        return trips.stream().map(this::mapToTripResponse).collect(Collectors.toList());
    }

    public TripResponse getTripById(Long tripId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        Trip trip = tripRepository.findByIdAndUserId(tripId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

        return mapToTripResponse(trip);
    }

    @Transactional
    public TripResponse updateTrip(Long tripId, TripRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        Trip trip = tripRepository.findByIdAndUserId(tripId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

        if (request.getDestinationId() != null) {
            Destination destination = destinationRepository.findById(request.getDestinationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + request.getDestinationId()));
            trip.setDestination(destination);
        }

        if (request.getNumberOfDays() != null) trip.setNumberOfDays(request.getNumberOfDays());
        if (request.getBudget() != null) trip.setBudget(request.getBudget());
        if (request.getNumberOfTravelers() != null) trip.setNumberOfTravelers(request.getNumberOfTravelers());
        if (request.getTravelDate() != null) trip.setTravelDate(request.getTravelDate());
        if (request.getInterests() != null) trip.setInterests(request.getInterests());
        if (request.getStatus() != null) trip.setStatus(request.getStatus().toUpperCase());

        // Recalculate estimated cost
        List<TripPlace> currentPlaces = tripPlaceRepository.findByTripIdOrderByVisitDayAsc(tripId);
        List<Place> places = currentPlaces.stream().map(TripPlace::getPlace).collect(Collectors.toList());

        BudgetBreakdown budgetBreakdown = budgetService.calculateBudget(
                trip.getBudget(),
                trip.getNumberOfDays(),
                trip.getNumberOfTravelers(),
                trip.getDestination(),
                places
        );
        trip.setEstimatedCost(budgetBreakdown.getTotalEstimatedCost());

        Trip updated = tripRepository.save(trip);
        return mapToTripResponse(updated);
    }

    @Transactional
    public void deleteTrip(Long tripId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        Trip trip = tripRepository.findByIdAndUserId(tripId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

        tripPlaceRepository.deleteByTripId(trip.getId());
        tripRepository.delete(trip);
    }

    @Transactional
    public TripResponse addPlaceToTrip(Long tripId, TripPlaceRequest placeRequest, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        Trip trip = tripRepository.findByIdAndUserId(tripId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

        Place place = placeRepository.findById(placeRequest.getPlaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Place not found with id: " + placeRequest.getPlaceId()));

        TripPlace tripPlace = new TripPlace(trip, place, placeRequest.getVisitDay(), placeRequest.getNotes());
        tripPlaceRepository.save(tripPlace);

        // Update estimated cost
        List<TripPlace> currentPlaces = tripPlaceRepository.findByTripIdOrderByVisitDayAsc(tripId);
        List<Place> places = currentPlaces.stream().map(TripPlace::getPlace).collect(Collectors.toList());
        BudgetBreakdown budgetBreakdown = budgetService.calculateBudget(
                trip.getBudget(), trip.getNumberOfDays(), trip.getNumberOfTravelers(), trip.getDestination(), places);
        trip.setEstimatedCost(budgetBreakdown.getTotalEstimatedCost());
        tripRepository.save(trip);

        return mapToTripResponse(trip);
    }

    @Transactional
    public TripResponse removePlaceFromTrip(Long tripId, Long tripPlaceId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        Trip trip = tripRepository.findByIdAndUserId(tripId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

        tripPlaceRepository.deleteById(tripPlaceId);

        // Update estimated cost
        List<TripPlace> currentPlaces = tripPlaceRepository.findByTripIdOrderByVisitDayAsc(tripId);
        List<Place> places = currentPlaces.stream().map(TripPlace::getPlace).collect(Collectors.toList());
        BudgetBreakdown budgetBreakdown = budgetService.calculateBudget(
                trip.getBudget(), trip.getNumberOfDays(), trip.getNumberOfTravelers(), trip.getDestination(), places);
        trip.setEstimatedCost(budgetBreakdown.getTotalEstimatedCost());
        tripRepository.save(trip);

        return mapToTripResponse(trip);
    }

    private TripResponse mapToTripResponse(Trip trip) {
        TripResponse res = new TripResponse();
        res.setId(trip.getId());
        res.setDestination(trip.getDestination());
        res.setNumberOfDays(trip.getNumberOfDays());
        res.setBudget(trip.getBudget());
        res.setNumberOfTravelers(trip.getNumberOfTravelers());
        res.setTravelDate(trip.getTravelDate());
        res.setInterests(trip.getInterests());
        res.setEstimatedCost(trip.getEstimatedCost());
        res.setStatus(trip.getStatus());
        res.setCreatedAt(trip.getCreatedAt());

        double remaining = trip.getBudget() - trip.getEstimatedCost();
        res.setRemainingBudget(remaining);
        res.setIsWithinBudget(remaining >= 0);

        List<TripPlace> tps = tripPlaceRepository.findByTripIdOrderByVisitDayAsc(trip.getId());
        List<TripResponse.TripPlaceItem> items = new ArrayList<>();
        List<Place> placeList = new ArrayList<>();

        for (TripPlace tp : tps) {
            TripResponse.TripPlaceItem item = new TripResponse.TripPlaceItem();
            item.setId(tp.getId());
            item.setPlaceId(tp.getPlace().getId());
            item.setPlaceName(tp.getPlace().getName());
            item.setCategory(tp.getPlace().getCategory());
            item.setLocation(tp.getPlace().getLocation());
            item.setEstimatedCost(tp.getPlace().getEstimatedCost());
            item.setImageUrl(tp.getPlace().getImageUrl());
            item.setVisitDay(tp.getVisitDay());
            item.setNotes(tp.getNotes());
            items.add(item);
            placeList.add(tp.getPlace());
        }
        res.setPlaces(items);

        BudgetBreakdown budgetBreakdown = budgetService.calculateBudget(
                trip.getBudget(),
                trip.getNumberOfDays(),
                trip.getNumberOfTravelers(),
                trip.getDestination(),
                placeList
        );
        res.setBudgetBreakdown(budgetBreakdown);

        return res;
    }
}
