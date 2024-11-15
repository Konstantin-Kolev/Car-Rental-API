package com.project01.car_rental.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Car {
    private final List<String> VALID_CITIES = List.of("Sofia", "Plovdiv", "Varna", "Burgas");

    private int id;
    private String model;
    private String location;
    private double dailyRate;
    private boolean isAvailable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @JsonIgnore
    public boolean isValidCity() {
        return this.VALID_CITIES.contains(this.location);
    }
}
