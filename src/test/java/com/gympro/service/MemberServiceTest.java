package com.gympro.service;

import com.gympro.entity.Member;
import com.gympro.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = new Member("Alex Turner", "9876543210", "alex@gympro.com", 29, "Male", "New York", LocalDate.now());
        testMember.setId(1L);
    }

    @Test
    void testGetAllMembers() {
        when(memberRepository.findAll()).thenReturn(List.of(testMember));

        List<Member> members = memberService.getAllMembers();

        assertEquals(1, members.size());
        assertEquals("Alex Turner", members.get(0).getName());
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void testGetMemberById_Found() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));

        Optional<Member> found = memberService.getMemberById(1L);

        assertTrue(found.isPresent());
        assertEquals("alex@gympro.com", found.get().getEmail());
    }

    @Test
    void testGetMemberById_NotFound() {
        when(memberRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Member> found = memberService.getMemberById(99L);

        assertFalse(found.isPresent());
    }

    @Test
    void testSaveMember() {
        when(memberRepository.save(testMember)).thenReturn(testMember);

        Member saved = memberService.saveMember(testMember);

        assertNotNull(saved);
        assertEquals("Alex Turner", saved.getName());
        verify(memberRepository, times(1)).save(testMember);
    }

    @Test
    void testDeleteMember() {
        doNothing().when(memberRepository).deleteById(1L);

        memberService.deleteMember(1L);

        verify(memberRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSearchMembers() {
        when(memberRepository.searchByNameOrPhone("alex")).thenReturn(List.of(testMember));

        List<Member> results = memberService.searchMembers("alex");

        assertEquals(1, results.size());
        assertEquals("Alex Turner", results.get(0).getName());
    }

    @Test
    void testGetTotalMemberCount() {
        when(memberRepository.count()).thenReturn(10L);

        long count = memberService.getTotalMemberCount();

        assertEquals(10L, count);
    }
}
