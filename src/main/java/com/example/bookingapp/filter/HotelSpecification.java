package com.example.bookingapp.filter;

import com.example.bookingapp.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter filter) {

        return Specification.where(byHotelId(filter.getHotelId()))
                .and(byName(filter.getName()))
                .and(byAnnounce(filter.getAnnounce()))
                .and(byCity(filter.getCity()))
                .and(byAddress(filter.getAddress()))
                .and(byDistance(filter.getDistance()))
                .and(byRating(filter.getRating()))
                .and(byVotes(filter.getVotes()));
    }

    static Specification<Hotel> byHotelId(Long hotelId) {
        return ((root, query, cb) -> {
            if(hotelId == null) { return null; }

            return cb.equal(root.get("id"), hotelId);
        });
    }

    static Specification<Hotel> byName(String name) {
        return ((root, query, cb) -> {
            if(name == null) { return null; }

            return cb.equal(root.get("name"), name);
        });
    }

    static Specification<Hotel> byAnnounce(String announce) {
        return ((root, query, cb) -> {
            if(announce == null) { return null; }

            return cb.like(root.get("announce"), "%" + announce + "%");    ///// ЧТОБЫ ПОКОРОЧЕ /////
        });
    }

    static Specification<Hotel> byCity(String city) {
        return ((root, query, cb) -> {
            if(city == null) { return null; }

            return cb.equal(root.get("city"), city);
        });
    }

    static Specification<Hotel> byAddress(String address) {
        return ((root, query, cb) -> {
            if(address == null) { return null; }

            return cb.like(root.get("address"), "%" + address + "%");
        });
    }

    static Specification<Hotel> byDistance(Float distance) {
        return ((root, query, cb) -> {
            if(distance == null) { return null; }

            return cb.lessThanOrEqualTo(root.get("distance"), distance);
        });
    }

    static Specification<Hotel> byRating(Float rating) {
        return ((root, query, cb) -> {
            if(rating == null) { return null; }

            return cb.greaterThanOrEqualTo(root.get("rating"), rating);
        });
    }

    static Specification<Hotel> byVotes(Integer votes) {
        return ((root, query, cb) -> {
            if(votes == null) { return null; }

            return cb.greaterThanOrEqualTo(root.get("votes"), votes);
        });
    }
}
