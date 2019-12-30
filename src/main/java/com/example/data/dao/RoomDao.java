package com.example.data.dao;

import com.example.data.entity.Room;

import java.util.List;

public interface RoomDao extends BaseDao<Room> {
    @Override
    Room create(Room room);

    @Override
    Room get(long id);

    @Override
    List<Room> getAll();

    @Override
    Room update(Room room);

    @Override
    void delete(long id);
}
