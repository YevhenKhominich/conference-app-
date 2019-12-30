package com.example.data.dao.impl;

import com.example.data.dao.ConferenceDao;
import com.example.data.entity.Conference;
import com.example.exception.DBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository("conferenceDaoImpl")
@Slf4j
public class ConferenceDaoImpl implements ConferenceDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Conference get(long id) {
        return entityManager.find(Conference.class, id);
    }

    @Override
    public List getAll() {
        return entityManager.createQuery("select c from Conference c")
                .getResultList();
    }

    @Override
    public Conference update(Conference conference) {
        return entityManager.merge(conference);
    }

    @Override
    public void delete(long id) {

        int n = entityManager.createQuery("delete from Conference c where c.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        if (n < 1)
            throw new DBException(String.format("Something gone wrong when deleting Conference with id: %d", id));


    }

    @Override
    public Conference create(Conference conference) {

        entityManager.persist(conference);
        log.info("Conference is created with id: " + conference.getId());

        return conference;
    }


}

