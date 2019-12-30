package com.example.data.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "ROOM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "room_name")
    private String name;

    @Column(name = "max_seats")
    private int maxSeats;

    // Can add the new entity Location
    @Column
    private int floor;

    @Column(name = "occupied")
    private boolean isOccupied = false;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxSeats=" + maxSeats +
                ", floor=" + floor +
                ", isOccupied=" + isOccupied +
                '}';
    }
}
