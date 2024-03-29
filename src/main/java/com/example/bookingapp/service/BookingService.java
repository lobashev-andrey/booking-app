package com.example.bookingapp.service;

import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.error.IncorrectRequestException;
import com.example.bookingapp.filter.BookingFilter;
import com.example.bookingapp.repository.BookingRepository;
import com.example.bookingapp.dto.KafkaBookingMessage;
import com.example.bookingapp.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    @Value("${app.kafka.kafka-booking-topic}")
    private String bookingTopic;

    private final BookingRepository repository;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public List<Booking> findAll(BookingFilter filter) {
        log.debug("findAll() method is called");

        return repository.findAll(
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    public Booking create(Booking booking) {
        log.info("create() method is called");
        checkBeforeBooking(booking);

        Booking createdBooking = repository.save(booking);
        kafkaTemplate.send(bookingTopic,
                            new KafkaBookingMessage(
                                    createdBooking.getRoom().getId(),
                                    createdBooking.getUser().getId(),
                                    createdBooking.getCheckIn().toString(),
                                    createdBooking.getCheckOut().toString()));
        return createdBooking;
    }

    public void checkBeforeBooking(Booking booking) {
        log.info("checkBeforeBooking() method is called");

        List<Booking> crossBookingList = getCrossBookingList(
                booking.getRoom().getId(), booking.getCheckIn(), booking.getCheckOut()
        );

        if(!crossBookingList.isEmpty()) {
            throw new IncorrectRequestException("Данная комната недоступна для бронирования в указанный период");
        }
    }

    public List<Booking> getCrossBookingList(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        log.info("getCrossBookingList() method is called");

        if(checkIn.isAfter(checkOut) ||
                checkIn.isBefore(LocalDate.now())) {
            throw new IncorrectRequestException("Введите корректные даты начала и окончания периода бронирования");
        }

        return repository.checkBeforeBooking(roomId, checkIn, checkOut);
    }
}
