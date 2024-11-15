package com.project01.car_rental.services;

import com.project01.car_rental.entities.Customer;
import com.project01.car_rental.mappers.CustomerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class CustomerService {
    private final JdbcTemplate db;

    public CustomerService(JdbcTemplate db) {
        this.db = db;
    }

    public int createCustomer(Customer customer) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO td_clients (first_name, last_name, phone, address, age, has_accidents) values(?, ?, ?, ?, ?, ?)");

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.db.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql.toString(), new String[] {"id"});  // Specify the column(s) for generated keys
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                ps.setString(3, customer.getPhoneNumber());
                ps.setString(4, customer.getAddress());
                ps.setInt(5, customer.getAge());
                ps.setBoolean(6, customer.getHasAccidents());
                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey() == null ? 0 : keyHolder.getKey().intValue();
    }

    public List<Customer> getAllCustomers() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_clients WHERE is_active = true");

        return this.db.query(sql.toString(), new CustomerRowMapper());
    }

    public Customer getCustomerById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_clients WHERE id = ? AND is_active = true");

        List<Customer> customers = this.db.query(sql.toString(), new CustomerRowMapper(), id);
        return customers.isEmpty() ? null : customers.get(0);
    }

    public Customer getCustomerByNamePhoneAddress(String firstName, String lastName, String phoneNumber, String address) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_clients WHERE first_name = ? AND last_name = ? AND phone = ? AND address = ? AND is_active = true LIMIT 1");

        List<Customer> customers = this.db.query(sql.toString(), new CustomerRowMapper(), firstName, lastName, phoneNumber, address);
        return customers.isEmpty() ? null : customers.get(0);
    }

    public boolean updateCustomer(Customer customer) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE td_clients SET first_name = ?, last_name = ?, phone = ?, address = ?, age = ?, has_accidents = ? WHERE id = ? AND is_active = true");

        int result = this.db.update(sql.toString(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getAddress(),
                customer.getAge(),
                customer.getHasAccidents(),
                customer.getId());

        return result == 1;
    }

    public boolean deleteCustomer(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE td_clients SET is_active = false WHERE id = ?");

        int result = this.db.update(sql.toString(), id);
        return result == 1;
    }
}
