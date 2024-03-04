package com.example.bookingapp.controller;

import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.error.IncorrectRequestException;
import com.example.bookingapp.service.HotelService;
import com.example.bookingapp.dto.HotelListResponse;
import com.example.bookingapp.dto.HotelRequest;
import com.example.bookingapp.dto.HotelResponse;
import com.example.bookingapp.dto.HotelResponseShort;
import com.example.bookingapp.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService service;

    private final HotelMapper mapper;

    @GetMapping
    public ResponseEntity<HotelListResponse> getAll() {

        return ResponseEntity.ok(
                mapper.hotelListToHotelListResponse(
                        service.findAllHotels()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                mapper.hotelToHotelResponse(
                        service.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotelResponse> create(@RequestBody HotelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.hotelToHotelResponse(
                        service.create(
                                mapper.hotelRequestToHotel(request))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotelResponseShort> update(@PathVariable Long id, @RequestBody HotelRequest request){
        return ResponseEntity.ok(
                mapper.hotelToHotelResponseShort(
                        service.update(
                                mapper.hotelRequestToHotel(id, request))));
    }

    @PutMapping("/{id}/{mark}")
    public ResponseEntity<HotelResponse> changeRating(@PathVariable Long id, @PathVariable Integer mark) {

        Hotel hotel = vote(service.findById(id), mark);

        return ResponseEntity.ok(
                mapper.hotelToHotelResponse(
                        service.update(
                                hotel)));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Вариант с хранением rawRating - близко к техзаданию (если голосов ближе к 10 млн, надо Double вместо Float)
    private Hotel vote(Hotel hotel, Integer mark) {
        if(mark < 1 || mark > 5) {
            throw new IncorrectRequestException("Для оценки отеля используйте числа от 1 до 5");
        }
        Float rawRating = hotel.getRawRating();
        Integer votes = hotel.getVotes() + 1;

        rawRating = (rawRating * votes - rawRating + mark) / votes;

        Float rating = Math.round(rawRating * 10) / 10f;

        System.out.print(votes + " mark: " + mark + " rating: " + rating + " rawRating: " + rawRating);

        hotel.setVotes(votes);
        hotel.setRating(rating);
        hotel.setRawRating(rawRating);

        System.out.println(" rawRating из базы: " + hotel.getRawRating());

        return hotel;
    }

    // Вариант как в техзадании - дает некорректные результаты
//    private Hotel vote(Hotel hotel, Integer mark) {
//        if(mark < 1 || mark > 5) {
//            throw new IncorrectRequestException("Для оценки отеля используйте числа от 1 до 5");
//        }
//
//        Float rating = hotel.getRating();
//        Integer votes = hotel.getVotes() + 1;
//
//        rating = (rating * votes - rating + mark) / votes;
//        rating = Math.round(rating * 10) / 10f;
//
//        hotel.setRating(rating);
//        hotel.setVotes(votes);
//
//        return hotel;
//    }

//    // Вариант с хранением totalRating  ( Надо добавить в сущность    private Float totalRating = 0f; )
//    private Hotel vote(Hotel hotel, Integer mark) {
//        if(mark < 1 || mark > 5) {
//            throw new IncorrectRequestException("Для оценки отеля используйте числа от 1 до 5");
//        }
//        Float newTotalRating = hotel.getTotalRating() + mark;
//        Integer newVotes = hotel.getVotes() + 1;
//
//        Float rating = Math.round(newTotalRating / newVotes * 10) / 10f;
//
//        hotel.setTotalRating(newTotalRating);
//        hotel.setVotes(newVotes);
//        hotel.setRating(rating);
//
//        return hotel;
//    }


}
