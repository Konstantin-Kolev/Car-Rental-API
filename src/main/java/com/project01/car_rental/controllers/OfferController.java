package com.project01.car_rental.controllers;

import com.project01.car_rental.entities.Car;
import com.project01.car_rental.entities.Customer;
import com.project01.car_rental.entities.Offer;
import com.project01.car_rental.entities.OfferWithCustomer;
import com.project01.car_rental.http.AppResponse;
import com.project01.car_rental.services.CarService;
import com.project01.car_rental.services.CustomerService;
import com.project01.car_rental.services.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OfferController {
    private final OfferService offerService;
    private final CustomerService customerService;
    private final CarService carService;

    public OfferController(OfferService offerService, CustomerService customerService, CarService carService) {
        this.offerService = offerService;
        this.customerService = customerService;
        this.carService = carService;
    }

    @PostMapping("/offers")
    public ResponseEntity<?> createOffer(@RequestBody Offer offer) {
        Customer orderCustomer = this.customerService.getCustomerById(offer.getCustomerId());
        if (orderCustomer == null) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Customer doesn't exist")
                    .build();
        }

        Car orderCar = this.carService.getCarById(offer.getCarId());
        if (orderCar == null) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Car doesn't exist")
                    .build();
        }

        offer.calculatePrice(orderCustomer.getHasAccidents(), orderCar.getDailyRate());

        if (!this.offerService.createOffer(offer)) {
            return AppResponse.error()
                    .withMessage("Error during offer creation")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Offer created successfully")
                .build();
    }

    @PostMapping("/offers/_createCustomer")
    public ResponseEntity<?> createOfferWithCustomer(@RequestBody OfferWithCustomer offerWithCustomer) {
        int customerId, carId;
        boolean customerAccidents;
        Customer customerForOffer = this.customerService.getCustomerByNamePhoneAddress(
                offerWithCustomer.getFirstName(),
                offerWithCustomer.getLastName(),
                offerWithCustomer.getPhoneNumber(),
                offerWithCustomer.getAddress());
        if (customerForOffer == null) {
            Customer newCustomer = new Customer();
            newCustomer.setFirstName(offerWithCustomer.getFirstName());
            newCustomer.setLastName(offerWithCustomer.getLastName());
            newCustomer.setPhoneNumber(offerWithCustomer.getPhoneNumber());
            newCustomer.setAddress(offerWithCustomer.getAddress());
            newCustomer.setAge(offerWithCustomer.getAge());
            newCustomer.setHasAccidents(offerWithCustomer.getHasAccidents());
            customerId = this.customerService.createCustomer(newCustomer);
            customerAccidents = offerWithCustomer.getHasAccidents();
        } else {
            customerId = customerForOffer.getId();
            customerAccidents = customerForOffer.getHasAccidents();
        }

        Car carForOffer = this.carService.getCarByModelAndLocation(offerWithCustomer.getModel(), offerWithCustomer.getAddress());
        if (carForOffer == null) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Car with that model doesn't exist in this location")
                    .build();
        }
        carId = carForOffer.getId();

        Offer newOffer = new Offer();
        newOffer.setCustomerId(customerId);
        newOffer.setCarId(carId);
        newOffer.setRentalStart(offerWithCustomer.getRentalStart());
        newOffer.setRentalEnd(offerWithCustomer.getRentalEnd());
        newOffer.calculatePrice(customerAccidents, carForOffer.getDailyRate());

        if (!this.offerService.createOffer(newOffer)) {
            return AppResponse.error()
                    .withMessage("Error during offer creation")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Offer created successfully")
                .build();
    }

    @GetMapping("/offers")
    public ResponseEntity<?> getAllOffers(@RequestParam(required = false, defaultValue = "0") int customerId) {
        List<Offer> offers;
        if (customerId == 0) {
            offers = this.offerService.getAllOffers();
        } else {
            offers = this.offerService.getOffersByClientId(customerId);
        }

        return AppResponse.success()
                .withData(offers)
                .build();
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<?> getOfferById(@PathVariable int id) {
        Offer offer = this.offerService.getOfferById(id);
        if (offer == null) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Offer not found")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(offer)
                .build();
    }

    @PostMapping("/offers/{id}")
    public ResponseEntity<?> acceptOffer(@PathVariable int id) {
        if (!this.offerService.acceptOffer(id)) {
            return AppResponse.error()
                    .withMessage("Error during offer update")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Offer accepted")
                .build();
    }

    @PutMapping("/offers")
    public ResponseEntity<?> updateOffer(@RequestBody Offer offer) {
        if (!this.offerService.updateOffer(offer)) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Offer not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Offer updated successfully")
                .build();
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable int id) {
        if (!this.offerService.deleteOffer(id)) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Offer not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Offer deleted successfully")
                .build();
    }
}
