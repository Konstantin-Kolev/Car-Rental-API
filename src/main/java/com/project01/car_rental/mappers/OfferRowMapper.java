package com.project01.car_rental.mappers;

import com.project01.car_rental.entities.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferRowMapper implements RowMapper<Offer> {
    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setId(rs.getInt("id"));
        offer.setCarId(rs.getInt("car_id"));
        offer.setCustomerId(rs.getInt("client_id"));
        offer.setRentalStart(rs.getString("rental_start"));
        offer.setRentalEnd(rs.getString("rental_end"));
        offer.setAccepted(rs.getBoolean("accepted"));
        offer.setTotalPrice(rs.getDouble("total_price"));

        return offer;
    }
}
