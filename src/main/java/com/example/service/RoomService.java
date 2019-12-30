package com.example.service;


import com.example.data.entity.Room;

import java.util.List;

public interface RoomService {

    Room save(Room room);

    void delete(long id);

    Room get(long id);

    List<Room> getAll();

    boolean isAvailable(Room room);

    Room update(Room room);


}
