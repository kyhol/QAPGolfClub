package com.golfclub.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.golfclub.GolfClubApp;
import com.golfclub.model.Member;
import com.golfclub.repository.MemberRepository;
import com.golfclub.service.MemberService;

@SpringBootTest(classes = GolfClubApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SimpleMemberTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testCreateMember() {
        // Create a test member
        Member testMember = new Member();
        testMember.setName("Test Member");
        testMember.setEmail("test@example.com");
        testMember.setAddress("123 Test Lane");
        testMember.setPhoneNumber("555-TEST");
        testMember.setMembershipStartDate(LocalDate.now());
        testMember.setMembershipDuration(12);

        // Save the member
        Member savedMember = memberService.createMember(testMember);

        // Verify
        assertNotNull(savedMember.getId());
        assertEquals("Test Member", savedMember.getName());
        assertEquals("test@example.com", savedMember.getEmail());

        // Clean up
        memberRepository.deleteById(savedMember.getId());
    }
}