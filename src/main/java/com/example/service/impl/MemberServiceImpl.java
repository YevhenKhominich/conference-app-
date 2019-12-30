package com.example.service.impl;

import com.example.data.dao.ConferenceDao;
import com.example.data.dao.MemberDao;
import com.example.data.entity.Conference;
import com.example.data.entity.Member;
import com.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private MemberDao memberDao;
    private ConferenceDao conferenceDao;

    @Autowired
    public MemberServiceImpl(MemberDao memberDao, ConferenceDao conferenceDao) {
        this.memberDao = memberDao;
        this.conferenceDao = conferenceDao;
    }

    @Override
    public Member save(Member member) {
        return memberDao.create(member);
    }

    @Override
    public void delete(long id) {
        memberDao.delete(id);
    }

    @Override
    public List<Member> getAll() {
        return memberDao.getAll();
    }

    @Override
    public Member get(long id) {
        return memberDao.get(id);
    }

    @Override
    public Member update(Member member) {
        return memberDao.update(member);
    }

    @Override
    public void addMemberToConference(Member member, long conferenceId) {
        Conference conference = conferenceDao.get(conferenceId);
        conference.getMembers().add(member);

        member.setConference(conference);
        memberDao.create(member);
    }

}
