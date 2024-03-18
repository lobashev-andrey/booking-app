package com.example.bookingapp.controller;

import com.example.bookingapp.dto.*;
import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.error.IncorrectRequestException;
import com.example.bookingapp.filter.HotelFilter;
import com.example.bookingapp.service.HotelService;
import com.example.bookingapp.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService service;

    private final HotelMapper mapper;

    @GetMapping
    public ResponseEntity<HotelListResponse> getAll() {
        log.info("HotelController: getAll() method is called");

        return ResponseEntity.ok(
                mapper.hotelListToHotelListResponse(
                        service.findAllHotels()
                ));
    }

    @GetMapping("/filter")
    public ResponseEntity<HotelFilterListResponse> filterBy(HotelFilter filter) {
        log.info("HotelController: filterBy() method is called");

        return ResponseEntity.ok(
                mapper.hotelListToHotelFilterListResponse(
                        service.count(), service.filterBy(filter))
        );


    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getById(@PathVariable Long id) {
        log.info("HotelController: getById() method is called with id={}", id);

        return ResponseEntity.ok(
                mapper.hotelToHotelResponse(
                        service.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotelResponse> create(@RequestBody HotelRequest request) {
        log.info("HotelController: create() method is called");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.hotelToHotelResponse(
                        service.create(
                                mapper.hotelRequestToHotel(request))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotelResponseShort> update(@PathVariable Long id, @RequestBody HotelRequest request){
        log.info("HotelController: update() method is called with id={}", id);

        return ResponseEntity.ok(
                mapper.hotelToHotelResponseShort(
                        service.update(
                                mapper.hotelRequestToHotel(id, request))));
    }

    @PutMapping("/{id}/{mark}")
    public ResponseEntity<HotelResponse> changeRating(@PathVariable Long id, @PathVariable Integer mark) {
        log.info("HotelController: changeRating() method is called with id={}", id);

        Hotel hotel = vote(service.findById(id), mark);

        return ResponseEntity.ok(
                mapper.hotelToHotelResponse(
                        service.update(
                                hotel)));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("delete() method is called with id={}", id);

        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private Hotel vote(Hotel hotel, Integer mark) {
        log.info("vote() method is called with mark={}", mark);

        if(mark < 1 || mark > 5) {
            throw new IncorrectRequestException("Для оценки отеля используйте целые числа от 1 до 5");
        }
        Float rawRating = hotel.getRawRating();
        Integer votes = hotel.getVotes() + 1;

        rawRating = (rawRating * votes - rawRating + mark) / votes;

        Float rating = Math.round(rawRating * 10) / 10f;

        hotel.setVotes(votes);
        hotel.setRating(rating);
        hotel.setRawRating(rawRating);

        return hotel;
    }
}
