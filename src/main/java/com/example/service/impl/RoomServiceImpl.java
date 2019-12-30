package com.example.service.impl;

import com.example.data.dao.RoomDao;
import com.example.data.entity.Room;
import com.example.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private RoomDao roomDao;

    @Autowired
    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public Room save(Room room) {
        return roomDao.create(room);
    }

    @Override
    public void delete(long id) {
        roomDao.delete(id);
    }

    @Override
    public Room get(long id) {
        return roomDao.get(id);
    }

    @Override
    public List<Room> getAll() {
        return roomDao.getAll();
    }

    @Override
    public boolean isAvailable(Room room) {
        return room.isOccupied();
    }

    @Override
    public Room update(Room room) {
        return roomDao.update(room);
    }
}
