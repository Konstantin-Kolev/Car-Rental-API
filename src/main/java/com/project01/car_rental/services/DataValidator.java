package com.project01.car_rental.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataValidator {
    private final static List<String> VALID_CITIES = List.of("Sofia", "Plovdiv", "Varna", "Burgas");

    public static boolean isCityValid(String city) {
        return VALID_CITIES.contains(city);
    }

    public static boolean isDateValid(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
