package com.example.web.controller.data;

import com.example.Application;
import com.example.data.dao.MemberDao;
import com.example.data.entity.Member;
import com.example.data.repository.MemberRepository;
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
public class MemberDaoMockTest {

    @Autowired
    private MemberDao memberDao;

    @MockBean
    private MemberRepository mockMemberRepository;

    private static final long TEST_ID = 1L;

    private static final Member TEST_MEMBER_EXPECTED = Member.builder()
            .id(TEST_ID)
            .firstName("Lexa")
            .lastName("Olex")
            .age(18)
            .build();

    private static final Member TEST_MEMBER = Member.builder()
            .firstName("Lexa")
            .lastName("Olex")
            .age(18)
            .build();

    @Test
    void whenCreateConferenceShouldReturnConferenceWithIdTest() {
        //given
        when(mockMemberRepository.save(TEST_MEMBER)).thenReturn(TEST_MEMBER_EXPECTED);
        //when
        Member actual = memberDao.save(TEST_MEMBER);
        //then
        assertEquals(TEST_MEMBER_EXPECTED, actual);
    }

    @Test
    void whenCreateExistingClientShouldThrowDBExceptionTest() {
        //given
        when(mockMemberRepository.save(TEST_MEMBER)).thenThrow(DBException.class);
        //when-then
        assertThrows(DBException.class, () -> memberDao.save(TEST_MEMBER));
    }

    @Test
    void whenFindAllShouldReturnListOfClientsTest() {
        //given
        ArrayList EXPECTED_LIST = new ArrayList();
        EXPECTED_LIST.add(TEST_MEMBER_EXPECTED);
        when(mockMemberRepository.findAll()).thenReturn(EXPECTED_LIST);

        //when
        List actual = memberDao.getAll();
        //then
        assertEquals(EXPECTED_LIST, actual);
    }

    @Test
    void whenFindByIdShouldReturnClientTest() {
        //given
        when(mockMemberRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(TEST_MEMBER_EXPECTED));
        //when
        Member actual = memberDao.get(TEST_ID);
        //then
        assertEquals(TEST_MEMBER_EXPECTED, actual);
    }

    @Test
    void whenFindByIdReturnNullShouldThrowNotFoundExceptionTest() {
        //given
        when(mockMemberRepository.findById(TEST_ID)).thenReturn(Optional.empty());
        //when-then
        assertThrows(DBException.class, () -> memberDao.get(TEST_ID));
    }

    @Test
    void deleteTest() {
        //when
        memberDao.delete(TEST_ID);
        //then
        verify(mockMemberRepository).deleteById(TEST_ID);
    }

    @Test
    void whenUpdateShouldReturnClientTest() {
        //given
        when(mockMemberRepository.save(TEST_MEMBER)).thenReturn(TEST_MEMBER_EXPECTED);
        //when
        Member actual = memberDao.update(TEST_MEMBER);
        //then
        assertEquals(TEST_MEMBER_EXPECTED, actual);
    }

    @Test
    void whenUpdateNonExistentClientShouldThrowDBException() {
        //given
        when(mockMemberRepository.save(TEST_MEMBER)).thenThrow(DBException.class);
        //when-then
        assertThrows(DBException.class, () -> memberDao.update(TEST_MEMBER));
    }

    @Test
    void whenFindAllByConference_IdShouldReturnListOfMembers() {
        //given
        ArrayList EXPECTED_LIST = new ArrayList();
        EXPECTED_LIST.add(TEST_MEMBER_EXPECTED);
        when(mockMemberRepository.findAllByConference_Id(TEST_ID)).thenReturn(EXPECTED_LIST);
        //when
        List actual = memberDao.findAllByConference_Id(TEST_ID);
        //then
        assertEquals(EXPECTED_LIST, actual);

    }
}
