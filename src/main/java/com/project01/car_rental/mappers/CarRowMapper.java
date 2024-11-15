package com.project01.car_rental.mappers;

import com.project01.car_rental.entities.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {

        Car car = new Car();
        car.setId(rs.getInt("id"));
        car.setModel(rs.getString("model"));
        car.setDailyRate(rs.getDouble("daily_rate"));
        car.setLocation(rs.getString("location"));
        car.setAvailable(rs.getBoolean("is_available"));

        return car;
    }
}
