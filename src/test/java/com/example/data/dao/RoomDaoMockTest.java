package com.example.web.controller.data;

import com.example.Application;
import com.example.data.dao.RoomDao;
import com.example.data.entity.Conference;
import com.example.data.entity.Room;
import com.example.data.repository.RoomRepository;
import com.example.exception.DBException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
public class RoomDaoMockTest {

    private static final long TEST_ID = 1L;

    private static final Room TEST_ROOM_EXPECTED = Room.builder()
            .id(TEST_ID)
            .floor(1)
            .isOccupied(false)
            .maxSeats(100)
            .name("RoomName")
            .build();

    private static final Room TEST_ROOM = Room.builder()
            .floor(1)
            .isOccupied(false)
            .maxSeats(100)
            .name("RoomName")
            .build();

    @Autowired
    private RoomDao roomDao;

    @MockBean
    private RoomRepository mockRoomRepository;

    @Test
    void whenCreateConferenceShouldReturnConferenceWithIdTest() {
        //given
        when(mockRoomRepository.save(TEST_ROOM)).thenReturn(TEST_ROOM_EXPECTED);
        //when
        Room actual = roomDao.save(TEST_ROOM);
        //then
        assertEquals(TEST_ROOM_EXPECTED, actual);
    }

    @Test
    void whenCreateExistingConferenceShouldThrowDBExceptionTest() {
        //given
        when(mockRoomRepository.save(TEST_ROOM)).thenThrow(DBException.class);
        //when-then
        assertThrows(DBException.class, () -> roomDao.save(TEST_ROOM));
    }

    @Test
    void whenGetAllShouldReturnListOfConferencesTest() {
        //given
        ArrayList EXPECTED_LIST = new ArrayList();
        EXPECTED_LIST.add(TEST_ROOM_EXPECTED);
        when(mockRoomRepository.findAll()).thenReturn(EXPECTED_LIST);

        //when
        List actual = roomDao.getAll();
        //then
        assertEquals(EXPECTED_LIST, actual);
    }

    @Test
    void whenGetByIdShouldReturnConferenceTest() {
        //given
        when(mockRoomRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(TEST_ROOM_EXPECTED));
        //when
        Room actual = roomDao.get(TEST_ID);
        //then
        assertEquals(TEST_ROOM_EXPECTED, actual);
    }

    @Test
    void whenGetByIdReturnNullShouldThrowDBExceptionTest() {
        //given
        when(mockRoomRepository.findById(TEST_ID)).thenReturn(Optional.empty());
        //when-then
        assertThrows(DBException.class, () -> roomDao.get(TEST_ID));
    }

    @Test
    void deleteTest() {
        //when
        roomDao.delete(TEST_ID);
        //then
        verify(mockRoomRepository).deleteById(TEST_ID);
    }

    @Test
    void whenUpdateShouldReturnConferenceTest() {
        //given
        when(mockRoomRepository.save(TEST_ROOM)).thenReturn(TEST_ROOM_EXPECTED);
        //when
        Room actual = roomDao.update(TEST_ROOM);
        //then
        assertEquals(TEST_ROOM_EXPECTED, actual);
    }

    @Test
    void whenUpdateNonExistentConferenceShouldThrowDBException() {
        //given
        when(mockRoomRepository.save(TEST_ROOM)).thenThrow(DBException.class);
        //when-then
        assertThrows(DBException.class, () -> roomDao.update(TEST_ROOM));
    }

}
