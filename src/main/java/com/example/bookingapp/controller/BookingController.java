package com.example.bookingapp.controller;

import com.example.bookingapp.dto.BookingListResponse;
import com.example.bookingapp.dto.BookingRequest;
import com.example.bookingapp.dto.BookingResponse;
import com.example.bookingapp.mapper.BookingMapper;
import com.example.bookingapp.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    private final BookingMapper mapper;

    @GetMapping
    public ResponseEntity<BookingListResponse> findAll() {
        return ResponseEntity.ok(
                mapper.bookingListToBookingListResponse(
                        service.findAll()));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                mapper.bookingToBookingResponse(
                        service.create(
                                mapper.bookingRequestToBooking(request))));
    }
}
