package com.project01.car_rental.services;

import com.project01.car_rental.entities.Offer;
import com.project01.car_rental.mappers.OfferRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    private final JdbcTemplate db;

    public OfferService(JdbcTemplate db) {
        this.db = db;
    }

    public boolean createOffer(Offer offer) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO td_offers (client_id, car_id, rental_start, rental_end, total_price) VALUES (?, ?, ?, ?, ?)");

        this.db.update(sql.toString(),
                offer.getCustomerId(),
                offer.getCarId(),
                offer.getRentalStart(),
                offer.getRentalEnd(),
                offer.getTotalPrice());

        return true;
    }

    public List<Offer> getAllOffers() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_offers WHERE is_active = true");

        return this.db.query(sql.toString(), new OfferRowMapper());
    }

    public List<Offer> getOffersByClientId(int clientId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_offers WHERE client_id = ? AND is_active = true");

        return this.db.query(sql.toString(), new OfferRowMapper(), clientId);
    }

    public Offer getOfferById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_offers WHERE id = ? AND is_active = true");

        List<Offer> offers = this.db.query(sql.toString(), new OfferRowMapper(), id);
        return offers.isEmpty() ? null : offers.get(0);
    }

    public boolean updateOffer(Offer offer) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE td_offers SET client_id = ?, car_id = ?, rental_start = ?, rental_end = ?, total_price = ? WHERE id = ? AND is_active = true");

        int result = this.db.update(sql.toString(),
                offer.getCustomerId(),
                offer.getCarId(),
                offer.getRentalStart(),
                offer.getRentalEnd(),
                offer.getTotalPrice());

        return result == 1;
    }

    public boolean acceptOffer(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE td_offers SET accepted = true WHERE id = ? AND is_active = true");

        int result = this.db.update(sql.toString(), id);
        return result == 1;
    }

    public boolean deleteOffer(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE td_offers SET is_active = false WHERE id = ?");

        int result = this.db.update(sql.toString(), id);
        return result == 1;
    }
}
