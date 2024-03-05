package com.example.bookingapp.filter;

import com.example.bookingapp.entity.Room;
import org.springframework.data.jpa.domain.Specification;

public interface RoomSpecification {

    static Specification<Room> withFilter(RoomFilter filter) {
        return Specification
                .where(byRoomId(filter.getRoomId()))
                .and(byName(filter.getName()))
                .and(byMinCost(filter.getMinCost()))
                .and(byMaxCost(filter.getMaxCost()))
                .and(byCapacity(filter.getCapacity()))
                .and(byHotelId(filter.getHotelId()));
    }

    static Specification<Room> byRoomId(Long roomId) {
        return ((root, query, cb) -> {
            if (roomId == null) { return null; }

            return cb.equal(root.get("id"), roomId);
        });
    }


    static Specification<Room> byName(String name) {
        return ((root, query, cb) -> {
            if (name == null) { return null; }

            return cb.equal(root.get("name"), name);
        });
    }

    static Specification<Room> byMinCost(Integer minCost) {
        return ((root, query, cb) -> {
            if (minCost == null) { return null; }

            return cb.greaterThanOrEqualTo(root.get("price"), minCost);
        });
    }

    static Specification<Room> byMaxCost(Integer maxCost) {
        return ((root, query, cb) -> {
            if (maxCost == null) { return null; }

            return cb.lessThanOrEqualTo(root.get("price"), maxCost);
        });
    }

    static Specification<Room> byCapacity(Integer capacity) {
        return ((root, query, cb) -> {
            if (capacity == null) { return null; }

            return cb.equal(root.get("capacity"), capacity);
        });
    }

    static Specification<Room> byHotelId(Long hotelId) {
        return ((root, query, cb) -> {
            if (hotelId == null) { return null; }

            return cb.equal(root.get("hotelId"), hotelId);
        });
    }
}
