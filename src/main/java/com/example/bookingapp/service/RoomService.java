package com.example.bookingapp.service;

import com.example.bookingapp.entity.Room;
import com.example.bookingapp.error.EntityNotFoundException;
import com.example.bookingapp.repository.RoomRepository;
import com.example.bookingapp.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository repository;

///////////////////////////////////////////// Отсутствует в техзадании
//    public List<Room> findAll() {
//        return repository.findAll();
//    }

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

    // for BookingMapper
    public Long idByRoom(Room room) {
        return room.getId();
    }
}
