package com.example.web.controller.integration;

import com.example.data.dao.RoomDao;
import com.example.data.entity.Conference;
import com.example.data.entity.Room;
import com.example.web.controller.ControllerBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoomControllerInregrationTest extends ControllerBaseTest {

    private static final long TEST_ID = 1l;

    private final static Room TEST_ROOM = Room.builder()
            .id(TEST_ID)
            .floor(2)
            .maxSeats(50)
            .name("Test")
            .isOccupied(false)
            .build();

    private static final Room TEST_ROOM_UPDATED = Room.builder()
            .name("UpdateRoom")
            .floor(3)
            .maxSeats(50)
            .isOccupied(false)
            .build();
    ;

    private Room INVALID_ROOM = Room.builder()
            .name("Octopus")
            .build();

    private static final Conference TEST_CONFERENCE = Conference.builder()
            .name("Opa")
            .date(new Date(145L))
            .build();


    @Autowired
    RoomDao roomDao;

    @Test
    void whenGetAllShouldRespondOkTest() throws Exception {

        mockMvc.perform(get("/rooms")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(status().isOk());

    }

    @Test
    @SqlGroup({
            @Sql(statements = "INSERT INTO rooms" +
                    " (id, room_name, floor, max_seats, occupied)" +
                    " VALUES (1, 'Test', 2, 50, 'false',)")
    })
    void whenGetAllShouldRespondOkAndReturnListTest() throws Exception {
        //when-then

        mockMvc.perform(get("/rooms")

                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]name", is("Test")))
                .andExpect(jsonPath("$[0].floor", is(2)))
                .andExpect(jsonPath("$[0].maxSeats", is(50)));

    }

    @Test
    void whenCreateRoomShouldReturnCreatedTest() throws Exception {
        Room expected = TEST_ROOM;

        //when-then
        mockMvc.perform(post("/rooms")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(OBJECT_MAPPER.writeValueAsString(expected)))
                .andExpect(status().isCreated());//assert

        List<Room> rooms = roomDao.getAll();
        Room actual = rooms.get(0);

        //assert
        assertEquals(1, rooms.size());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getMaxSeats(), actual.getMaxSeats());
        assertEquals(expected.getFloor(), actual.getFloor());
        assertFalse(actual.isOccupied());

        assertTrue(Objects.nonNull(actual.getId()) && actual.getId() > 0);

    }


    @Test
    @SqlGroup({
            @Sql(statements = "INSERT INTO rooms" +
                    " (id, room_name, floor, max_seats, occupied)" +
                    " VALUES (1, 'Test', 2, 50, 'false',)")

    })
    void whenCreateExistingRoomShouldReturnConflictTest() throws Exception {
        //when-then
        mockMvc.perform(post("/rooms")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(OBJECT_MAPPER.writeValueAsString(TEST_ROOM)))
                //assert
                .andExpect(status().isConflict());
    }

    @Test
    void whenCreateInvalidRoomShouldRespondBadRequestTest() throws Exception {
        //when-then
        mockMvc.perform(post("/rooms")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(OBJECT_MAPPER.writeValueAsString(INVALID_ROOM)))
                //assert
                .andExpect(status().isBadRequest());
    }


    @Test
    @SqlGroup({
            @Sql(statements = "INSERT INTO rooms" +
                    " (id, room_name, floor, max_seats, occupied)" +
                    " VALUES (1, 'Test', 2, 50, 'false',)")

    })
    void whenDeleteRoomShouldRespondOkTest() throws Exception {
        //when-then
        mockMvc.perform(delete("/rooms/" + TEST_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //assert
                .andExpect(status().isOk());

    }

    @Test
    void whenDeleteNonExistentRoomShouldRespondConflict() throws Exception {
        //when-then
        mockMvc.perform(delete("/rooms/" + TEST_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //assert
                .andExpect(status().isConflict());
    }

    @Test
    @SqlGroup({
            @Sql(statements = "INSERT INTO rooms" +
                    " (id, room_name, floor, max_seats, occupied)" +
                    " VALUES (1, 'Test', 2, 50, 'false',)")
    })
    void whenGetByIdShouldRespondOkTest() throws Exception {
        //when-then
        mockMvc.perform(get("/rooms/" + TEST_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.floor", is(2)))
                .andExpect(jsonPath("$.maxSeats", is(50)))
        ;
    }

    @Test
    void whenGetByNonExistentIdShouldReturnConflictTest() throws Exception {
        //when-then
        mockMvc.perform(get("/rooms/" + TEST_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //assert
                .andExpect(status().isConflict());
    }

    @Test
    @SqlGroup({
            @Sql(statements = "INSERT INTO rooms" +
                    " (id, room_name, floor, max_seats, occupied)" +
                    " VALUES (1, 'Test', 2, 50, 'false',)")
    })
    void whenUpdateRoomShouldReturnOkTest() throws Exception {

        Room expected = TEST_ROOM_UPDATED;

        //when-then
        mockMvc.perform(put("/rooms/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(OBJECT_MAPPER.writeValueAsString(TEST_ROOM_UPDATED)))
                .andExpect(status().isOk());//assert

        List<Room> rooms = roomDao.getAll();
        Room actual = rooms.get(0);

        //assert
        assertEquals(1, rooms.size());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getFloor(), actual.getFloor());
        assertEquals(expected.getMaxSeats(), actual.getMaxSeats());
        assertFalse(actual.isOccupied());

        assertTrue(Objects.nonNull(actual.getId()) && actual.getId() > 0);

    }

    @Test
    void whenUpdateRoomWithNonExistentIdShouldReturnConflictTest() throws Exception {
        //when-then
         mockMvc.perform(put("/rooms/" + TEST_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(OBJECT_MAPPER.writeValueAsString(TEST_ROOM)))
                //assert
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is(409)))
                .andExpect(jsonPath("$.error", is("Conflict")))
                .andExpect(jsonPath("$.message", is("Error when update room")));

    }

}
