package com.example.bookingapp;

import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository repository;

    public List<Hotel> findAllHotels() {
        return repository.findAll();
    }

    public Hotel findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Hotel create(Hotel hotel) {
        return repository.save(hotel);
    }

    public Hotel update(Hotel hotel) {
        Hotel existedHotel = findById(hotel.getId());

        BeanUtils.nonNullPropertiesCopy(hotel, existedHotel);

        return repository.save(existedHotel);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }


}
