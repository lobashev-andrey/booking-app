package com.example.bookingapp.service;

import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.error.EntityNotFoundException;
import com.example.bookingapp.filter.HotelFilter;
import com.example.bookingapp.filter.HotelSpecification;
import com.example.bookingapp.repository.HotelRepository;
import com.example.bookingapp.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository repository;

    public List<Hotel> findAllHotels() {
        log.debug("findAllHotels() method is called");

        return repository.findAll();
    }

    public List<Hotel> filterBy(HotelFilter filter) {
        log.debug("filterBy() method is called");

        return repository.findAll(
                HotelSpecification.withFilter(filter), PageRequest.of(filter.getPageNumber(), filter.getPageSize()))
                .getContent();
    }

    public Hotel findById(Long id) {
        log.debug("findById() method is called with id={}", id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("В базе данных нет отеля с ID " + id));
    }

    public Hotel create(Hotel hotel) {
        log.debug("create() method is called");

        return repository.save(hotel);
    }

    public Hotel update(Hotel hotel) {
        log.debug("update() method is called");

        Hotel existedHotel = findById(hotel.getId());

        BeanUtils.nonNullPropertiesCopy(hotel, existedHotel);

        return repository.save(existedHotel);
    }


    public void delete(Long id) {
        log.debug("delete() method is called with id={}", id);

        repository.delete(findById(id));
    }

    public long count() {
        log.debug("count() method is called");

        return repository.count();
    }


    // for RoomMapper
    public Long idByHotel(Hotel hotel) {
        log.debug("idByHotel() method is called");

        return hotel.getId();
    }
}
