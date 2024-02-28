package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.HotelListResponse;
import com.example.bookingapp.dto.HotelRequest;
import com.example.bookingapp.dto.HotelResponse;
import com.example.bookingapp.dto.HotelResponseShort;
import com.example.bookingapp.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    Hotel hotelRequestToHotel(HotelRequest request);

    @Mapping(source = "id", target = "id")
    Hotel hotelRequestToHotel(Long id, HotelRequest request);


    HotelResponseShort hotelToHotelResponseShort(Hotel hotel);

    HotelResponse hotelToHotelResponse(Hotel hotel);

    default HotelListResponse hotelListToHotelListResponse(List<Hotel> hotels) {

        return new HotelListResponse(
                hotels.stream().map(this::hotelToHotelResponse).toList()
        );
    }

}
