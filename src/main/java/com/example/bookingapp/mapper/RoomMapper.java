package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.RoomFilterListResponse;
import com.example.bookingapp.service.HotelService;
import com.example.bookingapp.dto.RoomRequest;
import com.example.bookingapp.dto.RoomResponse;
import com.example.bookingapp.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {HotelService.class})
public interface RoomMapper {

    @Mapping(source = "hotelId", target = "hotel")
    Room roomRequestToRoom(RoomRequest request);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "request.hotelId", target = "hotel")
    Room roomRequestToRoom(Long id, RoomRequest request);

    @Mapping(source = "hotel", target = "hotelId")
    RoomResponse roomToRoomResponse(Room room);

    List<RoomResponse> roomListToRoomResponseList(List<Room> rooms);

    default RoomFilterListResponse roomListToRoomFilterListResponse(List<Room> rooms) {
        return new RoomFilterListResponse(roomListToRoomResponseList(rooms));
    }

}
