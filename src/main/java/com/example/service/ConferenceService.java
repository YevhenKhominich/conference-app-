package com.example.service;

import com.example.data.entity.Conference;
import com.example.data.entity.Member;

import java.util.List;

public interface ConferenceService {

    Conference save(Conference conference);

    void cancel(long id);

    List<Conference> getAll();

    Conference get(long id);

    boolean isAvailable(Conference conference);

    List getAllMembersForConference(long id);

    Conference update(Conference conference);
}
