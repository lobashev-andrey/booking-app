package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.*;
import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.service.HotelService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { HotelService.class})
public interface HotelMapper {

    Hotel hotelRequestToHotel(HotelRequest request);

    @Mapping(source = "id", target = "id")
    Hotel hotelRequestToHotel(Long id, HotelRequest request);


    HotelResponseShort hotelToHotelResponseShort(Hotel hotel);

    HotelResponse hotelToHotelResponse(Hotel hotel);

    List<HotelResponse> hotelsToHotelResponses(List<Hotel> hotels);


//    default HotelListResponse hotelListToHotelListResponse(List<Hotel> hotels) {
//
//        return new HotelListResponse(
//                hotels.stream().map(this::hotelToHotelResponse).toList()
//        );
//    }
    default HotelListResponse hotelListToHotelListResponse(List<Hotel> hotels) {

    return new HotelListResponse( hotelsToHotelResponses(hotels));
    }

    default HotelFilterListResponse hotelListToHotelFilterListResponse(Long count, List<Hotel> hotels) {
        return new HotelFilterListResponse( count, hotelsToHotelResponses(hotels) );
    }





}
