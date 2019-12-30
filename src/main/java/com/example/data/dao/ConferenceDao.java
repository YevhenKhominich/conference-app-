package com.example.data.dao;

import com.example.data.entity.Conference;

import java.util.List;

public interface ConferenceDao extends BaseDao<Conference> {
    @Override
    Conference create(Conference conference);

    @Override
    Conference get(long id);

    @Override
    List getAll();

    @Override
    Conference update(Conference conference);

    @Override
    void delete(long id);
}
