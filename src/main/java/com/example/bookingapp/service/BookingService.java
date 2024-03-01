package com.example.bookingapp.service;

import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.entity.Room;
import com.example.bookingapp.error.EntityNotFoundException;
import com.example.bookingapp.error.IncorrectRequestException;
import com.example.bookingapp.repository.BookingRepository;
import com.example.bookingapp.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository repository;

    public List<Booking> findAll() {
        return repository.findAll();
    }
///////////////////////////////////////////// Отсутствует в техзадании
//    public Booking findById(Long id) {
//        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("В базе данных нет бронирования с ID " + id));
//    }

    public Booking create(Booking booking) {
        checkBeforeBooking(booking);

        return repository.save(booking);
    }
///////////////////////////////////////////// Отсутствует в техзадании
//    public Booking update(Booking booking) {
//        Booking existedBooking = findById(booking.getId());
//
//        BeanUtils.nonNullPropertiesCopy(booking, existedBooking);
//
//        return repository.save(existedBooking);
//    }
//
//    public void delete(Long id) {
//        repository.delete(findById(id));
//    }

    public void checkBeforeBooking(Booking booking) {
        if(booking.getCheckIn().isAfter(booking.getCheckOut()) ||
                booking.getCheckIn().isBefore(LocalDate.now())) {
            throw new IncorrectRequestException("Введите корректные даты начала и окончания периода бронирования");
        }

        List<Booking> bookings = repository.checkBeforeBooking(booking.getRoom().getId(), booking.getCheckIn(), booking.getCheckOut());

        if(!bookings.isEmpty()) {
            throw new IncorrectRequestException("Данная комната недоступна для бронирования в указанный период");
        }
    }

}
