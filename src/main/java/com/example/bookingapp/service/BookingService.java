package com.example.bookingapp.service;

import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.error.IncorrectRequestException;
import com.example.bookingapp.filter.BookingFilter;
import com.example.bookingapp.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository repository;

    public List<Booking> findAll(BookingFilter filter) {
        return repository.findAll(
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    public Booking create(Booking booking) {
        checkBeforeBooking(booking);

        return repository.save(booking);
    }

    public void checkBeforeBooking(Booking booking) {

        List<Booking> crossBookingList = getCrossBookingList(
                booking.getRoom().getId(), booking.getCheckIn(), booking.getCheckOut()
        );

        if(!crossBookingList.isEmpty()) {
            throw new IncorrectRequestException("Данная комната недоступна для бронирования в указанный период");
        }
    }

    public List<Booking> getCrossBookingList(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        if(checkIn.isAfter(checkOut) ||
                checkIn.isBefore(LocalDate.now())) {
            throw new IncorrectRequestException("Введите корректные даты начала и окончания периода бронирования");
        }

        return repository.checkBeforeBooking(roomId, checkIn, checkOut);
    }
}
