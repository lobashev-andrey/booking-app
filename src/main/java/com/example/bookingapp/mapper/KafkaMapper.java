package com.example.bookingapp.mapper;

import com.example.bookingapp.entity.BookingData;
import com.example.bookingapp.entity.UserData;
import com.example.bookingapp.dto.KafkaBookingMessage;
import com.example.bookingapp.dto.KafkaUserMessage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KafkaMapper {

    UserData toUserData(KafkaUserMessage kafkaMessage);

    BookingData toBookingData(KafkaBookingMessage kafkaMessage);

}
