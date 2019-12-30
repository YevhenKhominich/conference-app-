package com.example.web.dto;


import com.example.data.entity.Conference;
import com.example.data.entity.Room;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Optional;

@Data
@Builder
public class ConferenceDto {

    private Date date;

    private String name;

    private Optional<Room> room;

    private Integer amountOfMembers;

    public static ConferenceDto from(Conference conference) {

        return ConferenceDto.builder()
                .name(conference.getName())
                .date(conference.getDate())
                .amountOfMembers(conference.getMembers().size())
                .room(Optional.ofNullable(conference.getRoom()))
                .build();
    }
}
