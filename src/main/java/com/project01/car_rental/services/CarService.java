package com.project01.car_rental.services;

import com.project01.car_rental.entities.Car;
import com.project01.car_rental.mappers.CarRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final JdbcTemplate db;
    public CarService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public boolean createCar(Car car) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO td_cars (model, location, daily_rate) VALUES (?, ?, ?)");

        this.db.update(sql.toString(), car.getModel(), car.getLocation(), car.getDailyRate());
        return true;
    }

    public List<Car> getAllCars() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_cars WHERE is_active = true");

        return this.db.query(sql.toString(), new CarRowMapper());
    }

    public List<Car> getCarsByLocation(String location) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_cars WHERE location = ? AND is_active = true");

        return this.db.query(sql.toString(), new CarRowMapper(), location);
    }

    public Car getCarById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_cars WHERE id = ? AND is_active = true");

        List<Car> result = this.db.query(sql.toString(), new CarRowMapper(), id);
        return result.isEmpty() ? null : result.get(0);
    }

    public Car getCarByModelAndLocation(String model, String location) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_cars WHERE model = ? AND location = ? AND is_active = true LIMIT 1");

        List<Car> result = this.db.query(sql.toString(), new CarRowMapper(), model, location);
        return result.isEmpty() ? null : result.get(0);
    }

    public boolean updateCar(Car car) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE td_cars SET model = ?, location = ?, daily_rate = ?, is_available = ? WHERE id = ? AND is_active = true");

        int result = this.db.update(sql.toString(), car.getModel(), car.getLocation(), car.getDailyRate(), car.isAvailable(), car.getId());
        return result == 1;
    }

    public boolean deleteCar(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE td_cars SET is_active = false WHERE id = ?");

        int result = this.db.update(sql.toString(), id);
        return result == 1;
    }
}
