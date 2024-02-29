package com.example.bookingapp.controller;

import com.example.bookingapp.dto.RoomRequest;
import com.example.bookingapp.dto.RoomResponse;
import com.example.bookingapp.mapper.RoomMapper;
import com.example.bookingapp.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    private final RoomMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                mapper.roomToRoomResponse(
                        service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> create(@RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.roomToRoomResponse(
                        service.create(
                                mapper.roomRequestToRoom(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id, @RequestBody RoomRequest request) {
        return ResponseEntity.ok(
                mapper.roomToRoomResponse(
                        service.update(
                                mapper.roomRequestToRoom(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
