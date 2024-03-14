package com.example.bookingapp.statistics.data.entity;

import com.example.bookingapp.statistics.model.KafkaBookingMessage;
import com.example.bookingapp.statistics.model.KafkaUserMessage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KafkaMapper {

    UserData toUserData(KafkaUserMessage kafkaMessage);

    BookingData toBookingData(KafkaBookingMessage kafkaMessage);

}
