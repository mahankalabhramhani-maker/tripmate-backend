package com.example.tripmate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destinations")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "average_budget", nullable = false)
    private Double averageBudget;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    public Destination() {
    }

    public Destination(String name, String country, String description, Double averageBudget, String imageUrl) {
        this.name = name;
        this.country = country;
        this.description = description;
        this.averageBudget = averageBudget;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAverageBudget() {
        return averageBudget;
    }

    public void setAverageBudget(Double averageBudget) {
        this.averageBudget = averageBudget;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
