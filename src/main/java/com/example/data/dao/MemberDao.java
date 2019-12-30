package com.example.data.dao;

import com.example.data.entity.Member;

import java.util.List;

public interface MemberDao extends BaseDao<Member> {
    @Override
    Member create(Member member);

    @Override
    Member get(long id);

    @Override
    List<Member> getAll();

    @Override
    Member update(Member member);

    @Override
    void delete(long id);

}
