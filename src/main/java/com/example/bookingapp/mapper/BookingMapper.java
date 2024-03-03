package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.BookingListResponse;
import com.example.bookingapp.dto.BookingRequest;
import com.example.bookingapp.dto.BookingResponse;
import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.service.RoomService;
import com.example.bookingapp.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { UserService.class, RoomService.class, BookingDateParser.class})
public interface BookingMapper {
    @Mapping(source = "request.roomId", target = "room")
    @Mapping(source = "request.checkIn", target = "checkIn")
    @Mapping(source = "request.checkOut", target = "checkOut")
    Booking bookingRequestToBooking(BookingRequest request);

///////////////////////////////////////////// Отсутствует в техзадании
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "request.roomId", target = "room")
//    @Mapping(source = "request.checkIn", target = "checkIn")
//    @Mapping(source = "request.checkOut", target = "checkOut")
//    Booking bookingRequestToBooking(Long id, BookingRequest request);

    @Mapping(source = "user", target = "userId")
    @Mapping(source = "room", target = "roomId")
    BookingResponse bookingToBookingResponse(Booking booking);

    default BookingListResponse bookingListToBookingListResponse(List<Booking> bookings) {
        return new BookingListResponse(
                bookings.stream().map(this::bookingToBookingResponse).toList());
    }
}
