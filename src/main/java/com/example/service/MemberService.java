package com.example.service;



import com.example.data.entity.Member;

import java.util.List;

public interface MemberService {

    Member save(Member member);

    void delete(long id);

    List<Member> getAll();

    Member get(long id);

    Member update(Member member);

    void addMemberToConference(Member member,long id);
}
