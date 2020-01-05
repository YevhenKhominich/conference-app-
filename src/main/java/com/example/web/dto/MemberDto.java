package com.example.web.dto;

import com.example.data.entity.Member;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class MemberDto {

    private String firstName;

    private String lastName;

    private int age;

    private String conferenceName;

    public static MemberDto from(Member member) {


        String conferenceName;

        if (member.getConference() != null) {
            conferenceName = member.getConference().getName();
        } else conferenceName = "No conference";


        return MemberDto.builder()
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .age(member.getAge())
                .conferenceName(conferenceName)
                .build();

    }

}
