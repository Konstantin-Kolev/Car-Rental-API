package com.project01.car_rental.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Offer {
    private int id;
    private int carId;
    private int customerId;
    private String rentalStart;
    private String rentalEnd;
    private double totalPrice;
    private boolean accepted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(String rentalStart) {
        this.rentalStart = rentalStart;
    }

    public String getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(String rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @JsonIgnore
    public void calculatePrice(boolean customerAccident, double carRate) {
        HashMap<String, Integer> days  = this.calculateDays();
        if (customerAccident) {
            this.totalPrice += 200;
        }

        this.totalPrice += carRate * days.getOrDefault("Weekdays", 0);
        this.totalPrice += (carRate * 1.1) * days.getOrDefault("Weekends", 0);
    }

    private HashMap<String, Integer> calculateDays() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        HashMap<String, Integer> days = new HashMap<>();
        days.put("Weekdays", 0);
        days.put("Weekends", 0);

        LocalDate startDate = LocalDate.parse(this.getRentalStart(), formatter);
        LocalDate endDate = LocalDate.parse(this.getRentalEnd(), formatter);

        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            DayOfWeek day = startDate.getDayOfWeek();

            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                days.put("Weekends", days.getOrDefault("Weekends", 0) + 1);
            } else {
                days.put("Weekdays", days.getOrDefault("Weekdays", 0) + 1);
            }

            startDate = startDate.plusDays(1);
        }

        return days;
    }
}
