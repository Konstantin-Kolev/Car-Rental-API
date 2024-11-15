package com.project01.car_rental.controllers;

import com.project01.car_rental.entities.Customer;
import com.project01.car_rental.http.AppResponse;
import com.project01.car_rental.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        if (this.customerService.createCustomer(customer) == 0) {
            return AppResponse.error()
                    .withMessage("Error during customer creation")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Customer created successfully")
                .build();
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customers = this.customerService.getAllCustomers();

        return AppResponse.success()
                .withData(customers)
                .build();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable int id) {
        Customer customer = this.customerService.getCustomerById(id);

        if (customer == null) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Customer not found")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(customer)
                .build();
    }

    @PutMapping("/customers")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        if (!this.customerService.updateCustomer(customer)) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Customer not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Customer updated successfully")
                .build();
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
        if (!this.customerService.deleteCustomer(id)) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Customer not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Customer deleted successfully")
                .build();
    }
}
