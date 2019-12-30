package com.example.service.impl;

import com.example.data.dao.ConferenceDao;
import com.example.data.dao.MemberDao;
import com.example.data.dao.RoomDao;
import com.example.data.entity.Conference;
import com.example.data.entity.Room;
import com.example.service.ConferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceDao conferenceDao;
    private MemberDao memberDao;
    private RoomDao roomDao;

    @Autowired
    public ConferenceServiceImpl(ConferenceDao conferenceDao, MemberDao memberDao) {
        this.conferenceDao = conferenceDao;
        this.memberDao = memberDao;
    }

    @Override
    public Conference save(Conference conference) {

//        int n =20;

        //Сетит комнату которая не занята
//        Room room1 = roomDao.getAll().stream().filter(room -> room.getMaxSeats() >= n && !room.isOccupied()).findAny().get();
//        room1.setOccupied(true);
//
//
//        conference.setRoom(room1);

        log.info("Room is " + conference.getRoom());

        return conferenceDao.create(conference);
    }

    @Override
    public void cancel(long id) {

        Conference conference = conferenceDao.get(id);
        Room room = conference.getRoom();

        if (room != null) {
            room.setOccupied(false);
        }

        conferenceDao.delete(id);
    }

    @Override
    public List getAll() {
        return conferenceDao.getAll();
    }

    @Override
    public Conference get(long id) {
        return conferenceDao.get(id);
    }

    @Override
    public boolean isAvailable(Conference conference) {

        return conference.getRoom().getMaxSeats() > conference.getMembers().size();
    }

    @Override
    public List getAllMembersForConference(long id) {

        return memberDao.getAll()
                .stream()
                .filter(member -> member.getConference().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public Conference update(Conference conference) {
        return conferenceDao.update(conference);
    }


}
