package com.project01.car_rental.mappers;

import com.project01.car_rental.entities.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setPhoneNumber(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setAge(rs.getInt("age"));
        customer.setHasAccidents(rs.getBoolean("has_accidents"));

        return customer;
    }
}
