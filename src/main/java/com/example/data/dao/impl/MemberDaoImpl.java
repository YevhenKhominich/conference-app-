package com.example.data.dao.impl;

import com.example.data.dao.MemberDao;
import com.example.data.entity.Member;
import com.example.exception.DBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("memderDao")
@Slf4j
public class MemberDaoImpl implements MemberDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Member create(Member member) {

        entityManager.persist(member);
        log.info("Created Member with id : " + member.getId());

        return member;
    }

    @Override
    public Member get(long id) {
        return entityManager.find(Member.class, id);
    }

    @Override
    public List getAll() {
        return entityManager
                .createQuery("select m from Member m").getResultList();
    }

    @Override
    public Member update(Member member) {
        entityManager.merge(member);
        return member;
    }

    @Override
    public void delete(long id) {
        int n = entityManager.createQuery("delete from Member m where m.id=:id")
                .setParameter("id", id).executeUpdate();
        if (n < 1)
            throw new DBException(String.format("Something gone wrong when deleting member with id: %d", id));


    }
}
