package com.example.web.dto;

import com.example.data.entity.Conference;
import com.example.data.entity.Member;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;
import java.util.function.Consumer;

@Data
@Builder
public class MemberDto {

    private String firstName;

    private String lastName;

    private int age;

    private Optional<Conference> conference;

    public static MemberDto from(Member member) {

        return MemberDto.builder()
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .age(member.getAge())
                .conference(Optional.ofNullable(member.getConference()))
                .build();

    }

}
