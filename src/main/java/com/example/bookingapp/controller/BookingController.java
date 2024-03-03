package com.example.bookingapp.controller;

import com.example.bookingapp.dto.BookingListResponse;
import com.example.bookingapp.dto.BookingRequest;
import com.example.bookingapp.dto.BookingResponse;
import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.mapper.BookingMapper;
import com.example.bookingapp.service.BookingService;
import com.example.bookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    private final BookingMapper mapper;

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BookingListResponse> findAll() {
        return ResponseEntity.ok(
                mapper.bookingListToBookingListResponse(
                        service.findAll()));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request,
                                                  @AuthenticationPrincipal UserDetails userDetails) {

        Booking booking = mapper.bookingRequestToBooking(request);
        booking.setUser(userService.findByName(userDetails.getUsername()));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                mapper.bookingToBookingResponse(
                        service.create( booking )));
    }
}
