package com.example.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "conferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    // @NotNull(message = "Name cant be null") look hibernate validator
    @Column(name = "conference_name")
    private String name;

    @Temporal(TemporalType.DATE)
//    @NotNull(message = "Time cant be null")
    @Column(name = "conference_date")
    private Date date;

    @JsonIgnore
    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private Set<Member> members = new HashSet<>();


    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", members=" + members +
                +
                        '}';
    }
}
