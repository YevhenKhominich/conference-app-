package com.example.data.dao.impl;

import com.example.data.dao.RoomDao;
import com.example.data.entity.Room;
import com.example.exception.DBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository("roomDao")
@Slf4j
public class RoomDaoImpl implements RoomDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Room create(Room room) {
        entityManager.persist(room);
        log.info("Room was created with id: " + room.getId());
        return room;
    }

    @Override
    public Room get(long id) {
        return entityManager.find(Room.class, id);
    }

    @Override
    public List getAll() {
        return entityManager.createQuery("select r from Room r").getResultList();
    }

    @Override
    public Room update(Room room) {
        entityManager.merge(room);
        return room;
    }

    @Override
    public void delete(long id) {

        int n = entityManager.createQuery("delete from Room r where r.id=:id")
                .setParameter("id", id).executeUpdate();

        if (n < 1)
            throw new DBException(String.format("Something gone wrong when deleting room with id: %d", id));


    }
}
