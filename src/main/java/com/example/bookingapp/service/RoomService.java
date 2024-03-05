package com.example.bookingapp.service;

import com.example.bookingapp.entity.Room;
import com.example.bookingapp.error.EntityNotFoundException;
import com.example.bookingapp.error.IncorrectRequestException;
import com.example.bookingapp.filter.RoomFilter;
import com.example.bookingapp.filter.RoomSpecification;
import com.example.bookingapp.mapper.DateParser;
import com.example.bookingapp.repository.RoomRepository;
import com.example.bookingapp.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository repository;

    private final BookingService bookingService;


    public List<Room> filterBy(RoomFilter filter) {

        return repository.findAll(
                RoomSpecification.withFilter(filter), PageRequest.of(filter.getPageNumber(), filter.getPageSize()))
                .getContent()
                .stream().filter(room -> {

                            if (doFilterByPeriod(filter)) {
                                return bookingService.getCrossBookingList(
                                        room.getId(),
                                        DateParser.parse(filter.getArrival()),
                                        DateParser.parse(filter.getDeparture())).isEmpty();
                            }
                            return true;
                        }
                ).toList();
    }


    public Room findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("В базе данных нет комнаты с ID " + id));
    }

    public Room create(Room room) {
        return repository.save(room);
    }

    public Room update(Room room) {
        Room existedRoom = findById(room.getId());

        BeanUtils.nonNullPropertiesCopy(room, existedRoom);

        return repository.save(existedRoom);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    private boolean doFilterByPeriod(RoomFilter filter) {
        if(filter.getArrival() != null && filter.getDeparture() != null) return true;

        if(filter.getArrival() == null && filter.getDeparture() == null) return false;

        throw new IncorrectRequestException("Для фильтрации по периоду проживания необходимо указать обе даты");
    }


    // for BookingMapper
    public Long idByRoom(Room room) {
        return room.getId();
    }
}
