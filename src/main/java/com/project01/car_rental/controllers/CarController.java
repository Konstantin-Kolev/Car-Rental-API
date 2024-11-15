package com.project01.car_rental.controllers;

import com.project01.car_rental.entities.Car;
import com.project01.car_rental.http.AppResponse;
import com.project01.car_rental.services.CarService;
import com.project01.car_rental.services.DataValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CarController {

    private final CarService carService;



    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars")
    public ResponseEntity<?> createCar(@RequestBody Car car) {
        if (!DataValidator.isCityValid(car.getLocation())) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Location is invalid")
                    .build();
        }

        if (!this.carService.createCar(car)) {
            return AppResponse.error()
                    .withMessage("Error during car creation")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Car successfully created")
                .build();
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars(@RequestParam(required = false) String location) {

        List<Car> cars;
        if (location == null) {
            cars = this.carService.getAllCars();
        } else {
            if (!DataValidator.isCityValid(location)) {
                return AppResponse.error()
                        .withCode(HttpStatus.BAD_REQUEST)
                        .withMessage("Location is invalid")
                        .build();
            }
            cars = this.carService.getCarsByLocation(location);
        }

        return AppResponse.success()
                .withData(cars)
                .build();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getCarById(@PathVariable int id) {
        Car car = this.carService.getCarById(id);
        if (car == null) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Car not found")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(car)
                .build();
    }

    @PutMapping("/cars")
    public ResponseEntity<?> updateCar(@RequestBody Car car) {
        if (!DataValidator.isCityValid(car.getLocation())) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Location is invalid")
                    .build();
        }

        if (!this.carService.updateCar(car)) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Car not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Car successfully updated")
                .build();
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable int id) {
        if (!this.carService.deleteCar(id)) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Car not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Car successfully deleted")
                .build();
    }
}
