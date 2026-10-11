package com.example.tripmate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "trip_places")
public class TripPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonIgnore
    private Trip trip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "visit_day", nullable = false)
    private Integer visitDay;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public TripPlace() {
    }

    public TripPlace(Trip trip, Place place, Integer visitDay, String notes) {
        this.trip = trip;
        this.place = place;
        this.visitDay = visitDay;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
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
