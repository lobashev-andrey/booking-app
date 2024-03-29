package com.example.bookingapp.controller;

import com.example.bookingapp.dto.RoomFilterListResponse;
import com.example.bookingapp.dto.RoomRequest;
import com.example.bookingapp.dto.RoomResponse;
import com.example.bookingapp.filter.RoomFilter;
import com.example.bookingapp.mapper.RoomMapper;
import com.example.bookingapp.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService service;

    private final RoomMapper mapper;

    @GetMapping("/filter")
    public ResponseEntity<RoomFilterListResponse> filterBy(RoomFilter filter) {
        log.info("filterBy() method is called");

        return ResponseEntity.ok(
                mapper.roomListToRoomFilterListResponse(
                        service.filterBy(filter)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        log.info("findById() method is called with id={}", id);

        return ResponseEntity.ok(
                mapper.roomToRoomResponse(
                        service.findById(id)));
    }

@PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoomResponse> create(@RequestBody RoomRequest request) {
        log.info("create() method is called");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.roomToRoomResponse(
                        service.create(
                                mapper.roomRequestToRoom(request))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id, @RequestBody RoomRequest request) {
        log.info("update() method is called with id={}", id);

        return ResponseEntity.ok(
                mapper.roomToRoomResponse(
                        service.update(
                                mapper.roomRequestToRoom(id, request))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("delete() method is called with id={}", id);

        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
