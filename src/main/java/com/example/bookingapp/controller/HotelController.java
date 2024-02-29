package com.example.bookingapp.controller;

import com.example.bookingapp.service.HotelService;
import com.example.bookingapp.dto.HotelListResponse;
import com.example.bookingapp.dto.HotelRequest;
import com.example.bookingapp.dto.HotelResponse;
import com.example.bookingapp.dto.HotelResponseShort;
import com.example.bookingapp.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HotelResponse> create(@RequestBody HotelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.hotelToHotelResponse(
                        service.create(
                                mapper.hotelRequestToHotel(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponseShort> update(@PathVariable Long id, @RequestBody HotelRequest request){
        return ResponseEntity.ok(
                mapper.hotelToHotelResponseShort(
                        service.update(
                                mapper.hotelRequestToHotel(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
